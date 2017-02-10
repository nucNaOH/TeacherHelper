package com.teacher.diary;

import java.util.List;

import com.teacher.dbutil.DBUtil;
import com.teacher.helper.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Dailylistactivity extends Activity {
	// 声明一个listview
	private ListView nolist;
	// 获取到所有日记
	List<dailyNote> list = DBUtil.getAlldailyNote();
	//public int MID;

	// SimpleAdapter readrijiAdapter1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dailylist);
		nolist = (ListView) findViewById(R.id.dailylist);
		nolist.setAdapter(new readrijiAdapter1(Dailylistactivity.this, list));
		nolist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

		


			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				DeleteDialog();
				return false;
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1000, 100, 1, "写日记");
		menu.add(1000,101,1,"删除日记");
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 100:
			//创建日记
			Intent intent = new Intent(Dailylistactivity.this,
					dailyactivity.class);
			startActivity(intent);
			Dailylistactivity.this.finish();
			break;
		case 101:
			// 删除日记
			Intent intent1 = new Intent(Dailylistactivity.this,
					ManagerNoteActivity.class);
			startActivity(intent1);
			Dailylistactivity.this.finish();
			break;

		}
		return true;
	}
	
	private void DeleteDialog() {
		AlertDialog.Builder builder = new Builder(Dailylistactivity.this);

		builder.setMessage("确定删除日记");
		builder.setTitle("提示");

		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
		//File构造参数list读取文件路径
		//File file = new File(listItem.get(index));
	//	file.delete();
		//通知adapter 更新
		//adapter = new AAAdapter(this, list);
		//listView.setAdapter(adapter);
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
