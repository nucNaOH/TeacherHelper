package com.student.manage;



import com.teacher.helper.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

public class detilactivity extends Activity {
	private TextView name = null;
	private TextView number = null;
	private TextView sex = null;
	private TextView sphone = null;
	private TextView phone = null;
	private TextView address = null;
	private TextView beizhu = null;
	private String mark = "1";
	private SharedPreferences sp;
	private String id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.student_student);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		Intent intent = getIntent();
		
		String classname = sp.getString("classname", "");//ȡ�����ܺ��
		 id = intent.getExtras().getString("id");
		mark = intent.getExtras().getString("mark");
		addinfoNote note = DBUtil.getNoteById(id,classname);
		name = (TextView)findViewById(R.id.name);
		name.setText("������"+note.getSname());
		
		number = (TextView)findViewById(R.id.number);
		number.setText("ѧ�ţ�"+note.getSnumber());
		
		sex = (TextView)findViewById(R.id.sex);
		sex.setText("�Ա�"+note.getSex());
		
      sphone = (TextView)findViewById(R.id.sphone);
      sphone.setText("ѧ���绰��"+note.getSphone());
      
      phone = (TextView)findViewById(R.id.phone);
      phone.setText("�ҳ��绰��"+note.getPhone());
       
       address = (TextView)findViewById(R.id.address);
       address.setText("��ͥסַ��"+note.getAddress());
        
       beizhu = (TextView)findViewById(R.id.beizhu);
       beizhu.setText("��ע��"+note.getBeizhu());
      
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1000, 100, 1, "�޸�ѧ����Ϣ");
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 100:
          Intent intent = new Intent();
          intent.setClass(detilactivity.this, updateinfoactivity.class);
          Bundle bl = new Bundle();
			bl.putString("id", id+"");
			bl.putString("mark", "2");
			intent.putExtras(bl);
          startActivity(intent);
          detilactivity.this.finish();
			break;

		}
		return true;
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.setClass(detilactivity.this, StudentlistActivity.class);
			startActivity(intent);
			detilactivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
