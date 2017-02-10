package com.example.communication.util;

/**
 * ��Ϣ����
 * 
 * @author Administrator
 * 
 */
public class CommunicationConst {
	public static final int COM_VERSION 		= 0x001; // Ĭ�ϰ汾��1
	public static final int COM_PORT 			= 0x0979; // Ĭ�϶˿�2425

	public static final int COM_ONLINE 			= 0x00000001; // �û�����
	public static final int COM_OFFLINE 		= 0x00000002; // �û�����
	public static final int COM_ANSONLINE 		= 0x00000003; // �����û�������Ϣ
	public static final int COM_SENDMSG 		= 0x00000020; // ������Ϣ
	public static final int COM_RECVMSG 		= 0x00000021; // �յ���Ϣ
	public static final int COM_SENDCHECK 		= 0x00000100; // ������֤

	public static final int FILE_SENDFILE 		= 0x00000060; // �ļ���������
	public static final int FILE_ATTACHFILE 	= 0x00200000; // �����ļ�
	public static final int FILE_RELEASEFILE 	= 0x00000061; // ���������ļ�
	public static final int FILE_SENDSUCCESS 	= 0xFF;       // �ļ����ͳɹ�
	public static final int FILE_RECEIVEINFO 	= 0xFE; 	  // �����ļ��������ļ���Ϣ
	public static final int FILE_RECEIVESUCCESS = 0xFD; 	  // �����ļ��ɹ�
	public static final int FILE_REGULAR 		= 0x00000001; // �ļ�����
}
