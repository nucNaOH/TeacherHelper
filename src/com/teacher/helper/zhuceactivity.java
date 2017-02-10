package com.teacher.helper;



import com.student.leave.add_leaveactivity;
import com.student.leave.leaveNote;
import com.teacher.dbutil.DBUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class zhuceactivity extends Activity {
	EditText password = null;
	EditText number = null;
	EditText sure = null;
	private String textnumber;
	protected SharedPreferences spCount;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhece1);
		
	}
	public void sure(View view){
		// 输入用户名和密码
				number = (EditText) findViewById(R.id.uname); // 首先获取用来输入用户名的组件
				String textnumber = number.getText().toString();
				password = (EditText) findViewById(R.id.password); // 同时也需要获取输入密码的组件
				String textpassword = password.getText().toString();
				sure = (EditText) findViewById(R.id.qpassword); // 同时也需要获取输入密码的组件
				String textsure = sure.getText().toString();
				if(textsure.equals(textpassword)){
					//获取本应用程序的spcount对象
				spCount = getSharedPreferences("counter",  0);
				spCount.edit()
				.putString("username", textnumber.toString())
		        .putString("userpassword", textpassword.toString())
				.commit();
				if (TextUtils.isEmpty(textpassword)
						|| TextUtils.isEmpty(textnumber)) {
					Toast.makeText(zhuceactivity.this, "账号或密码不能为空",
							Toast.LENGTH_SHORT).show();
					return;
				} else {
					if(DBUtil.DB_NAME!=null){
					deleteDatabase("mycourse");
					}
					Toast.makeText(zhuceactivity.this, "注册成功,请妥善保管个人信息",
							Toast.LENGTH_SHORT).show();
					
			
					tohome();

				}
						
				}else{
					Toast.makeText(zhuceactivity.this, "请重新输入密码",
							Toast.LENGTH_SHORT).show();
					
				}
		
		
		
	}
//	private void createbiao() {
//		DBUtil.db = zhuceactivity.this.openOrCreateDatabase(DBUtil.DB_NAME+textnumber,
//				MODE_PRIVATE, null); // 数据库私有
//		DBUtil.db.execSQL(DBUtil.CREATE_TABLE);
//		DBUtil.db.execSQL(DBUtil.CREATE_TABLE1);
//		DBUtil.db.execSQL(DBUtil.CREATE_TABLE2);
//		DBUtil.db.execSQL(DBUtil.CREATE_TABLE3);
//		DBUtil.db.execSQL(DBUtil.CREATE_TABLE4);
//		DBUtil.db.execSQL(DBUtil.CREATE_TABLE5);
//		DBUtil.db.execSQL(DBUtil.CREATE_TABLE8);
//		DBUtil.db.execSQL(DBUtil.CREATE_TABLE7);
//	
//	}
//	
	//注册后返回登陆界面
		private void tohome(){
		Intent intent = new Intent();
		intent.setClass(zhuceactivity.this, Enteractivity.class);
		zhuceactivity.this.startActivity(intent);
		zhuceactivity.this.finish();		
		}
		
}
