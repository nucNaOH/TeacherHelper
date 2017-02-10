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
 * 使用Handler异步处理消息
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
		
		//建立通知
		mNotManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mNotification = new Notification(android.R.drawable.stat_sys_download, "接收文件", System.currentTimeMillis());
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
				//收到发送文件请求
				//得到附加文件信息,字符串数组，分别放了  IP，附加文件信息,发送者名称，包ID
				final String[] extraMsg = (String[]) msg.obj;	
				byte[] bt = {0x07};		//用于分隔多个发送文件的字符
				String splitStr = new String(bt);
				final String[] fileInfos = extraMsg[1].split(splitStr);	//使用分隔字符进行分割
				
				
				String infoStr = "发送者IP:\t" + extraMsg[0] + "\n" + 
								 "发送者名称:\t" + extraMsg[2] + "\n" +
								 "文件总数:\t" + fileInfos.length +"个";
				
				new AlertDialog.Builder(queue.getLast())
					.setIcon(android.R.drawable.ic_dialog_info)
					.setTitle("收到文件传输请求")
					.setMessage(infoStr)
					.setPositiveButton("接收", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									Thread fileReceiveThread = new Thread(new TcpFileReceiveThread(extraMsg[3], extraMsg[0],fileInfos));	//新建一个接受文件线程
									fileReceiveThread.start();	//启动线程
									
									queue.getLast().showNotification();	//显示notification
								}
							})
					 .setNegativeButton("取消", 
							 new DialogInterface.OnClickListener() {
						 		public void onClick(DialogInterface dialog, int which) {
						 			//发送拒绝报文
						 			//构造拒绝报文
									CommunicationProtocol ipmsgSend = new CommunicationProtocol();
									ipmsgSend.setVersion("" +CommunicationConst.COM_VERSION);	//拒绝命令字
									ipmsgSend.setCommandNo(CommunicationConst.FILE_RELEASEFILE);
									ipmsgSend.setSenderName("android");
									ipmsgSend.setSenderHost("android");
									ipmsgSend.setAdditionalSection(extraMsg[3] + "\0");	//附加信息里是确认收到的包的编号
						 			
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
				
			case CommunicationConst.FILE_RECEIVEINFO:{	//更新接收文件进度条
				int[] sendedPer = (int[]) msg.obj;	//得到信息
				queue.getLast().mNotification.contentView.setProgressBar(R.id.pd_download, 100, sendedPer[1], false);
				queue.getLast().mNotification.contentView.setTextViewText(R.id.fileRec_info, "文件"+ (sendedPer[0] + 1) +"接收中:" + sendedPer[1] + "%");
				
				queue.getLast().showNotification();	//显示notification
			}
				break;
				
			case CommunicationConst.FILE_RECEIVESUCCESS:{	//文件接收成功
				int[] successNum = (int[]) msg.obj;
				
				queue.getLast().mNotification.contentView.setTextViewText(R.id.fileRec_info, "第"+ successNum[0] +"个文件接收成功");
				if(successNum[0] == successNum[1]){
					queue.getLast().mNotification.contentView.setTextViewText(R.id.fileRec_info, "文件成功接收到SD卡的Received文件夹中");
					
					queue.getLast().makeTextShort("文件成功接收到SD卡的Received文件夹中");
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
	
	
	/* Handler的sendEmptyMessage()只能放数据
	 * sendMessage()允许你处理Message对象(Message里可以包含数据)
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
