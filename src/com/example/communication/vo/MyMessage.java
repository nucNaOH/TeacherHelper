package com.example.communication.vo;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 消息类
 * 
 * @author Administrator
 * 
 */
@SuppressLint("SimpleDateFormat")
public class MyMessage {
	// 消息发送者的IP
	private String senderIp;
	// 消息发送者的名字
	private String senderName;
	// 消息内容
	private String msg;
	// 发送时间
	private Date time;
	// 是否为自己发送
	private boolean selfMsg;

	public MyMessage() {
		// 默认不是自己
		this.selfMsg = false;
	}

	public MyMessage(String senderIp, String senderName, String msg, Date time) {
		super();
		this.senderIp = senderIp;
		this.senderName = senderName;
		this.msg = msg;
		this.time = time;
		this.selfMsg = false;
	}

	public String getSenderIp() {
		return senderIp;
	}

	public void setSenderIp(String senderIp) {
		this.senderIp = senderIp;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public boolean isSelfMsg() {
		return selfMsg;
	}

	public void setSelfMsg(boolean selfMsg) {
		this.selfMsg = selfMsg;
	}

	public String getTimeStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(time);
	}
}
