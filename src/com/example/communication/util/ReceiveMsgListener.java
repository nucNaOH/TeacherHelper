package com.example.communication.util;

import com.example.communication.vo.MyMessage;

/**
 * 监听接收消息的接口
 * 
 * @author Administrator
 *
 */
public interface ReceiveMsgListener {
	public boolean receive(MyMessage msg);

}
