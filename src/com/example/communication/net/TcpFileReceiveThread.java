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
 * TCP接收文件线程类
 * 
 * @author Administrator
 * 
 */
public class TcpFileReceiveThread implements Runnable {

	private String[] fileInfos; // 文件信息字符数组
	private String senderIp;
	private long packetNo; // 包编号
	private String savePath; // 文件保存路径

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

		// 判断接收文件的文件夹是否存在，若不存在，则创建
		File fileDir = new File(savePath);
		if (!fileDir.exists()) { // 若不存在
			fileDir.mkdir();
		}

	}

	@Override
	public void run() {
		for (int i = 0; i < fileInfos.length; i++) { // 循环接受每个文件
			// 注意，这里暂时未处理文件名包含冒号的情况，协议规定中若文件名包含冒号，则用双冒号替代。需做处理，这里暂时没做
			String[] fileInfo = fileInfos[i].split(":"); // 使用:分隔得到文件信息数组
			// 先发送一个指定获取文件的包
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

				// 发送收取文件命令
				byte[] sendBytes = ipmsgPro.getProtocolString().getBytes("gbk");
				bos.write(sendBytes, 0, sendBytes.length);
				bos.flush();

				// 接收文件
				File receiveFile = new File(savePath + fileInfo[1]);
				if (receiveFile.exists()) { // 若对应文件名的文件已存在，则删除原来的文件
					receiveFile.delete();
				}
				fbos = new BufferedOutputStream(new FileOutputStream(
						receiveFile));
				bis = new BufferedInputStream(socket.getInputStream());
				int len = 0;
				long sended = 0; // 已接收文件大小
				long total = Long.parseLong(fileInfo[2], 16); // 文件总大小
				int temp = 0;
				while ((len = bis.read(readBuffer)) != -1) {
					fbos.write(readBuffer, 0, len);
					fbos.flush();

					sended += len; // 已接收文件大小
					int sendedPer = (int) (sended * 100 / total); // 接收百分比
					if (temp != sendedPer) { // 每增加一个百分比，发送一个message
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
			} finally { // 处理

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
