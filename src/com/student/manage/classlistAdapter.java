package com.student.manage;

import java.util.List;

import com.teacher.helper.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class classlistAdapter extends BaseAdapter {
	private Context context = null;
	List<classNote> list1 = DBUtil.getAllclassNote();

	public classlistAdapter(Context context, List<classNote> list1) {
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
			convertView = inflater.inflate(R.layout.classroom_student, null);
			vh.classroom = (TextView) convertView.findViewById(R.id.classroom);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.classroom.setText(list1.get(position).getClassname()+"°à");
		classlistAdapter.this.notifyDataSetChanged();
		return convertView;
	}


	public final class ViewHolder {
		public TextView classroom;

	}

}
