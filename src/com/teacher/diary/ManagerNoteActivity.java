package com.teacher.diary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.teacher.dbutil.DBUtil;
import com.teacher.helper.R;

public class ManagerNoteActivity extends Activity{
	
	
	private Button delete = null; 
	private ListView nolist = null;
	private TextView showtime = null; 
	private TextView show = null; 	
	public static List<String> cbList = new ArrayList<String>();
	List<dailyNote> list = DBUtil.getAlldailyNote();
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_managernode);
		
//		showtime = (TextView) findViewById(R.id.showtime);
//		showtime.setText(DBUtil.getNowTime());
		
		
		delete = (Button) findViewById(R.id.delete);
		delete.setOnClickListener(new OnClickListener(){
			@SuppressLint("NewApi")
			public void onClick(View v) {
				for (int i=0; i<cbList.size(); i++) {
					   if(cbList.get(i).isEmpty()){
						   Toast.makeText(ManagerNoteActivity.this, "请选择要删除的日记", Toast.LENGTH_SHORT).show();   
					   }else{
						  
						DBUtil.deleteNote1(cbList.get(i));
						cbList.clear();
						   
				Toast.makeText(ManagerNoteActivity.this, "删除日记成功!", Toast.LENGTH_SHORT).show();
					   }
					}
				
				
				
				list = DBUtil.getAlldailyNote();
			    
				if (list.size() > 0) {
					show.setVisibility(View.GONE);
				} else {
					show.setVisibility(View.VISIBLE);
				}
				nolist.setAdapter(new ManagerNoteAdapter(ManagerNoteActivity.this, list));
			}
		});
		show = (TextView) findViewById(R.id.show);
		nolist = (ListView) findViewById(R.id.nolist);
		
		if (list.size() > 0) {
			show.setVisibility(View.GONE);
		} else {
			show.setVisibility(View.VISIBLE);
		}
		nolist.setAdapter(new ManagerNoteAdapter(ManagerNoteActivity.this, list));
		
	}
	/**
	 * 返回
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK ) {
			Intent intent = new Intent(ManagerNoteActivity.this, Dailylistactivity.class);
			startActivity(intent);
			ManagerNoteActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	
}
