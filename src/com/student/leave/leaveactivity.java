package com.student.leave;

import java.util.List;

import com.teacher.dbutil.DBUtil;
import com.teacher.helper.R;
import com.teacher.note.Note;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class leaveactivity extends Activity {
	private ListView leave_list;
	List<leaveNote> list1 = DBUtil.getAllleaveNote();
	int index = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leave);
		leave_list=(ListView) findViewById(R.id.leave_list);
		leave_list.setAdapter(new readleaveAdapter(leaveactivity.this, list1));
		leave_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

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
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1000, 100, 1, "添加记录");
		return true;
	}

	// 对退出菜单进行处理
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 100:
			// 创建对话框
			Intent intent = new Intent(leaveactivity.this, add_leaveactivity.class);
			startActivity(intent);
			leaveactivity.this.finish();
			break;

		}
		return true;
	}
	private void DeleteDialog() {
		AlertDialog.Builder builder = new Builder(leaveactivity.this);

		builder.setMessage("确定删除该记录");
		builder.setTitle("提示");

		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				 
				for (int i=0; i<list1.size(); i++) {
					
					leaveNote note = list1.get(index);
					DBUtil.deleteleaveNote(String.valueOf(note.getId()));
					list1.clear();
					
					
			     Toast.makeText(leaveactivity.this, "删除请假记录!", Toast.LENGTH_SHORT).show();
				}
			
			        list1 = DBUtil.getAllleaveNote();
			        leave_list.setAdapter(new readleaveAdapter(leaveactivity.this, list1));
			
			
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
