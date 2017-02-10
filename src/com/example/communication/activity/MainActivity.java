package com.example.communication.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;


import com.teacher.helper.R;
import com.example.communication.adapter.UserExpandableListAdapter;
import com.example.communication.util.CommunicationConst;
import com.example.communication.vo.MyMessage;
import com.example.communication.vo.User;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

/**
 * 主界面
 * 
 * @author Administrator
 * 
 */
public class MainActivity extends BaseActivity implements OnClickListener {
	public static String hostIp;

	private ExpandableListView userList;

	private UserExpandableListAdapter adapter;
	private List<String> strGroups; // 所有一级菜单名称集合
	private List<List<User>> children;

	private TextView totalUser;
	private Button refreshButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);


		findViews();

		// 所有一级菜单名称集合
		strGroups = new ArrayList<String>();
		// 二级菜单名称集合
		children = new ArrayList<List<User>>();

		// 开始监听数据
		netThreadHelper.connectSocket();
		// 广播上线
		netThreadHelper.noticeLine("online");

		adapter = new UserExpandableListAdapter(this, strGroups, children);
		userList.setAdapter(adapter);

		refreshButton.setOnClickListener(this);
		refreshViews();
	}

	@Override
	public void finish() {
		super.finish();
		netThreadHelper.noticeLine("offline");
		netThreadHelper.disconnectSocket();

	}

	private void findViews() {
		totalUser = (TextView) findViewById(R.id.totalUser);
		userList = (ExpandableListView) findViewById(R.id.userlist);
		refreshButton = (Button) findViewById(R.id.refresh);
	}

	@Override
	public void processMessage(Message msg) {
		switch (msg.what) {
		case CommunicationConst.COM_ONLINE:
		case CommunicationConst.COM_OFFLINE:
		case CommunicationConst.COM_ANSONLINE:
		case CommunicationConst.COM_SENDMSG:
			refreshViews();
			break;
		}
	}

	// 更新数据和UI显示
	private void refreshViews() {
		// 清空数据
		strGroups.clear();
		children.clear();

		Map<String, User> currentUsers = new HashMap<String, User>();
		currentUsers.putAll(netThreadHelper.getUsers());
		Queue<MyMessage> msgQueue = netThreadHelper.getReceiveMsgQueue();
		// IP地址与未收消息个数的map
		Map<String, Integer> ip2Msg = new HashMap<String, Integer>(); 
		// 遍历消息队列，填充ip2Msg
		Iterator<MyMessage> it = msgQueue.iterator();
		while (it.hasNext()) {
			MyMessage chatMsg = it.next();
			// 得到消息发送者IP
			String ip = chatMsg.getSenderIp();
			Integer tempInt = ip2Msg.get(ip);
			// 若map中没有IP对应的消息个数,则把IP添加进去,值为1
			if (tempInt == null) {
				ip2Msg.put(ip, 1);
			} else {
				// 若已经有对应IP，则将其值加一
				ip2Msg.put(ip, ip2Msg.get(ip) + 1);
			}
		}

		// 遍历currentUsers,更新strGroups和children
		Iterator<String> iterator = currentUsers.keySet().iterator();
		while (iterator.hasNext()) {
			User user = currentUsers.get(iterator.next());
			// 设置每个在线用户对应的未收消息个数
			if (ip2Msg.get(user.getIp()) == null) {
				user.setMsgCount(0);
			} else {
				user.setMsgCount(ip2Msg.get(user.getIp()));
			}

			String groupName = user.getGroupName();
			int index = strGroups.indexOf(groupName);
			// 没有相应分组，则添加分组，并添加对应child
			if (index == -1) {
				strGroups.add(groupName);

				List<User> childData = new ArrayList<User>();
				childData.add(user);
				children.add(childData);
				// 已存在分组，则将对应child添加到相对应分组中
			} else {
				children.get(index).add(user);
			}

		}

		adapter.notifyDataSetChanged(); // 更新ExpandableListView

		String countStr = "当前在线" + currentUsers.size() + "个用户";
		totalUser.setText(countStr); // 更新TextView

	}

	@Override
	public void onClick(View v) {
		if (v.equals(refreshButton)) {
			netThreadHelper.refreshUsers();
			refreshViews();
		}
	}



}