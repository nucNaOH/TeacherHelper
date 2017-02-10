package com.example.communication.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.os.Message;

import com.example.communication.activity.MainActivity;
import com.example.communication.activity.BaseActivity;
import com.example.communication.util.CommunicationConst;
import com.example.communication.util.CommunicationProtocol;
import com.example.communication.util.ReceiveMsgListener;
import com.example.communication.vo.MyMessage;
import com.example.communication.vo.User;

/**
 * UDP通信
 * 
 * @author Administrator
 *
 */

/*
 *1、局域网用户列表的建立，启动时使用UDP协议向255.255.255.255这个广播地址发送广播包,默认端口是2425。
 *  广播包内容包含用户名、工作组、主机名、IP等信息，已启动的用户通过2425端口收到此广播包后，就会在自己的用户列表中添加这个用户的
 *  用户名、工作组等信息，同时向对方IP发送本机用户的个人信息，从而双方都能建立起用户列表；
 *2、刷新用户列表和建立用户列表类似，返回的标识信息略有不同；
 *3、传送聊天信息使用UDP协议，由于UDP协议是无连接协议，传输速度快，但是没有确认机制，是不可靠的协议，
 *  需要自己定义返回信息的标志来判断对方是否收到信息；
 *4、用户离线时发送一个离线广播包到255.255.255.255，收到此广播包的用户，根据包中的IP地址删除对方的用户列表信息；
 *5、传送文件使用TCP协议，端口2425。
 */
public class NetThreadHelper implements Runnable {

	private static NetThreadHelper instance;

	// 线程工作标识
	private boolean onWork = false;

	private String selfName;
	private String selfGroup;

	// UDP线程
	private Thread udpThread = null;
	// 用于接收和发送UDP数据的Socket
	private DatagramSocket udpSocket = null;
	// 用于发送的UDP数据包
	private DatagramPacket udpSendPacket = null;
	// 用于接收的UDP数据包
	private DatagramPacket udpResPacket = null;
	// 接收数据的缓存
	private byte[] resBuffer = new byte[1024];
	// 发送数据的缓存
	private byte[] sendBuffer = null;

	// 所有用户的集合
	private Map<String, User> users;
	// 用户个数，只有调用
	private int userCount = 0;

	// 消息队列，没有聊天窗口时的消息放到这个队列中
	private Queue<MyMessage> receiveMsgQueue;
	// ReceiveMsgListener容器
	private Vector<ReceiveMsgListener> listeners;

	private NetThreadHelper() {
		users = new HashMap<String, User>();
		receiveMsgQueue = new ConcurrentLinkedQueue<MyMessage>();
		listeners = new Vector<ReceiveMsgListener>();
		selfName = "AndroidGuy";
		selfGroup = "android";
	}

	/** 单例 */
	public static NetThreadHelper newInstance() {
		if (instance == null){
			instance = new NetThreadHelper();
		}
		return instance;
	}

	public Map<String, User> getUsers() {
		return users;
	}

	public int getUserCount() {
		return userCount;
	}

	public Queue<MyMessage> getReceiveMsgQueue() {
		return receiveMsgQueue;
	}

	// 添加Listener到容器中
	public void addReceiveMsgListener(ReceiveMsgListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	// 从容器中移除相应的Listener
	public void removeReceiveMsgListener(ReceiveMsgListener listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}

	/** 判断是否有前台的聊天窗口来接收数据 */
	private boolean receiveMsg(MyMessage msg) {
		for (int i = 0; i < listeners.size(); i++) {
			ReceiveMsgListener listener = listeners.get(i);
			if (listener.receive(msg)) {
				return true;
			}
		}
		return false;
	}

	/** 上线或下线通知 */
	public void noticeLine(String tag) {
		CommunicationProtocol onlineOrOfflineSend = new CommunicationProtocol();
		onlineOrOfflineSend.setVersion(String.valueOf(CommunicationConst.COM_VERSION));
		onlineOrOfflineSend.setSenderName(selfName);
		onlineOrOfflineSend.setSenderHost(selfGroup);
		if (tag.equals("online")) {
			onlineOrOfflineSend.setCommandNo(CommunicationConst.COM_ONLINE);
		} else if (tag.equals("offline")) {
			onlineOrOfflineSend.setCommandNo(CommunicationConst.COM_OFFLINE);
		}
		// 附加信息中加入用户名和分组信息
		onlineOrOfflineSend.setAdditionalSection(selfName + "\0" + selfGroup);
		InetAddress broadcastAddr;
		try {
			// 广播地址
			broadcastAddr = InetAddress.getByName("255.255.255.255");
			// 发送数据
			sendUdpData(onlineOrOfflineSend.getProtocolString() + "\0",
					broadcastAddr, CommunicationConst.COM_PORT);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	/** 更新用户列表 */
	public void refreshUsers() {
		// 清空列表
		users.clear();
		// 发送上线通知
		noticeLine("online");
		BaseActivity.sendEmptyMessage(CommunicationConst.COM_ONLINE);
	}

	/** 监听端口，接收UDP数据 */
	public boolean connectSocket() {
		boolean result = false;
		try {
			if (udpSocket == null) {
				udpSocket = new DatagramSocket(CommunicationConst.COM_PORT);
			}
			if (udpResPacket == null) {
				udpResPacket = new DatagramPacket(resBuffer, 1024);
			}
			onWork = true;
			if (udpThread == null) {
				udpThread = new Thread(this);
				udpThread.start();
			}
			result = true;
		} catch (SocketException e) {
			e.printStackTrace();
			disconnectSocket();
		}
		return result;
	}

	/** 停止监听 */
	public void disconnectSocket() {
		onWork = false;
		if (udpThread != null) {
			udpThread.interrupt(); 
		}
	}
	
	/** 发送UDP数据包 */
	public synchronized void sendUdpData(String sendStr, InetAddress sendto,int sendPort) { 
		try {
			sendBuffer = sendStr.getBytes("gbk");
			udpSendPacket = new DatagramPacket(sendBuffer,sendBuffer.length,sendto,sendPort);
			udpSocket.send(udpSendPacket); 
			udpSendPacket = null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace();
			udpSendPacket = null;
		}
	}
	
	/** 添加用户到Users的Map中 */
	private synchronized void addUser(CommunicationProtocol comPro) { 
		String userIp = udpResPacket.getAddress().getHostAddress();
		User user = new User();
		user.setAlias(comPro.getSenderName()); 
		String[] userInfo = comPro.getAdditionalSection().split("\0"); 
		
		user.setUserName(userInfo[0]);
		if(userIp.equals(MainActivity.hostIp)){
			user.setGroupName("自己");
		}else{
			user.setGroupName("好友");
		}
		
		user.setIp(userIp);
		user.setHostName(comPro.getSenderHost());
		users.put(userIp, user);
	}

	@Override
	public void run() {
		while (onWork) {
			try {
				udpSocket.receive(udpResPacket);
			} catch (IOException e) {
				onWork = false;
				if (udpResPacket != null) {
					udpResPacket = null;
				}
				if (udpSocket != null) {
					udpSocket.close();
					udpSocket = null;
				}
				udpThread = null;
				break;
			}
			if (udpResPacket.getLength() == 0) {
				continue;
			}
			String comStr = "";
			try {
				comStr = new String(resBuffer, 0, udpResPacket.getLength(),"gbk");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			// 截取收到的数据
			CommunicationProtocol comPro = new CommunicationProtocol(comStr);
			int commandNo = comPro.getCommandNo();
			// 取低8位
			int commandNo2 = 0x000000FF & commandNo; 
			switch (commandNo2) {
				// 收到上线数据包，添加用户
				case CommunicationConst.COM_ONLINE: {
					addUser(comPro);
					BaseActivity.sendEmptyMessage(CommunicationConst.COM_ONLINE);
					// 构造回送报文内容
					CommunicationProtocol onlineConSend = new CommunicationProtocol();
					onlineConSend.setVersion(String.valueOf(CommunicationConst.COM_VERSION));
					onlineConSend.setSenderName(selfName);
					onlineConSend.setSenderHost(selfGroup);
					onlineConSend.setCommandNo(CommunicationConst.COM_ANSONLINE);
					onlineConSend.setAdditionalSection(selfName + "\0");
					sendUdpData(onlineConSend.getProtocolString(),
							udpResPacket.getAddress(), udpResPacket.getPort());
					break;
				}
	
				// 收到上线应答，更新在线用户表
				case CommunicationConst.COM_ANSONLINE: {
					addUser(comPro);
					BaseActivity.sendEmptyMessage(CommunicationConst.COM_ANSONLINE);
					break;
				}
	
				// 收到下线广播，删除Map中对应的值
				case CommunicationConst.COM_OFFLINE: {
					String userIp = udpResPacket.getAddress().getHostAddress();
					users.remove(userIp);
					BaseActivity.sendEmptyMessage(CommunicationConst.COM_OFFLINE);
					break;
	
				}
	
				// 收到消息，处理
				case CommunicationConst.COM_SENDMSG: {
					String senderIp = udpResPacket.getAddress().getHostAddress();
					String senderName = comPro.getSenderName();
					String additionStr = comPro.getAdditionalSection();
					Date time = new Date();
					String msgStr;
	
					// 命令附加字段的判断
	
					// 若有命令字传送验证选项，则回送收到的消息报文
					if ((commandNo & CommunicationConst.COM_SENDCHECK) == CommunicationConst.COM_SENDCHECK) {
						// 构造消息报文
						CommunicationProtocol ipmsgSend = new CommunicationProtocol();
						ipmsgSend.setVersion("" + CommunicationConst.COM_VERSION); 
						ipmsgSend.setCommandNo(CommunicationConst.COM_RECVMSG);
						ipmsgSend.setSenderName(selfName);
						ipmsgSend.setSenderHost(selfGroup);
						ipmsgSend.setAdditionalSection(comPro.getPacketNo() + "\0"); 
	
						sendUdpData(ipmsgSend.getProtocolString(),
								udpResPacket.getAddress(), udpResPacket.getPort()); 
					}
	
					String[] splitStr = additionStr.split("\0"); 
					msgStr = splitStr[0]; 
	
					// 若有发送文件选项，则截取附带信息
					if ((commandNo & CommunicationConst.FILE_ATTACHFILE) == CommunicationConst.FILE_ATTACHFILE) {
						Message msg = new Message();
						msg.what = (CommunicationConst.COM_SENDMSG | CommunicationConst.FILE_ATTACHFILE);
						String[] extraMsg = { senderIp, splitStr[1], senderName,
								comPro.getPacketNo() };
						msg.obj = extraMsg; 
						BaseActivity.sendMessage(msg);
	
						break;
					}
	
					// 若只发送消息
					MyMessage msg = new MyMessage(senderIp, senderName, msgStr, time);
					if (!receiveMsg(msg)) { 
						receiveMsgQueue.add(msg); 
						BaseActivity.sendEmptyMessage(CommunicationConst.COM_SENDMSG); 
					}
					break;
				}
				
				// 拒绝接收文件
				case CommunicationConst.FILE_RELEASEFILE: { 
					BaseActivity.sendEmptyMessage(CommunicationConst.FILE_RELEASEFILE);
					break;
				}
			}
			
			// 每次接受完UDP数据后重置长度
			if (udpResPacket != null) { 
				udpResPacket.setLength(1024);
			}
		}
		if (udpResPacket != null) {
			udpResPacket = null;
		}
		if (udpSocket != null) {
			udpSocket.close();
			udpSocket = null;
		}
		udpThread = null;
	}

}
