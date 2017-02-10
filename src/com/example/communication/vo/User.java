package com.example.communication.vo;

/**
 * 用户类
 * 
 * @author Administrator
 * 
 */
public class User {
	// 用户名
	private String userName;
	// 别名
	private String alias;
	// 组名
	private String groupName;
	// IP地址
	private String ip;
	// 主机名
	private String hostName;
	// 未接收消息数
	private int msgCount;

	public User() {
		// 初始为0
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
