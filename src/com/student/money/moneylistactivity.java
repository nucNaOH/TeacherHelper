package com.student.money;


import java.util.List;

import com.teacher.dbutil.DBUtil;
import com.teacher.helper.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class moneylistactivity extends Activity {
	private TextView in;
	private TextView out;
	private TextView count;
	private ListView list;
	int index = 0;
	
	// 获取到所有课程信息
	
	
	List<moneyNote> list1 = DBUtil.getAllmoneyNote();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moneylist);
		in=(TextView) findViewById(R.id.in);
		out=(TextView) findViewById(R.id.out);
		count=(TextView) findViewById(R.id.count);
		in.setText(String.valueOf(DBUtil.getinCount()));
		out.setText(String.valueOf(DBUtil.getoutCount()));
		count.setText(String.valueOf(DBUtil.getinCount()-DBUtil.getoutCount()));
			
		list = (ListView) findViewById(R.id.listView1);
		list.setAdapter(new readmoneyAdapter(moneylistactivity.this, list1));
		
		list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				index=arg2;
				DeleteDialog();
				return false;
			}

		});
		
		

	}

	private void DeleteDialog() {
		AlertDialog.Builder builder = new Builder(moneylistactivity.this);

		builder.setMessage("确定删除文件");
		builder.setTitle("提示");

		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				 
				for (int i=0; i<list1.size(); i++) {
					
					moneyNote note = list1.get(index);
					DBUtil.deletemoneyNote(String.valueOf(note.getId()));
					list1.clear();
					
					
			     Toast.makeText(moneylistactivity.this, "删除课程!", Toast.LENGTH_SHORT).show();
				}
			
			        list1 = DBUtil.getAllmoneyNote();
					list.setAdapter(new readmoneyAdapter(moneylistactivity.this, list1));
			
			
			}
		});

		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder.show();
	}
	
   
}
