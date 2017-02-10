package com.example.communication.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.annotation.SuppressLint;
import android.os.Message;

import com.example.communication.activity.BaseActivity;
import com.example.communication.util.CommunicationConst;
import com.example.communication.util.CommunicationProtocol;

/**
 * TCP�����ļ��߳���
 * 
 * @author Administrator
 * 
 */
public class TcpFileReceiveThread implements Runnable {

	private String[] fileInfos; // �ļ���Ϣ�ַ�����
	private String senderIp;
	private long packetNo; // �����
	private String savePath; // �ļ�����·��

	private String selfName;
	private String selfGroup;

	private Socket socket;
	private BufferedInputStream bis;
	private BufferedOutputStream bos;
	BufferedOutputStream fbos;
	private byte[] readBuffer = new byte[1024*1024];

	@SuppressLint("SdCardPath")
	public TcpFileReceiveThread(String packetNo, String senderIp,
			String[] fileInfos) {
		this.packetNo = Long.valueOf(packetNo);
		this.fileInfos = fileInfos;
		this.senderIp = senderIp;

		selfName = "android";
		selfGroup = "android";
		savePath = "/mnt/sdcard/Received/";

		// �жϽ����ļ����ļ����Ƿ���ڣ��������ڣ��򴴽�
		File fileDir = new File(savePath);
		if (!fileDir.exists()) { // ��������
			fileDir.mkdir();
		}

	}

	@Override
	public void run() {
		for (int i = 0; i < fileInfos.length; i++) { // ѭ������ÿ���ļ�
			// ע�⣬������ʱδ�����ļ�������ð�ŵ������Э��涨�����ļ�������ð�ţ�����˫ð���������������������ʱû��
			String[] fileInfo = fileInfos[i].split(":"); // ʹ��:�ָ��õ��ļ���Ϣ����
			// �ȷ���һ��ָ����ȡ�ļ��İ�
			CommunicationProtocol ipmsgPro = new CommunicationProtocol();
			ipmsgPro.setVersion(String.valueOf(CommunicationConst.COM_VERSION));
			ipmsgPro.setCommandNo(CommunicationConst.FILE_SENDFILE);
			ipmsgPro.setSenderName(selfName);
			ipmsgPro.setSenderHost(selfGroup);
			String additionStr = Long.toHexString(packetNo) + ":" + i + ":"+ "0:";
			ipmsgPro.setAdditionalSection(additionStr);

			try {
				socket = new Socket(senderIp, CommunicationConst.COM_PORT);
				bos = new BufferedOutputStream(socket.getOutputStream());

				// ������ȡ�ļ�����
				byte[] sendBytes = ipmsgPro.getProtocolString().getBytes("gbk");
				bos.write(sendBytes, 0, sendBytes.length);
				bos.flush();

				// �����ļ�
				File receiveFile = new File(savePath + fileInfo[1]);
				if (receiveFile.exists()) { // ����Ӧ�ļ������ļ��Ѵ��ڣ���ɾ��ԭ�����ļ�
					receiveFile.delete();
				}
				fbos = new BufferedOutputStream(new FileOutputStream(
						receiveFile));
				bis = new BufferedInputStream(socket.getInputStream());
				int len = 0;
				long sended = 0; // �ѽ����ļ���С
				long total = Long.parseLong(fileInfo[2], 16); // �ļ��ܴ�С
				int temp = 0;
				while ((len = bis.read(readBuffer)) != -1) {
					fbos.write(readBuffer, 0, len);
					fbos.flush();

					sended += len; // �ѽ����ļ���С
					int sendedPer = (int) (sended * 100 / total); // ���հٷֱ�
					if (temp != sendedPer) { // ÿ����һ���ٷֱȣ�����һ��message
						int[] msgObj = { i, sendedPer };
						Message msg = new Message();
						msg.what = CommunicationConst.FILE_RECEIVEINFO;
						msg.obj = msgObj;
						BaseActivity.sendMessage(msg);
						temp = sendedPer;
					}
					if (len < readBuffer.length)
						break;
				}

				int[] success = { i + 1, fileInfos.length };
				Message msg4success = new Message();
				msg4success.what = CommunicationConst.FILE_RECEIVESUCCESS;
				msg4success.obj = success;
				BaseActivity.sendMessage(msg4success);

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally { // ����

				if (bos != null) {
					try {
						bos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					bos = null;
				}

				if (fbos != null) {
					try {
						fbos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					fbos = null;
				}

				if (bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					bis = null;
				}

				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					socket = null;
				}

			}

		}

	}

}
