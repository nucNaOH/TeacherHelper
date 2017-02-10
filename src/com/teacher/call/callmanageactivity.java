package com.teacher.call;

import java.util.ArrayList;
import java.util.List;

import com.teacher.dbutil.DBUtil;
import com.teacher.helper.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class callmanageactivity extends Activity {
	private ListView calllist = null;
	public static List<String> cbList = new ArrayList<String>();
	List<callNote> list = DBUtil.getAllcallNote(); 
	private int index =0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.callmanage);
		calllist = (ListView) findViewById(R.id.call);
		calllist.setAdapter(new callNoteAdapter(callmanageactivity.this, list));
		calllist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				index = arg2;
				

			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1000, 100, 1, "添加");
		menu.add(1000, 101, 2, "删除");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 100:
			// 添加联系人
			Intent intent = new Intent(callmanageactivity.this,
					addphoneactivity.class);
			startActivity(intent);
			callmanageactivity.this.finish();
			break;
		case 101:
			// 删除联系人
			for (int i = 0; i < cbList.size(); i++) {

				DBUtil.deleteNote2(cbList.get(i));
				cbList.clear();
				Toast.makeText(callmanageactivity.this, "删除联系人",
						Toast.LENGTH_SHORT).show();
			}

			list = DBUtil.getAllcallNote();

			calllist.setAdapter(new callNoteAdapter(callmanageactivity.this,
					list));

			break;

		}
		return true;
	}

	/**
	 * 拨打电话
	 * 
	 * @param view
	 */
	public void boda(View view) {

		//for (int i = 0; i < cbList.size(); i++) {
			int j = 0;
			j = Integer.parseInt(cbList.get(index));
		
			callNote note = list.get(j - 1);

			String callnumber = note.getCallnumber();

			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:" + callnumber));
			startActivity(intent);
		}
		
	public void select(View view) {

		//for (int i = 0; i < cbList.size(); i++) {
			

			Intent intent = new Intent();
			intent.setClass(callmanageactivity.this, contactactivity.class);
			startActivity(intent);
		}
	//}

}
