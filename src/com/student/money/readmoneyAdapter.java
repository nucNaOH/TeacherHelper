package com.student.money;

import java.util.List;

import com.teacher.dbutil.DBUtil;
import com.teacher.helper.R;




import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class readmoneyAdapter extends BaseAdapter {
	private Context context = null;
	List<moneyNote> list1 = DBUtil.getAllmoneyNote();
	
	ViewHolder vh = null;

	public readmoneyAdapter(Context context, List<moneyNote> list1) {
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
		//ViewHolder vh = null;
		
		if (convertView == null) {
			vh = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.money_list, null);
			vh.leixing = (TextView) convertView.findViewById(R.id.textView2);
			vh.shuoming = (TextView) convertView.findViewById(R.id.textView4);
			vh.jine = (TextView) convertView.findViewById(R.id.textView6);
			vh.riqi = (TextView) convertView.findViewById(R.id.textView8);
			vh.shijian = (TextView) convertView.findViewById(R.id.textView9);
			//vh.beizhu= (TextView) convertView.findViewById(R.id.textView11);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		if(list1.get(position).getRb()!=null ){
			vh.leixing.setText(list1.get(position).getRb());
		}else{
			vh.leixing.setText(list1.get(position).getOut());
		}
		
		vh.shuoming.setText(list1.get(position).getShuoming());
		vh.jine.setText(list1.get(position).getJine());
		vh.riqi.setText(list1.get(position).getDate());
		vh.shijian.setText(list1.get(position).getTime());
		//vh.beizhu.setText(list1.get(position).getBeizhu());
		return convertView;
	}
//	class CheckBoxOnCLick implements OnClickListener {
//		private int index = 0;
//		public CheckBoxOnCLick(int index) {
//			this.index = index;
//		}
//		public void onClick(View v) {
//			moneyNote note = list1.get(index);
////			if (courseActivity.cbList.contains(note.getId()+"")) {
////				courseActivity.cbList.remove(note.getId()+"");
////			} else {
////				courseActivity.cbList.add(note.getId()+"");
////			}
//		}
//	}

	public final class ViewHolder {
		public TextView leixing;
		public TextView shuoming;
		public TextView jine;
		public TextView riqi;
		public TextView shijian;
		public TextView beizhu;
	}
	
	 
}
