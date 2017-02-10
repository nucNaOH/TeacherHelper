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
	// ����һ��listview
	private ListView nolist;
	// ��ȡ�������ռ�
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
		menu.add(1000, 100, 1, "д�ռ�");
		menu.add(1000,101,1,"ɾ���ռ�");
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 100:
			//�����ռ�
			Intent intent = new Intent(Dailylistactivity.this,
					dailyactivity.class);
			startActivity(intent);
			Dailylistactivity.this.finish();
			break;
		case 101:
			// ɾ���ռ�
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

		builder.setMessage("ȷ��ɾ���ռ�");
		builder.setTitle("��ʾ");

		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
		//File�������list��ȡ�ļ�·��
		//File file = new File(listItem.get(index));
	//	file.delete();
		//֪ͨadapter ����
		//adapter = new AAAdapter(this, list);
		//listView.setAdapter(adapter);
		}
		});

		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {

		}
		});
		builder.show();
	}

}
