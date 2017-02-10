package com.example.communication.activity;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.teacher.helper.R;
import com.example.communication.adapter.ChatListAdapter;
import com.example.communication.net.TcpFileSendThread;
import com.example.communication.util.CommunicationConst;
import com.example.communication.util.CommunicationProtocol;
import com.example.communication.util.ReceiveMsgListener;
import com.example.communication.vo.MyMessage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 聊天窗口视图
 * 
 * @author Administrator
 * 
 */
public class ChatActivity extends BaseActivity implements OnClickListener,
		ReceiveMsgListener {

	// 名字及IP
	private TextView chat_name;
	// 组名
	private TextView chat_group;
	// 退出按钮
	private Button chat_quit;
	// 聊天列表
	private ListView chat_list;
	// 聊天输入框
	private EditText chat_input;
	// 发送按钮
	private Button chat_send;

	// 用于显示的消息list
	private List<MyMessage> msgList;
	// 接收用户的名字
	private String receiverName;
	// 接收用户的IP
	private String receiverIp;
	// 接收用户的组名
	private String receiverGroup;
	// ListView对应的adapter
	private ChatListAdapter adapter;

	private String selfName;
	private String selfGroup;

	// 发送文件
	private final static int MENU_ITEM_SENDFILE = Menu.FIRST;
	// 退出窗口
	private final static int MENU_ITEM_EXIT = Menu.FIRST + 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);

		findViews();

		msgList = new ArrayList<MyMessage>();
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		receiverName = bundle.getString("receiverName");
		receiverIp = bundle.getString("receiverIp");
		receiverGroup = bundle.getString("receiverGroup");
		selfName = "AndroidGuy";
		selfGroup = "android";

		chat_name.setText(receiverName + "(" + receiverIp + ")");
		chat_group.setText("分组：" + "好友圈");
		chat_quit.setOnClickListener(this);
		chat_send.setOnClickListener(this);

		Iterator<MyMessage> it = netThreadHelper.getReceiveMsgQueue()
				.iterator();
		// 循环消息队列，获取队列中与本聊天activity相关信息
		while (it.hasNext()) {
			MyMessage temp = it.next();
			// 若消息队列中的发送者与本activity的消息接收者IP相同，则将这个消息拿出，添加到本activity要显示的消息list中
			if (receiverIp.equals(temp.getSenderIp())) {
				msgList.add(temp);
				// 将本消息从消息队列中移除
				it.remove();
			}
		}

		adapter = new ChatListAdapter(this, msgList);
		chat_list.setAdapter(adapter);
		// 注册到listeners
		netThreadHelper.addReceiveMsgListener(this);
	}

	private void findViews() {
		chat_name = (TextView) findViewById(R.id.chat_name);
		chat_group = (TextView) findViewById(R.id.chat_mood);
		chat_quit = (Button) findViewById(R.id.chat_quit);
		chat_list = (ListView) findViewById(R.id.chat_list);
		chat_input = (EditText) findViewById(R.id.chat_input);
		chat_send = (Button) findViewById(R.id.chat_send);
	}

	@Override
	public void processMessage(Message msg) {
		switch (msg.what) {
		case CommunicationConst.COM_SENDMSG: {
			// 刷新ListView
			adapter.notifyDataSetChanged();
			break;
		}

		// 拒绝接受文件,停止发送文件线程
		case CommunicationConst.FILE_RELEASEFILE: {
			if (TcpFileSendThread.server != null) {
				try {
					TcpFileSendThread.server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
		}

		case CommunicationConst.FILE_SENDSUCCESS: {
			makeTextShort("文件发送成功");
			break;
		}

		}
	}

	@Override
	public boolean receive(MyMessage msg) {
		// 若消息与本activity有关，则接收
		if (receiverIp.equals(msg.getSenderIp())) {
			msgList.add(msg);
			// 使用handle通知，来更新UI
			sendEmptyMessage(CommunicationConst.COM_SENDMSG);
			return true;
		}
		return false;
	}

	@Override
	public void finish() {
		// 一定要移除，不然信息接收会出现问题
		netThreadHelper.removeReceiveMsgListener(this);
		super.finish();
	}

	@Override
	public void onClick(View v) {
		if (v == chat_send) {
			sendAndAddMessage();
		} else if (v == chat_quit) {
			finish();
		}
	}

	/**
	 * 发送消息并将该消息添加到UI显示
	 */
	private void sendAndAddMessage() {
		String msgStr = chat_input.getText().toString().trim();
		if (!"".equals(msgStr)) {
			// 发送消息
			CommunicationProtocol sendMsg = new CommunicationProtocol();
			sendMsg.setVersion(String.valueOf(CommunicationConst.COM_VERSION));
			sendMsg.setSenderName(selfName);
			sendMsg.setSenderHost(selfGroup);
			sendMsg.setCommandNo(CommunicationConst.COM_SENDMSG);
			sendMsg.setAdditionalSection(msgStr);
			InetAddress sendto = null;
			try {
				sendto = InetAddress.getByName(receiverIp);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			if (sendto != null)
				netThreadHelper.sendUdpData(sendMsg.getProtocolString() + "\0",
						sendto, CommunicationConst.COM_PORT);

			// 添加消息到显示list
			MyMessage selfMsg = new MyMessage("localhost", selfName, msgStr,new Date());
			// 设置为自身消息
			selfMsg.setSelfMsg(true);
			msgList.add(selfMsg);
		} else {
			makeTextShort("不能发送空内容");
		}

		chat_input.setText("");
		adapter.notifyDataSetChanged();// 更新UI
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_ITEM_SENDFILE, 0, "发送文件");
		menu.add(0, MENU_ITEM_EXIT, 0, "回到主页面");

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case MENU_ITEM_SENDFILE:{
				Intent intent = new Intent(this, FileActivity.class);
				startActivityForResult(intent, 0);
				break;
			}
				
			case MENU_ITEM_EXIT:{
				finish();
				break;
			}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			// 得到发送文件的路径
			Bundle bundle = data.getExtras();
			String filePaths = bundle.getString("filePaths");
			String[] filePathArray = filePaths.split("\0");

			// 发送传送文件UDP数据报
			CommunicationProtocol sendPro = new CommunicationProtocol();
			sendPro.setVersion("" + CommunicationConst.COM_VERSION);
			sendPro.setCommandNo(CommunicationConst.COM_SENDMSG
					| CommunicationConst.FILE_ATTACHFILE);
			sendPro.setSenderName(selfName);
			sendPro.setSenderHost(selfGroup);
			String msgStr = ""; // 发送的消息

			// 用于组合附加文件格式
			StringBuffer additionInfoSb = new StringBuffer();
			for (String path : filePathArray) {
				File file = new File(path);
				additionInfoSb.append("0:");
				additionInfoSb.append(file.getName() + ":");
				// 文件的大小用十六进制表示
				additionInfoSb.append(Long.toHexString(file.length()) + ":");
				// 文件创建时间，现在暂时已最后修改时间替代
				additionInfoSb.append(Long.toHexString(file.lastModified())+ ":");
				additionInfoSb.append(CommunicationConst.FILE_REGULAR + ":");
				// 用于分隔多个发送文件的字符
				byte[] bt = { 0x07 };
				String splitStr = new String(bt);
				additionInfoSb.append(splitStr);
			}

			sendPro.setAdditionalSection(msgStr + "\0"
					+ additionInfoSb.toString() + "\0");

			InetAddress sendto = null;
			try {
				sendto = InetAddress.getByName(receiverIp);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			if (sendto != null)
				netThreadHelper.sendUdpData(sendPro.getProtocolString(),
						sendto, CommunicationConst.COM_PORT);

			Thread netTcpFileSendThread = new Thread(new TcpFileSendThread(filePathArray));
			netTcpFileSendThread.start(); // 启动线程
		}
	}

}
