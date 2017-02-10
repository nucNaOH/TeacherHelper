package com.example.communication.util;

/**
 * 消息常量
 * 
 * @author Administrator
 * 
 */
public class CommunicationConst {
	public static final int COM_VERSION 		= 0x001; // 默认版本号1
	public static final int COM_PORT 			= 0x0979; // 默认端口2425

	public static final int COM_ONLINE 			= 0x00000001; // 用户上线
	public static final int COM_OFFLINE 		= 0x00000002; // 用户下线
	public static final int COM_ANSONLINE 		= 0x00000003; // 回送用户在线信息
	public static final int COM_SENDMSG 		= 0x00000020; // 发送消息
	public static final int COM_RECVMSG 		= 0x00000021; // 收到消息
	public static final int COM_SENDCHECK 		= 0x00000100; // 传送验证

	public static final int FILE_SENDFILE 		= 0x00000060; // 文件传输请求
	public static final int FILE_ATTACHFILE 	= 0x00200000; // 附加文件
	public static final int FILE_RELEASEFILE 	= 0x00000061; // 丢弃附加文件
	public static final int FILE_SENDSUCCESS 	= 0xFF;       // 文件发送成功
	public static final int FILE_RECEIVEINFO 	= 0xFE; 	  // 接收文件，包含文件信息
	public static final int FILE_RECEIVESUCCESS = 0xFD; 	  // 接收文件成功
	public static final int FILE_REGULAR 		= 0x00000001; // 文件规则
}
