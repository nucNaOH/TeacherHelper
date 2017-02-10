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
 * ���촰����ͼ
 * 
 * @author Administrator
 * 
 */
public class ChatActivity extends BaseActivity implements OnClickListener,
		ReceiveMsgListener {

	// ���ּ�IP
	private TextView chat_name;
	// ����
	private TextView chat_group;
	// �˳���ť
	private Button chat_quit;
	// �����б�
	private ListView chat_list;
	// ���������
	private EditText chat_input;
	// ���Ͱ�ť
	private Button chat_send;

	// ������ʾ����Ϣlist
	private List<MyMessage> msgList;
	// �����û�������
	private String receiverName;
	// �����û���IP
	private String receiverIp;
	// �����û�������
	private String receiverGroup;
	// ListView��Ӧ��adapter
	private ChatListAdapter adapter;

	private String selfName;
	private String selfGroup;

	// �����ļ�
	private final static int MENU_ITEM_SENDFILE = Menu.FIRST;
	// �˳�����
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
		chat_group.setText("���飺" + "����Ȧ");
		chat_quit.setOnClickListener(this);
		chat_send.setOnClickListener(this);

		Iterator<MyMessage> it = netThreadHelper.getReceiveMsgQueue()
				.iterator();
		// ѭ����Ϣ���У���ȡ�������뱾����activity�����Ϣ
		while (it.hasNext()) {
			MyMessage temp = it.next();
			// ����Ϣ�����еķ������뱾activity����Ϣ������IP��ͬ���������Ϣ�ó�����ӵ���activityҪ��ʾ����Ϣlist��
			if (receiverIp.equals(temp.getSenderIp())) {
				msgList.add(temp);
				// ������Ϣ����Ϣ�������Ƴ�
				it.remove();
			}
		}

		adapter = new ChatListAdapter(this, msgList);
		chat_list.setAdapter(adapter);
		// ע�ᵽlisteners
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
			// ˢ��ListView
			adapter.notifyDataSetChanged();
			break;
		}

		// �ܾ������ļ�,ֹͣ�����ļ��߳�
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
			makeTextShort("�ļ����ͳɹ�");
			break;
		}

		}
	}

	@Override
	public boolean receive(MyMessage msg) {
		// ����Ϣ�뱾activity�йأ������
		if (receiverIp.equals(msg.getSenderIp())) {
			msgList.add(msg);
			// ʹ��handle֪ͨ��������UI
			sendEmptyMessage(CommunicationConst.COM_SENDMSG);
			return true;
		}
		return false;
	}

	@Override
	public void finish() {
		// һ��Ҫ�Ƴ�����Ȼ��Ϣ���ջ��������
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
	 * ������Ϣ��������Ϣ��ӵ�UI��ʾ
	 */
	private void sendAndAddMessage() {
		String msgStr = chat_input.getText().toString().trim();
		if (!"".equals(msgStr)) {
			// ������Ϣ
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

			// �����Ϣ����ʾlist
			MyMessage selfMsg = new MyMessage("localhost", selfName, msgStr,new Date());
			// ����Ϊ������Ϣ
			selfMsg.setSelfMsg(true);
			msgList.add(selfMsg);
		} else {
			makeTextShort("���ܷ��Ϳ�����");
		}

		chat_input.setText("");
		adapter.notifyDataSetChanged();// ����UI
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_ITEM_SENDFILE, 0, "�����ļ�");
		menu.add(0, MENU_ITEM_EXIT, 0, "�ص���ҳ��");

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
			// �õ������ļ���·��
			Bundle bundle = data.getExtras();
			String filePaths = bundle.getString("filePaths");
			String[] filePathArray = filePaths.split("\0");

			// ���ʹ����ļ�UDP���ݱ�
			CommunicationProtocol sendPro = new CommunicationProtocol();
			sendPro.setVersion("" + CommunicationConst.COM_VERSION);
			sendPro.setCommandNo(CommunicationConst.COM_SENDMSG
					| CommunicationConst.FILE_ATTACHFILE);
			sendPro.setSenderName(selfName);
			sendPro.setSenderHost(selfGroup);
			String msgStr = ""; // ���͵���Ϣ

			// ������ϸ����ļ���ʽ
			StringBuffer additionInfoSb = new StringBuffer();
			for (String path : filePathArray) {
				File file = new File(path);
				additionInfoSb.append("0:");
				additionInfoSb.append(file.getName() + ":");
				// �ļ��Ĵ�С��ʮ�����Ʊ�ʾ
				additionInfoSb.append(Long.toHexString(file.length()) + ":");
				// �ļ�����ʱ�䣬������ʱ������޸�ʱ�����
				additionInfoSb.append(Long.toHexString(file.lastModified())+ ":");
				additionInfoSb.append(CommunicationConst.FILE_REGULAR + ":");
				// ���ڷָ���������ļ����ַ�
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
			netTcpFileSendThread.start(); // �����߳�
		}
	}

}
