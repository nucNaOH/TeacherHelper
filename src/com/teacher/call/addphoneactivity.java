package com.teacher.call;

import com.teacher.dbutil.DBUtil;
import com.teacher.helper.R;
import com.teacher.note.Note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class addphoneactivity extends Activity {
	private EditText callname;
    private EditText callnumber;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		//this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				//WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.addphone);
		callname = (EditText) findViewById(R.id.callname);
		callnumber = (EditText) findViewById(R.id.callnumber);
	}
	public void save(View view){
		String cname = callname.getText().toString();
		String cnumber = callnumber.getText().toString().trim();
		if ("".equals(cname.trim()) || "".equals(cnumber.trim())) {
			Toast.makeText(addphoneactivity.this, "联系人或电话不能为空",
					Toast.LENGTH_SHORT).show();
			return;
		}else {
			//创建Note工具类的对象，获取标题，内容，日期	
            callNote note = new callNote();
            note.setCallname(cname);
			note.setCallnumber(cnumber);
			
			
			//将note添加到数据库中
			long id = DBUtil.addNote(note);
			if (id > 0) {
				Toast.makeText(addphoneactivity.this, "保存成功", Toast.LENGTH_SHORT).show();
			tohome();
			} else {
				Toast.makeText(addphoneactivity.this, "保存失败", Toast.LENGTH_SHORT).show();
			}
		
		
		
		
		
		
		
		}
		
		
	}
	private void tohome(){
		Intent intent = new Intent();
		intent.setClass(addphoneactivity.this, callmanageactivity.class);
		addphoneactivity.this.startActivity(intent);
		addphoneactivity.this.finish();	
		}
	

}
