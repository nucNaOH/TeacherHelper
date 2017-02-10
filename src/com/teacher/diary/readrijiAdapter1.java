package com.teacher.diary;

import java.util.List;

import com.teacher.helper.R;





import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class readrijiAdapter1 extends BaseAdapter{
	private Context context = null;
	private List<dailyNote> list1 = null;
	
	public readrijiAdapter1(Context context, List<dailyNote> list1){
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (convertView == null) {
			vh = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.readriji, null);
			vh.time = (TextView)convertView.findViewById(R.id.time1);
			vh.notitle = (TextView)convertView.findViewById(R.id.liebiao);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.time.setText(list1.get(position).getRiqi());
		vh.notitle.setText(list1.get(position).getTitle());
		convertView.setOnClickListener(new ItemOnClick(position));
		return convertView;
	}
	
	//µã»÷item
		class ItemOnClick implements OnClickListener {
			int index = 0;
			public ItemOnClick(int index) {
				this.index = index;
			}
			public void onClick(View v) {
	    		int id = list1.get(index).getId();
				Intent intent = new Intent();
			    intent.setClass(context, detilactivity.class);
				Bundle bl = new Bundle();
				bl.putString("id", id+"");
				bl.putString("mark", "2");
				intent.putExtras(bl);
				context.startActivity(intent);		    
	    	}
		}
		
	public final class ViewHolder {
		public TextView time;
		public TextView notitle;
	}
	

}
