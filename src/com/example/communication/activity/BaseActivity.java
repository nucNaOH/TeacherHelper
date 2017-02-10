package com.example.communication.activity;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;


import com.example.communication.net.TcpFileReceiveThread;
import com.example.communication.net.NetThreadHelper;
import com.example.communication.util.CommunicationConst;
import com.example.communication.util.CommunicationProtocol;
import com.teacher.helper.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * ʹ��Handler�첽������Ϣ
 * @author Administrator
 *
 */
public abstract class BaseActivity extends Activity {
	private static int notification_id = 9786970;
	private NotificationManager mNotManager;
	private Notification mNotification;
	
	protected static LinkedList<BaseActivity> queue = new LinkedList<BaseActivity>();
	protected static NetThreadHelper netThreadHelper;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		netThreadHelper = NetThreadHelper.newInstance();
		
		//����֪ͨ
		mNotManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mNotification = new Notification(android.R.drawable.stat_sys_download, "�����ļ�", System.currentTimeMillis());
		mNotification.contentView = new RemoteViews(getPackageName(), R.layout.file_download_notification);
		mNotification.contentView.setProgressBar(R.id.pd_download, 100, 0, false);
		Intent notificationIntent = new Intent(this,BaseActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		mNotification.contentIntent = contentIntent;
		
		
		if(!queue.contains(this))
			queue.add(this);
	}
	
	private static Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommunicationConst.COM_SENDMSG | CommunicationConst.FILE_ATTACHFILE:{
				//�յ������ļ�����
				//�õ������ļ���Ϣ,�ַ������飬�ֱ����  IP�������ļ���Ϣ,���������ƣ���ID
				final String[] extraMsg = (String[]) msg.obj;	
				byte[] bt = {0x07};		//���ڷָ���������ļ����ַ�
				String splitStr = new String(bt);
				final String[] fileInfos = extraMsg[1].split(splitStr);	//ʹ�÷ָ��ַ����зָ�
				
				
				String infoStr = "������IP:\t" + extraMsg[0] + "\n" + 
								 "����������:\t" + extraMsg[2] + "\n" +
								 "�ļ�����:\t" + fileInfos.length +"��";
				
				new AlertDialog.Builder(queue.getLast())
					.setIcon(android.R.drawable.ic_dialog_info)
					.setTitle("�յ��ļ���������")
					.setMessage(infoStr)
					.setPositiveButton("����", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									Thread fileReceiveThread = new Thread(new TcpFileReceiveThread(extraMsg[3], extraMsg[0],fileInfos));	//�½�һ�������ļ��߳�
									fileReceiveThread.start();	//�����߳�
									
									queue.getLast().showNotification();	//��ʾnotification
								}
							})
					 .setNegativeButton("ȡ��", 
							 new DialogInterface.OnClickListener() {
						 		public void onClick(DialogInterface dialog, int which) {
						 			//���;ܾ�����
						 			//����ܾ�����
									CommunicationProtocol ipmsgSend = new CommunicationProtocol();
									ipmsgSend.setVersion("" +CommunicationConst.COM_VERSION);	//�ܾ�������
									ipmsgSend.setCommandNo(CommunicationConst.FILE_RELEASEFILE);
									ipmsgSend.setSenderName("android");
									ipmsgSend.setSenderHost("android");
									ipmsgSend.setAdditionalSection(extraMsg[3] + "\0");	//������Ϣ����ȷ���յ��İ��ı��
						 			
									InetAddress sendAddress = null;
									try {
										sendAddress = InetAddress.getByName(extraMsg[0]);
									} catch (UnknownHostException e) {
										e.printStackTrace();
									}
									
									netThreadHelper.sendUdpData(ipmsgSend.getProtocolString(), sendAddress, CommunicationConst.COM_PORT);
									
						 		}
					 }).show();
					 
			}
				break;
				
			case CommunicationConst.FILE_RECEIVEINFO:{	//���½����ļ�������
				int[] sendedPer = (int[]) msg.obj;	//�õ���Ϣ
				queue.getLast().mNotification.contentView.setProgressBar(R.id.pd_download, 100, sendedPer[1], false);
				queue.getLast().mNotification.contentView.setTextViewText(R.id.fileRec_info, "�ļ�"+ (sendedPer[0] + 1) +"������:" + sendedPer[1] + "%");
				
				queue.getLast().showNotification();	//��ʾnotification
			}
				break;
				
			case CommunicationConst.FILE_RECEIVESUCCESS:{	//�ļ����ճɹ�
				int[] successNum = (int[]) msg.obj;
				
				queue.getLast().mNotification.contentView.setTextViewText(R.id.fileRec_info, "��"+ successNum[0] +"���ļ����ճɹ�");
				if(successNum[0] == successNum[1]){
					queue.getLast().mNotification.contentView.setTextViewText(R.id.fileRec_info, "�ļ��ɹ����յ�SD����Received�ļ�����");
					
					queue.getLast().makeTextShort("�ļ��ɹ����յ�SD����Received�ļ�����");
				}
				queue.getLast().showNotification();
			}
				break;
			default:
				if(queue.size() > 0)
					queue.getLast().processMessage(msg);
				break;
			}
		}

	};
	
	public static BaseActivity getActivity(int index){
		if (index < 0 || index >= queue.size()){
			throw new IllegalArgumentException("out of queue");
		}
		return queue.get(index);
	}
	
	public static BaseActivity getCurrentActivity(){
		return queue.getLast();
	}
	
	
	/* Handler��sendEmptyMessage()ֻ�ܷ�����
	 * sendMessage()�����㴦��Message����(Message����԰�������)
	 */
	public static void sendEmptyMessage(int what) {
		handler.sendEmptyMessage(what);
	}
	
	public static void sendMessage(Message msg) {
		handler.sendMessage(msg);
	}
	
	public static void sendMessage(int cmd, String text) {
		Message msg = new Message();
		msg.obj = text;
		msg.what = cmd;
		sendMessage(msg);
	}
	
	public void exit() {
		while (queue.size() > 0)
			queue.getLast().finish();
	}

	@Override
	public void finish() {
		super.finish();
		queue.removeLast();
	}

	public void makeTextLong(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}

	public void makeTextShort(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	public abstract void processMessage(Message msg);
	
	protected void showNotification(){
		mNotManager.notify(notification_id, mNotification);
	}
	
}
