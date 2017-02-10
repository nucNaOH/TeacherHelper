package com.example.communication.adapter;

import java.util.List;

import com.teacher.helper.R;
import com.example.communication.vo.MyMessage;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * ÿһ����Ϣ�ж�Ӧ��Adapter
 * @author Administrator
 *
 */
public class ChatListAdapter extends BaseAdapter {
	protected LayoutInflater mInflater;
	protected List<MyMessage> msgList;
	protected Resources res;

	public ChatListAdapter(Context c, List<MyMessage> list) {
		super();
		this.mInflater = LayoutInflater.from(c);
		this.msgList = list;
		res = c.getResources();
	}

	@Override
	public int getCount() {
		return msgList.size();
	}

	@Override
	public Object getItem(int position) {
		return msgList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView == null) {
			view = mInflater.inflate(R.layout.chat_item, null);
		} else {
			view = convertView;
		}
		MyMessage msg = msgList.get(position);

		TextView show_name = (TextView) view.findViewById(R.id.show_name);
		show_name.setText(msg.getSenderName());
		if (msg.isSelfMsg()) { // �����Ƿ����Լ�����Ϣ������ɫ
			show_name.setTextColor(res.getColor(R.color.chat_myself));
		} else {
			show_name.setTextColor(res.getColor(R.color.chat_other));
		}

		TextView show_time = (TextView) view.findViewById(R.id.show_time);
		show_time.setText(msg.getTimeStr());

		TextView message = (TextView) view.findViewById(R.id.message);
		message.setText(msg.getMsg());

		return view;
	}

}
