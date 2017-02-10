package com.student.leave;

import java.util.List;

import com.teacher.call.callmanageactivity;
import com.teacher.dbutil.DBUtil;
import com.teacher.helper.R;




import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class readleaveAdapter extends BaseAdapter {
	private Context context = null;
	List<leaveNote> list1 = DBUtil.getAllleaveNote();

	public readleaveAdapter(Context context, List<leaveNote> list1) {
		this.context = context;
		this.list1 = list1;
	}

	@Override
	public int getCount() {
		return list1.size();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (convertView == null) {
			vh = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.reason, null);
			vh.name = (TextView) convertView.findViewById(R.id.textView5);
			vh.date = (TextView) convertView.findViewById(R.id.textView6);
			vh.date2 = (TextView) convertView.findViewById(R.id.textView8);
			vh.reason = (TextView) convertView.findViewById(R.id.textView7);
			

			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.name.setText(list1.get(position).getName());
		vh.date.setText(list1.get(position).getDate());
		vh.date2.setText(list1.get(position).getDate2());
		vh.reason.setText(list1.get(position).getReason());
		
		return convertView;
	}
	class CheckBoxOnCLick implements OnClickListener {
		private int index = 0;
		public CheckBoxOnCLick(int index) {
			this.index = index;
		}
		public void onClick(View v) {
			leaveNote note = list1.get(index);
//			if (courseActivity.cbList.contains(note.getId()+"")) {
//				courseActivity.cbList.remove(note.getId()+"");
//			} else {
//				courseActivity.cbList.add(note.getId()+"");
//			}
		}
	}

	public final class ViewHolder {
		public TextView name;
		public TextView date;
		public TextView date2;
		public TextView reason;
		
	}

}
