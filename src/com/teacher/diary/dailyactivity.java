package com.teacher.diary;

import com.teacher.dbutil.DBUtil;
import com.teacher.helper.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class dailyactivity extends Activity {
	//������Ŀ������
     private EditText title1;
     private EditText content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.diary_edit);
		title1=(EditText) findViewById(R.id.title);
		content=(EditText) findViewById(R.id.content);
		
	}
	public void save(View view){
		String title = title1.getText().toString();
		String body = content.getText().toString();
		if(TextUtils.isEmpty(title) && TextUtils.isEmpty(body)){
			Toast.makeText(getApplicationContext(), "�������ռǱ��������", 0).show();
		}else{
			
            dailyNote note = new dailyNote();
            note.setTitle(title);
			note.setContent(body);
			//note.setClassroom(classroom);
			
			
			//��note��ӵ����ݿ���
			long id = DBUtil.addDaily(note);
			if (id > 0) {
				Toast.makeText(dailyactivity.this, "����ɹ�", Toast.LENGTH_SHORT).show();
			tohome();
			} else {
				Toast.makeText(dailyactivity.this, "����ʧ��", Toast.LENGTH_SHORT).show();
			}
			
			
			
			
		}
		
		
		
	}
	private void tohome(){
		Intent intent = new Intent();
		intent.setClass(dailyactivity.this, Dailylistactivity.class);
		dailyactivity.this.startActivity(intent);
		dailyactivity.this.finish();	
		}

}
