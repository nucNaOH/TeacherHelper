package com.teacher.schoolweb;


import java.util.List;

import com.student.leave.add_leaveactivity;
import com.student.leave.leaveNote;
import com.student.leave.leaveactivity;
import com.student.leave.readleaveAdapter;
import com.teacher.dbutil.DBUtil;
import com.teacher.helper.R;
import com.teacher.note.Note;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class schoolactivity extends Activity {
    private EditText http;
    private ListView weblist;
    List<schoolwebNote> list1 = DBUtil.getAllschoolwebNote();
	int index = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.school);
		http=(EditText) findViewById(R.id.edxxhttp);
		weblist=(ListView) findViewById(R.id.weblist);
		weblist.setAdapter(new readschoolwebAdapter(schoolactivity.this, list1));
		weblist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				index=arg2;
				DeleteDialog();
				return false;
			}

		});
		weblist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				index=arg2;
//			
				  list1 = DBUtil.getAllschoolwebNote();
			       weblist.setAdapter(new readschoolwebAdapter(schoolactivity.this, list1));
				for (int i=0; i<list1.size(); i++) {
				schoolwebNote note = list1.get(index);
				String schoolhttp = String.valueOf(note.getSchoolWeb());
				
				Intent intent = new Intent( schoolactivity.this,httpactivity.class);
				Bundle bd = new Bundle();
				bd.putString("xxhttp",schoolhttp);
				intent.putExtra("http", bd);
				startActivity(intent);
				
				
				
				
				
//				
				}
				
				
				
			}

		});
	}
	
	
	public void go(View view){
		String schoolhttp = http.getText().toString().trim();
		Intent i = new Intent( schoolactivity.this,httpactivity.class);
		Bundle bd = new Bundle();
		bd.putString("xxhttp",schoolhttp);
		i.putExtra("http", bd);
		startActivity(i);
		if(TextUtils.isEmpty(schoolhttp)){
			Toast.makeText(schoolactivity.this, "请输入网址", Toast.LENGTH_SHORT).show();
		}else {
			if(list1.contains(schoolhttp)){
				Toast.makeText(schoolactivity.this, "已存在该网址", Toast.LENGTH_SHORT).show();
			}else{	
		schoolwebNote note = new schoolwebNote();
        note.setSchoolWeb(schoolhttp);
        long id = DBUtil.addschoolwebNote(note);
        if (id > 0) {
			Toast.makeText(schoolactivity.this, "保存成功", Toast.LENGTH_SHORT).show();
        }
		}
		}
	
        List<schoolwebNote> list1 = DBUtil.getAllschoolwebNote();
        weblist.setAdapter(new readschoolwebAdapter(schoolactivity.this, list1));
	}
	private void DeleteDialog() {
		AlertDialog.Builder builder = new Builder(schoolactivity.this);

		builder.setMessage("确定删除网站");
		builder.setTitle("提示");

		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				 
				for (int i=0; i<list1.size(); i++) {
					
					schoolwebNote note = list1.get(index);
					DBUtil.deleteschoolwebNote(String.valueOf(note.getId()));
					list1.clear();
					
					
			     Toast.makeText(schoolactivity.this, "删除!", Toast.LENGTH_SHORT).show();
				}
			
			        list1 = DBUtil.getAllschoolwebNote();
			        weblist.setAdapter(new readschoolwebAdapter(schoolactivity.this, list1));
			
			
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
