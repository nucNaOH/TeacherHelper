package com.student.manage;

import java.util.List;
import com.teacher.helper.R;

import com.student.manage.studentlistAdapter.ItemOnClick;
import com.student.manage.studentlistAdapter.ViewHolder;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class StudentlistActivity extends Activity {
	// 声明一个listview
	private String classname;
	private ListView studentlist;
	List<addinfoNote> list;
		// 获取到所有日记
	 private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.studentlist_student);

		  sp = getSharedPreferences("config", MODE_PRIVATE);
			
			
	    classname = sp.getString("classname", "");
        list = DBUtil.getAllstudentNote(classname);
		studentlist = (ListView) findViewById(R.id.studentlist);
		studentlist.setAdapter(new studentlistAdapter(StudentlistActivity.this, list));
		studentlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				//DeleteDialog();
				return false;
			}
			
		});
	}
	public class studentlistAdapter extends BaseAdapter{
		private Context context = null;
		private List<addinfoNote> list1 = null;
		private SharedPreferences sp;
		
		public studentlistAdapter(Context context, List<addinfoNote> list1){
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
				convertView = inflater.inflate(R.layout.studentname_student, null);
				vh.name = (TextView)convertView.findViewById(R.id.name);
				
				vh.number = (TextView)convertView.findViewById(R.id.number);

				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}
			vh.name.setText(list1.get(position).getSname());
		    vh.number.setText(list1.get(position).getSnumber());

			convertView.setOnClickListener(new ItemOnClick(position));
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
				    intent.setClass(context, detilactivity.class);
					Bundle bl = new Bundle();
					bl.putString("id", id+"");
					bl.putString("mark", "2");
					intent.putExtras(bl);
					context.startActivity(intent);
					StudentlistActivity.this.finish();
					
		    	}
			}
			
		public final class ViewHolder {
			public TextView name;
			public TextView number;

			
		}
		

	}

}
