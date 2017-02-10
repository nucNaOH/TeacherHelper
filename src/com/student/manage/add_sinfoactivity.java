package com.student.manage;


import java.util.List;

import com.teacher.helper.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class add_sinfoactivity extends Activity {
   private TextView classname;
   //学生学号
   private EditText ed_snumber;
   //学生姓名
   private EditText ed_name;
   //性别
   private RadioButton man;
   private RadioButton woman;
   private RadioButton rb;
   //学生电话
   private EditText ed_sphone;
   //家长电话
   private EditText ed_pphone;
   //家庭住址
   private EditText ed_address;
   //备注
   private EditText ed_beizhu;
   private String classroom;
   private RadioGroup raGroup1; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addsinfo_student);
		//传入班级
		classname=(TextView) findViewById(R.id.classname);
		Intent intent = getIntent();
		Bundle b = intent.getBundleExtra("newname");
		String temp = b.getString("name");
		classname.setText(temp+"班");
		classroom="班"+temp;
		
		ed_snumber=(EditText) findViewById(R.id.ed_snumber);
		ed_name=(EditText) findViewById(R.id.ed_sname);
		 // 获取两个RadioButton对象，选择账目
		man = (RadioButton) findViewById(R.id.man);
		woman = (RadioButton) findViewById(R.id.woman);
        // 为RadioButton添加监听
        man.setOnClickListener(radio_listener);
        woman.setOnClickListener(radio_listener);
		ed_sphone=(EditText) findViewById(R.id.ed_sphone);
		ed_pphone=(EditText) findViewById(R.id.ed_pphone);
		ed_address=(EditText) findViewById(R.id.ed_address);
		ed_beizhu=(EditText) findViewById(R.id.ed_beizhu);
		raGroup1=(RadioGroup) findViewById(R.id.radioGroup1);
		
	}
	 private OnClickListener radio_listener = new OnClickListener() {

	        @Override
	        public void onClick(View v) {
	            // 在RadionButton选中时所要做的操作
	           rb = (RadioButton) v;
	            Toast.makeText(getApplicationContext(), rb.getText(),
	                   Toast.LENGTH_SHORT).show();
	          
	        }
	    };
	/**
	 * 保存学生信息
	 */
   public void sure(View view){
	   String sex = null;
	   String name = ed_name.getText().toString().trim();
	   String number = ed_snumber.getText().toString().trim();
	   if(man.isChecked()|| woman.isChecked() ){
			 if(rb.getText().equals("男")){
				 sex=rb.getText().toString().trim();
			}else if(rb.getText().equals("女")){
				sex=rb.getText().toString();
			}
			}
	   String sphone = ed_sphone.getText().toString().trim();
	   String phone = ed_pphone.getText().toString().trim();
	   String address = ed_address.getText().toString().trim();
	   String beizhu = ed_beizhu.getText().toString().trim();
	   if(TextUtils.isEmpty(name)||TextUtils.isEmpty(number)||man.isChecked()==false&& woman.isChecked()==false||TextUtils.isEmpty(sphone)
			   ||TextUtils.isEmpty(phone) ||TextUtils.isEmpty(address) ){
		   Toast.makeText(add_sinfoactivity.this, "请输入完整的学生信息", 0).show();
	   }else{
		   addinfoNote note = new addinfoNote();
		   note.setSname(name);
		   note.setSnumber(number);
		   note.setSex(sex);
		   note.setSphone(sphone);
		   note.setPhone(phone);
		   note.setAddress(address);
		   note.setBeizhu(beizhu);
		  
		   long id = DBUtil.addsinfoNote(note,classroom);
			if (id > 0) {
				Toast.makeText(add_sinfoactivity.this, "添加成功",
						Toast.LENGTH_SHORT).show();
				ed_name.setText("");
				ed_snumber.setText("");
				ed_sphone.setText("");
				ed_pphone.setText("");
				ed_address.setText("");
				ed_beizhu.setText("");
				man.setChecked(false);
				woman.setChecked(false);
			} else {
				Toast.makeText(add_sinfoactivity.this, "添加失败",
						Toast.LENGTH_SHORT).show();
			}
	   }
	   
	   
   }
}
