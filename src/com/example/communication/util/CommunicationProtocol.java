package com.example.communication.util;

import java.util.Date;

/**
 * ģ��Э����
 * 
 * @author Administrator
 * 
 */
public class CommunicationProtocol {
	// �汾��
	private String version;
	// ���ݰ����
	private String packetNo;
	// ����������
	private String senderName;
	// ������������
	private String senderHost;
	// ������
	private int commandNo;
	// ��������
	private String additionalSection;

	public CommunicationProtocol() {
		this.packetNo = getSeconds();
	}

	public CommunicationProtocol(String senderName, String senderHost,
			int commandNo, String additionalSection) {
		super();
		this.version = "1";
		this.packetNo = getSeconds();
		this.senderName = senderName;
		this.senderHost = senderHost;
		this.commandNo = commandNo;
		this.additionalSection = additionalSection;
	}

	// ����Э���ַ�����ʼ��
	public CommunicationProtocol(String protocolString) {
		String[] args = protocolString.split(":");
		version = args[0];
		packetNo = args[1];
		senderName = args[2];
		senderHost = args[3];
		commandNo = Integer.parseInt(args[4]);
		// �Ƿ��и�������
		if (args.length >= 6) {
			additionalSection = args[5];
		} else {
			additionalSection = "";
		}
		// ��������������:�����
		for (int i = 6; i < args.length; i++) {
			additionalSection += (":" + args[i]);
		}
	}

	// �õ�Э�鴮
	public String getProtocolString() {
		StringBuffer sb = new StringBuffer();
		sb.append(version);
		sb.append(":");
		sb.append(packetNo);
		sb.append(":");
		sb.append(senderName);
		sb.append(":");
		sb.append(senderHost);
		sb.append(":");
		sb.append(commandNo);
		sb.append(":");
		sb.append(additionalSection);
		return sb.toString();
	}

	// �õ����ݰ���ţ�������
	private String getSeconds() {
		Date nowDate = new Date();
		return Long.toString(nowDate.getTime());
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPacketNo() {
		return packetNo;
	}

	public void setPacketNo(String packetNo) {
		this.packetNo = packetNo;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderHost() {
		return senderHost;
	}

	public void setSenderHost(String senderHost) {
		this.senderHost = senderHost;
	}

	public int getCommandNo() {
		return commandNo;
	}

	public void setCommandNo(int commandNo) {
		this.commandNo = commandNo;
	}

	public String getAdditionalSection() {
		return additionalSection;
	}

	public void setAdditionalSection(String additionalSection) {
		this.additionalSection = additionalSection;
	}

}
