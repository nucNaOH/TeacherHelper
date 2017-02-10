package com.teacher.family;

import java.util.List;

import com.student.leave.leaveNote;
import com.teacher.dbutil.DBUtil;
import com.teacher.helper.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class sinfoAdapter extends BaseAdapter{
	private Context context = null;
	private List<studentNote> list1 = null;
	protected SharedPreferences spCount;
	private DBUtil db;
	int index = 0;
	private sinfoAdapter adapter;
	public sinfoAdapter(Context context, List<studentNote> list1){
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
			convertView = inflater.inflate(R.layout.studentlist, null);
			vh.name = (TextView)convertView.findViewById(R.id.name);
			vh.iv_delete = (ImageView)convertView.findViewById(R.id.iv_delete);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.name.setText(list1.get(position).getName());
		convertView.setOnClickListener(new ItemOnClick(position));
		vh.iv_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new Builder(context);
				builder.setTitle("警告");
				builder.setMessage("确定要删除这条记录么？");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//删除数据库的内容
					    
						studentNote note = list1.get(index);
						DBUtil.deleteNote4(String.valueOf(note.getId()));
						//db.deleteNote4(list1.get(position));
						//更新界面。
						list1.remove(index);
						//通知listview数据适配器更新
						adapter=new sinfoAdapter(context, list1);
						adapter.notifyDataSetChanged();
						list1.clear();
					    list1 = DBUtil.getAllsinfoNote();
					    spCount = context.getSharedPreferences("counter",  0);
						spCount.edit()
						.putString("deleteitem", "1")
						.commit();
					    
					}
				});
				builder.setNegativeButton("取消", null);
				builder.show();
			}
		});
		return convertView;
		
	}
	
	//点击item
		class ItemOnClick implements OnClickListener {
			int index = 0;
			public ItemOnClick(int index) {
				this.index = index;
			}
			public void onClick(View v) {
	    		int id = list1.get(index).getId();
				Intent intent = new Intent();
			    intent.setClass(context, detilsinfoactivity.class);
				Bundle bl = new Bundle();
				bl.putString("id", id+"");
				bl.putString("mark", "2");
				intent.putExtras(bl);
				context.startActivity(intent);		    
	    	}
		}
		
	public final class ViewHolder {
		public TextView name;
		public ImageView iv_delete;
	}
	
}
