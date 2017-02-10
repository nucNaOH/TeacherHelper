package com.teacher.helper;

import cn.edu.nuc.mycallname.mainview.MainActivity4;

import com.teacher.dbutil.DBUtil;

import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WelcomeActivity extends Activity {

	private EditText edpassword;
	private EditText et_setup_pwd;
	private EditText et_setup_confirm;
	private Button ok;
	private Button cancel;
	private AlertDialog dialog;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_main);
		
		createbiao();
		
//		PackageManager pm = getPackageManager();
//		try {
//			@SuppressWarnings("unused")
//			PackageInfo pi = pm.getPackageInfo("com.lyt.android", 0);
//
//		} catch (NameNotFoundException e) {
//			e.printStackTrace();
//		}

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
//				Intent intent = new Intent(WelcomeActivity.this,
//						Enteractivity.class);
//				startActivity(intent);
//				WelcomeActivity.this.finish();
			}

		}, 1700);
		if(isSetupPwd()){
			//已经设置密码了，弹出的是输入对话框
			showEnterDialog();
		}else{
			//没有设置密码，弹出的是设置密码对话框
			showSetupPwdDialog();
		}
	}
	private void showSetupPwdDialog() {
		DBUtil.initialmoneyNote();
		AlertDialog.Builder builder = new Builder(WelcomeActivity.this);
		// 自定义一个布局文件
		View view = View.inflate(WelcomeActivity.this, R.layout.dialog_setup_password, null);
		et_setup_pwd = (EditText) view.findViewById(R.id.et_setup_pwd);
		et_setup_confirm = (EditText) view.findViewById(R.id.et_setup_confirm);
		ok = (Button) view.findViewById(R.id.ok);
		cancel = (Button) view.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//把这个对话框取消掉
				dialog.dismiss();
			}
		});
		ok.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				
				//  取出密码
				String password = et_setup_pwd.getText().toString().trim();
				String password_confirm = et_setup_confirm.getText().toString().trim();
				if(TextUtils.isEmpty(password) || TextUtils.isEmpty(password_confirm)){
					Toast.makeText(WelcomeActivity.this, "密码为空", 0).show();
					return;
				}
				//判断是否一致才去保存
				if(password.equals(password_confirm)){
					//一致的话，就保存密码，把对话框消掉，还要进入手机防盗页面
					Editor editor = sp.edit();
					editor.putString("password", password);
					editor.commit();
					dialog.dismiss();
					//Log.i(TAG, "一致的话，就保存密码，把对话框消掉，还要进入手机防盗页面");
					//Intent intent = new Intent(MainActivity.this,LostFindActivity.class);
					//startActivity(intent);
					Toast.makeText(WelcomeActivity.this, "密码设置成功，请妥善保管密码！", 0).show();
				}else{
					
					Toast.makeText(WelcomeActivity.this, "密码不一致", 0).show();
					return ;
				}
				
				Intent intent = new Intent(WelcomeActivity.this,MainActivity4.class);
				startActivity(intent);
				WelcomeActivity.this.finish();
			}
		});
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.setTitle("首次使用请设置密码");
		dialog.show();
		
	}
	/**
	 * 输入密码对话框
	 */
	private void showEnterDialog() {
		
		AlertDialog.Builder builder = new Builder(WelcomeActivity.this);
		// 自定义一个布局文件
		View view = View.inflate(WelcomeActivity.this, R.layout.dialog_enter_password, null);
		et_setup_pwd = (EditText) view.findViewById(R.id.et_setup_pwd);
		ok = (Button) view.findViewById(R.id.ok);
		cancel = (Button) view.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//把这个对话框取消掉
				dialog.dismiss();
			}
		});
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//  取出密码
				String password = et_setup_pwd.getText().toString().trim();
				String savePassword = sp.getString("password", "");//取出加密后的
				if(TextUtils.isEmpty(password)){
					Toast.makeText(WelcomeActivity.this, "密码为空", 1).show();
					return;
				}
				
				if(password.equals(savePassword)){
					//输入的密码是我之前设置的密码
					//把对话框消掉，进入主页面；
					dialog.dismiss();
					//Log.i(TAG, "把对话框消掉，进入手机防盗页面");
					Intent intent = new Intent(WelcomeActivity.this,MainActivity4.class);
					startActivity(intent);
					WelcomeActivity.this.finish();
					
				}else{
					Toast.makeText(WelcomeActivity.this, "密码错误", 1).show();
					et_setup_pwd.setText("");
					return;
				}
				
			}
		});
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.setTitle("请输入密码");
		dialog.show();
		
	}
	private boolean isSetupPwd(){
		String password = sp.getString("password", null);
//		if(TextUtils.isEmpty(password)){
//			return false;
//		}else{
//			return true;
//		}
		return !TextUtils.isEmpty(password);
		
	}
    
	// 创建数据库
	private void createbiao() {

		if (DBUtil.db == null) {
			DBUtil.db = WelcomeActivity.this.openOrCreateDatabase(DBUtil.DB_NAME,
					MODE_PRIVATE, null); // 数据库私有
			//DBUtil.db.execSQL(DBUtil.CREATE_TABLE);
			DBUtil.db.execSQL(DBUtil.CREATE_TABLE1);
			DBUtil.db.execSQL(DBUtil.CREATE_TABLE2);
			//DBUtil.db.execSQL(DBUtil.CREATE_TABLE3);
			DBUtil.db.execSQL(DBUtil.CREATE_TABLE4);
			//DBUtil.db.execSQL(DBUtil.CREATE_TABLE5);
			DBUtil.db.execSQL(DBUtil.CREATE_TABLE8);
			DBUtil.db.execSQL(DBUtil.CREATE_TABLE7);
			DBUtil.db.execSQL(DBUtil.CREATE_TABLE9);
			DBUtil.db.execSQL(DBUtil.CREATE_TABLE10);
		}
	}

}
