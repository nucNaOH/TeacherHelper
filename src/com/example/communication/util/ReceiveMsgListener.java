package com.example.communication.util;

import com.example.communication.vo.MyMessage;

/**
 * ����������Ϣ�Ľӿ�
 * 
 * @author Administrator
 *
 */
public interface ReceiveMsgListener {
	public boolean receive(MyMessage msg);

}
