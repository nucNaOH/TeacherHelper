package com.student.manage;

import android.app.Activity;
import com.teacher.helper.R;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class updateinfoactivity extends Activity {
    private EditText name;
    private EditText number;
    private RadioButton man;
    private RadioButton woman;
    private RadioButton rb;
    private EditText sphone;
    private EditText phone;
    private EditText address;
    private EditText beizhu;
    private String mark = "1";
	private SharedPreferences sp;
	private String classname;
	private String id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.updateinfo_student);
		name=(EditText) findViewById(R.id.ed_name);
		number=(EditText) findViewById(R.id.ed_number);
		//sex=(EditText) findViewById(R.id.ed_name);
		sphone=(EditText) findViewById(R.id.ed_sphone);
		phone=(EditText) findViewById(R.id.ed_phone);
		address=(EditText) findViewById(R.id.ed_address);
		beizhu=(EditText) findViewById(R.id.ed_beizhu);
		man = (RadioButton) findViewById(R.id.man);
		woman = (RadioButton) findViewById(R.id.woman);
        // 为RadioButton添加监听
        man.setOnClickListener(radio_listener);
        woman.setOnClickListener(radio_listener);
        
        
        sp = getSharedPreferences("config", MODE_PRIVATE);
		Intent intent = getIntent();
		
		classname = sp.getString("classname", "");
	 id = intent.getExtras().getString("id");
		mark = intent.getExtras().getString("mark");
		addinfoNote note = DBUtil.getNoteById(id,classname);
		
		name.setText(note.getSname());
		number.setText(note.getSnumber());
		if(note.getSex().equals("男")){
		man.performClick();
			
		}else{
			woman.performClick();
		}
	    sphone.setText(note.getSphone());
	    phone.setText(note.getPhone());
	    address.setText(note.getAddress());
	    beizhu.setText(note.getBeizhu());
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
	     * 保存修改的信息
	     * @param view
	     */
    public void save(View view){
       String sex = null;
  	   String sname = name.getText().toString().trim();
  	   String snumber =number.getText().toString().trim();
  	   if(man.isChecked()|| woman.isChecked() ){
  			
  			 if(rb.getText().equals("男")){
  				 sex=rb.getText().toString().trim();
  				 //srb2=rb.setText("null");
  			}else if(rb.getText().equals("女")){
  				//支出
  				sex=rb.getText().toString();
  			}
  			}
  	   String edsphone = sphone.getText().toString().trim();
  	   String edphone = phone.getText().toString().trim();
  	   String edaddress = address.getText().toString().trim();
  	   String edbeizhu = beizhu.getText().toString().trim();
  	   DBUtil.updateinfo(classname, sname, snumber, sex, edsphone, edphone, edaddress, edbeizhu,id);
  	   Toast.makeText(updateinfoactivity.this, "修改成功！", 0).show();
  	   //返回到学生列表界面
  	   Intent intent = new Intent();
  	   intent.setClass(updateinfoactivity.this, StudentlistActivity.class);
  	   startActivity(intent);
  	 updateinfoactivity.this.finish();
    }

}
