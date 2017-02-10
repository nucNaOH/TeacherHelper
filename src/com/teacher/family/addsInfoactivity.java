package com.teacher.family;

import com.teacher.dbutil.DBUtil;
import com.teacher.helper.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class addsInfoactivity extends Activity {
	private EditText name;
    private EditText pname;
    private EditText phonenumber;
    private EditText address;
    private EditText beizhu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addsinfo);
		name=(EditText) findViewById(R.id.edsname);
		pname=(EditText) findViewById(R.id.edpname);
		phonenumber=(EditText) findViewById(R.id.ednumber);
		address=(EditText) findViewById(R.id.edaddress);
		beizhu=(EditText) findViewById(R.id.edbeizhu);
		
	}
	@SuppressLint("ShowToast")
	public void save(View view){
		String sname = name.getText().toString();
		String spname = pname.getText().toString();
		String spnumber = phonenumber.getText().toString();
		String saddress = address.getText().toString();
		String sbeizhu = beizhu.getText().toString();
		if(TextUtils.isEmpty(sname) && TextUtils.isEmpty(spname) && TextUtils.isEmpty(spnumber) && TextUtils.isEmpty(sbeizhu)){
			Toast.makeText(getApplicationContext(), "������ѧ����Ϣ", 0).show();
		}else{
			
            studentNote note = new studentNote();
            note.setName(sname);
			note.setPname(spname);
			note.setPhonenumber(spnumber);
			note.setAddress(saddress);
			note.setBeizhu(sbeizhu);
			
			
			//��note��ӵ����ݿ���
			long id = DBUtil.addinfoNote(note);
			if (id > 0) {
				Toast.makeText(addsInfoactivity.this, "����ɹ�", Toast.LENGTH_SHORT).show();
			tohome();
			} else {
				Toast.makeText(addsInfoactivity.this, "����ʧ��", Toast.LENGTH_SHORT).show();
			}
			
			
			
			
		}
		
		
	}
	private void tohome(){
			Intent intent = new Intent();
			intent.setClass(addsInfoactivity.this, sInfoactivity.class);
			addsInfoactivity.this.startActivity(intent);
			addsInfoactivity.this.finish();	
			}

}
