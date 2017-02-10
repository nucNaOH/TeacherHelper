package com.teacher.schoolweb;

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

public class readschoolwebAdapter extends BaseAdapter {
	private Context context = null;
	List<schoolwebNote> list1 = DBUtil.getAllschoolwebNote();

	public readschoolwebAdapter(Context context, List<schoolwebNote> list1) {
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
			convertView = inflater.inflate(R.layout.schoolweb_list, null);
			vh.schoolweb = (TextView) convertView.findViewById(R.id.weblist);
			
			

			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.schoolweb.setText(list1.get(position).getSchoolWeb());
		
		
		return convertView;
	}
//	class CheckBoxOnCLick implements OnClickListener {
//		private int index = 0;
//		public CheckBoxOnCLick(int index) {
//			this.index = index;
//		}
//		public void onClick(View v) {
//			leaveNote note = list1.get(index);
////			if (courseActivity.cbList.contains(note.getId()+"")) {
////				courseActivity.cbList.remove(note.getId()+"");
////			} else {
////				courseActivity.cbList.add(note.getId()+"");
////			}
//		}
//	}

	public final class ViewHolder {
		public TextView schoolweb;
		
		
	}

}
