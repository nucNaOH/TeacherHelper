package com.example.communication.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;


import com.example.communication.activity.BaseActivity;
import com.example.communication.util.CommunicationConst;
import com.example.communication.util.CommunicationProtocol;
/**
 * TCP发送文件线程类
 * @author Administrator
 *
 */
public class TcpFileSendThread implements Runnable{
	private String[] filePathArray;	//保存发送文件路径的数组
	
	public static ServerSocket server;	
	private Socket socket;	
	private byte[] readBuffer = new byte[1024*1024];
	
	public TcpFileSendThread(String[] filePathArray){
		this.filePathArray = filePathArray;
		
		try {
			server = new ServerSocket(CommunicationConst.COM_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public void run() {
		for(int i = 0; i < filePathArray.length; i++){
			try {
				socket = server.accept();
				BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
				BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
				int mlen = bis.read(readBuffer);
				String comStr = new String(readBuffer,0,mlen,"gbk");
				
				
				
				CommunicationProtocol comPro = new CommunicationProtocol(comStr);
				String fileNoStr = comPro.getAdditionalSection();
				String[] fileNoArray = fileNoStr.split(":");
				int sendFileNo = Integer.valueOf(fileNoArray[1]);
				
				File sendFile = new File(filePathArray[sendFileNo]);	//要发送的文件
				BufferedInputStream fbis = new BufferedInputStream(new FileInputStream(sendFile));
				
				int rlen = 0;
				while((rlen = fbis.read(readBuffer)) != -1){
					bos.write(readBuffer, 0, rlen);
				}
				bos.flush();
				
				if(bis != null){
					bis.close();
					bis = null;
				}
				
				if(fbis != null){
					fbis.close();
					fbis = null;
				}
				
				if(bos != null){
					bos.close();
					bos = null;
				}
				
				if(i == (filePathArray.length -1)){
					BaseActivity.sendEmptyMessage(CommunicationConst.FILE_SENDSUCCESS);	//文件发送成功
				}
				
			}catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				break;
			} finally{
				if(socket != null){
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					socket = null;
				}
			}
			
		}
		
		if(server != null){
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			server = null;
		}
		
		
	}

}
