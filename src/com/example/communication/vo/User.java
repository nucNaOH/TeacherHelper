package com.example.communication.vo;

/**
 * �û���
 * 
 * @author Administrator
 * 
 */
public class User {
	// �û���
	private String userName;
	// ����
	private String alias;
	// ����
	private String groupName;
	// IP��ַ
	private String ip;
	// ������
	private String hostName;
	// δ������Ϣ��
	private int msgCount;

	public User() {
		// ��ʼΪ0
		msgCount = 0;
	}

	public User(String userName, String alias, String groupName, String ip,
			String hostName) {
		super();
		this.userName = userName;
		this.alias = alias;
		this.groupName = groupName;
		this.ip = ip;
		this.hostName = hostName;
		msgCount = 0;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int getMsgCount() {
		return msgCount;
	}

	public void setMsgCount(int msgCount) {
		this.msgCount = msgCount;
	}

}
