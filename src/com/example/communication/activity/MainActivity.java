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
 * ������
 * 
 * @author Administrator
 * 
 */
public class MainActivity extends BaseActivity implements OnClickListener {
	public static String hostIp;

	private ExpandableListView userList;

	private UserExpandableListAdapter adapter;
	private List<String> strGroups; // ����һ���˵����Ƽ���
	private List<List<User>> children;

	private TextView totalUser;
	private Button refreshButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);


		findViews();

		// ����һ���˵����Ƽ���
		strGroups = new ArrayList<String>();
		// �����˵����Ƽ���
		children = new ArrayList<List<User>>();

		// ��ʼ��������
		netThreadHelper.connectSocket();
		// �㲥����
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

	// �������ݺ�UI��ʾ
	private void refreshViews() {
		// �������
		strGroups.clear();
		children.clear();

		Map<String, User> currentUsers = new HashMap<String, User>();
		currentUsers.putAll(netThreadHelper.getUsers());
		Queue<MyMessage> msgQueue = netThreadHelper.getReceiveMsgQueue();
		// IP��ַ��δ����Ϣ������map
		Map<String, Integer> ip2Msg = new HashMap<String, Integer>(); 
		// ������Ϣ���У����ip2Msg
		Iterator<MyMessage> it = msgQueue.iterator();
		while (it.hasNext()) {
			MyMessage chatMsg = it.next();
			// �õ���Ϣ������IP
			String ip = chatMsg.getSenderIp();
			Integer tempInt = ip2Msg.get(ip);
			// ��map��û��IP��Ӧ����Ϣ����,���IP��ӽ�ȥ,ֵΪ1
			if (tempInt == null) {
				ip2Msg.put(ip, 1);
			} else {
				// ���Ѿ��ж�ӦIP������ֵ��һ
				ip2Msg.put(ip, ip2Msg.get(ip) + 1);
			}
		}

		// ����currentUsers,����strGroups��children
		Iterator<String> iterator = currentUsers.keySet().iterator();
		while (iterator.hasNext()) {
			User user = currentUsers.get(iterator.next());
			// ����ÿ�������û���Ӧ��δ����Ϣ����
			if (ip2Msg.get(user.getIp()) == null) {
				user.setMsgCount(0);
			} else {
				user.setMsgCount(ip2Msg.get(user.getIp()));
			}

			String groupName = user.getGroupName();
			int index = strGroups.indexOf(groupName);
			// û����Ӧ���飬����ӷ��飬����Ӷ�Ӧchild
			if (index == -1) {
				strGroups.add(groupName);

				List<User> childData = new ArrayList<User>();
				childData.add(user);
				children.add(childData);
				// �Ѵ��ڷ��飬�򽫶�Ӧchild��ӵ����Ӧ������
			} else {
				children.get(index).add(user);
			}

		}

		adapter.notifyDataSetChanged(); // ����ExpandableListView

		String countStr = "��ǰ����" + currentUsers.size() + "���û�";
		totalUser.setText(countStr); // ����TextView

	}

	@Override
	public void onClick(View v) {
		if (v.equals(refreshButton)) {
			netThreadHelper.refreshUsers();
			refreshViews();
		}
	}



}