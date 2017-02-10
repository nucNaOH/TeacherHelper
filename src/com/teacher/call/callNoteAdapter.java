package com.teacher.call;
import java.util.HashMap;
import java.util.List;

import com.teacher.call.callmanageactivity;
import com.teacher.helper.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;



public class callNoteAdapter extends BaseAdapter{
	
	private Context context = null;
	private List<callNote> list = null;
	ViewHolder vh = null;
	public callNoteAdapter(Context context, List<callNote> list){
		this.context = context;
		this.list = list;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
			vh = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.activity_nolist_manager_item, null);
			//使用viewHolder来寻找控件
			vh.callname = (TextView)convertView.findViewById(R.id.name);
			vh.callnumber = (TextView)convertView.findViewById(R.id.phonenumber);
			vh.cb = (CheckBox) convertView.findViewById(R.id.cb);
			convertView.setTag(vh);
		} else {
			//填充数据
			vh = (ViewHolder) convertView.getTag();
		}
		//绑定数据到控件
		vh.callname.setText(list.get(position).getCallname());
		vh.callnumber.setText(list.get(position).getCallnumber());
		vh.cb.setOnClickListener(new CheckBoxOnCLick(position));
		//convertView.setOnClickListener(new ItemOnClick(position));
		return convertView;
	}
	class CheckBoxOnCLick implements OnClickListener {
		private int index = 0;
		public CheckBoxOnCLick(int index) {
			this.index = index;
		}
	
		public void onClick(View v) {

			callNote note = list.get(index);
			if (callmanageactivity.cbList.contains(note.getId()+"")) {
				callmanageactivity.cbList.remove(note.getId()+"");
			} else {
				callmanageactivity.cbList.add(note.getId()+"");
			}
		}
	}
	//首先创建viewHolder
	public final class ViewHolder {
		public TextView callname;
		public TextView callnumber;
		public CheckBox cb;
	}
}
