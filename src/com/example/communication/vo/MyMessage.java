package com.example.communication.vo;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ��Ϣ��
 * 
 * @author Administrator
 * 
 */
@SuppressLint("SimpleDateFormat")
public class MyMessage {
	// ��Ϣ�����ߵ�IP
	private String senderIp;
	// ��Ϣ�����ߵ�����
	private String senderName;
	// ��Ϣ����
	private String msg;
	// ����ʱ��
	private Date time;
	// �Ƿ�Ϊ�Լ�����
	private boolean selfMsg;

	public MyMessage() {
		// Ĭ�ϲ����Լ�
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
