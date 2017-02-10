package com.teacher.helper;






import cn.edu.nuc.mycallname.mainview.MainActivity4;

import com.teacher.dbutil.DBUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Enteractivity extends Activity {
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
		setContentView(R.layout.login);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		edpassword=(EditText) findViewById(R.id.password);
	}
    public void login(View view){
    	//showSetupPwdDialog();
    	//�ж��Ƿ����ù�����
    			if(isSetupPwd()){
    				//�Ѿ����������ˣ�������������Ի���
    				showEnterDialog();
    			}else{
    				//û���������룬����������������Ի���
    				showSetupPwdDialog();
    			}
    }
	/**
	 * ��������Ի���
	 */
	private void showSetupPwdDialog() {
		DBUtil.initialmoneyNote();
		AlertDialog.Builder builder = new Builder(Enteractivity.this);
		// �Զ���һ�������ļ�
		View view = View.inflate(Enteractivity.this, R.layout.dialog_setup_password, null);
		et_setup_pwd = (EditText) view.findViewById(R.id.et_setup_pwd);
		et_setup_confirm = (EditText) view.findViewById(R.id.et_setup_confirm);
		ok = (Button) view.findViewById(R.id.ok);
		cancel = (Button) view.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//������Ի���ȡ����
				dialog.dismiss();
			}
		});
		ok.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				
				//  ȡ������
				String password = et_setup_pwd.getText().toString().trim();
				String password_confirm = et_setup_confirm.getText().toString().trim();
				if(TextUtils.isEmpty(password) || TextUtils.isEmpty(password_confirm)){
					Toast.makeText(Enteractivity.this, "����Ϊ��", 0).show();
					return;
				}
				//�ж��Ƿ�һ�²�ȥ����
				if(password.equals(password_confirm)){
					//һ�µĻ����ͱ������룬�ѶԻ�����������Ҫ�����ֻ�����ҳ��
					Editor editor = sp.edit();
					editor.putString("password", password);
					editor.commit();
					dialog.dismiss();
					//Log.i(TAG, "һ�µĻ����ͱ������룬�ѶԻ�����������Ҫ�����ֻ�����ҳ��");
					//Intent intent = new Intent(MainActivity.this,LostFindActivity.class);
					//startActivity(intent);
					Toast.makeText(Enteractivity.this, "�������óɹ��������Ʊ������룡", 0).show();
				}else{
					
					Toast.makeText(Enteractivity.this, "���벻һ��", 0).show();
					return ;
				}
				
				
				
			}
		});
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
		
	}
	/**
	 * ��������Ի���
	 */
	private void showEnterDialog() {
		
		AlertDialog.Builder builder = new Builder(Enteractivity.this);
		// �Զ���һ�������ļ�
		View view = View.inflate(Enteractivity.this, R.layout.dialog_enter_password, null);
		et_setup_pwd = (EditText) view.findViewById(R.id.et_setup_pwd);
		ok = (Button) view.findViewById(R.id.ok);
		cancel = (Button) view.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//������Ի���ȡ����
				dialog.dismiss();
			}
		});
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//  ȡ������
				String password = et_setup_pwd.getText().toString().trim();
				String savePassword = sp.getString("password", "");//ȡ�����ܺ��
				if(TextUtils.isEmpty(password)){
					Toast.makeText(Enteractivity.this, "����Ϊ��", 1).show();
					return;
				}
				
				if(password.equals(savePassword)){
					//�������������֮ǰ���õ�����
					//�ѶԻ���������������ҳ�棻
					dialog.dismiss();
					//Log.i(TAG, "�ѶԻ��������������ֻ�����ҳ��");
					Intent intent = new Intent(Enteractivity.this,MainActivity4.class);
					startActivity(intent);
					Enteractivity.this.finish();
					
				}else{
					Toast.makeText(Enteractivity.this, "�������", 1).show();
					et_setup_pwd.setText("");
					return;
				}
				
				
				
			}
		});
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
		
	
		
	}
	/**
	 * �ж��Ƿ����ù�����
	 * @return
	 */
	private boolean isSetupPwd(){
		String password = sp.getString("password", null);
//		if(TextUtils.isEmpty(password)){
//			return false;
//		}else{
//			return true;
//		}
		return !TextUtils.isEmpty(password);
		
	}

}
