package com.teacher.course;

import com.teacher.dbutil.DBUtil;
import com.teacher.helper.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class DeleteCourseActivity extends Activity {
	
	    private static final String[] m_bloods = { "星期一", "星期二", "星期三", "星期四","星期五",
	    	"星期六","星期日"};
		public Spinner m_Spinner;
		private static final String[] m_bloods1 = { "第一节", "第二节", "第三节",
		"第四节", "第五节", "第六节", "第七节"};
	    public Spinner m_Spinner1;
		private ArrayAdapter<String> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deletecourse);
		 /*
		 * 星期
		 */
		m_Spinner = (Spinner) this.findViewById(R.id.spinner1);
		// 将可选内容与ArrayAdapter连接起来
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, m_bloods);

		// 设置下拉列表的风格
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到m_Spinner中
		m_Spinner.setAdapter(adapter);

		// 添加事件Spinner事件监听
		m_Spinner.setOnItemSelectedListener(m_SpinnerListener1);

		// 设置默认值
		m_Spinner.setVisibility(View.VISIBLE);
		
		/*
		 * 显示节次
		 */
		m_Spinner1 = (Spinner) this.findViewById(R.id.spinner2);
		// 将可选内容与ArrayAdapter连接起来
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, m_bloods1);

		// 设置下拉列表的风格
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到m_Spinner中
		m_Spinner1.setAdapter(adapter);

		// 添加事件Spinner事件监听
		m_Spinner1.setOnItemSelectedListener(m_SpinnerListener1);

		// 设置默认值
		m_Spinner1.setVisibility(View.VISIBLE);
	}
	//添加点击事件
		private Spinner.OnItemSelectedListener m_SpinnerListener1 = new Spinner.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		};
		public void delete(View view){
			String xqi = m_Spinner.getSelectedItem().toString();;
			String jieci = m_Spinner1.getSelectedItem().toString();
			 courseNote note = new courseNote();
	            note.setXingqi(xqi);
				note.setJieci(jieci);
				note.setClassroom(" ");
				note.setBeizhu(" ");
				
				//将note添加到数据库中
				long id = DBUtil.addcourseNote(note);
				if (id > 0) {
					Toast.makeText(DeleteCourseActivity.this, "刪除成功", Toast.LENGTH_SHORT).show();
				tohome();
				} else {
					Toast.makeText(DeleteCourseActivity.this, "刪除失败", Toast.LENGTH_SHORT).show();
				}
			
		}
		private void tohome(){
			Intent intent = new Intent();
			intent.setClass(DeleteCourseActivity.this, TableCourseActivity.class);
			DeleteCourseActivity.this.startActivity(intent);
			DeleteCourseActivity.this.finish();	
			}
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				Intent intent = new Intent();
				intent.setClass(DeleteCourseActivity.this, TableCourseActivity.class);
				DeleteCourseActivity.this.startActivity(intent);
				DeleteCourseActivity.this.finish();	
				return true;
			}
			return super.onKeyDown(keyCode, event);
		}
		
}
