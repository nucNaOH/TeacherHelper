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
	
	    private static final String[] m_bloods = { "����һ", "���ڶ�", "������", "������","������",
	    	"������","������"};
		public Spinner m_Spinner;
		private static final String[] m_bloods1 = { "��һ��", "�ڶ���", "������",
		"���Ľ�", "�����", "������", "���߽�"};
	    public Spinner m_Spinner1;
		private ArrayAdapter<String> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deletecourse);
		 /*
		 * ����
		 */
		m_Spinner = (Spinner) this.findViewById(R.id.spinner1);
		// ����ѡ������ArrayAdapter��������
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, m_bloods);

		// ���������б�ķ��
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// ��adapter ��ӵ�m_Spinner��
		m_Spinner.setAdapter(adapter);

		// ����¼�Spinner�¼�����
		m_Spinner.setOnItemSelectedListener(m_SpinnerListener1);

		// ����Ĭ��ֵ
		m_Spinner.setVisibility(View.VISIBLE);
		
		/*
		 * ��ʾ�ڴ�
		 */
		m_Spinner1 = (Spinner) this.findViewById(R.id.spinner2);
		// ����ѡ������ArrayAdapter��������
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, m_bloods1);

		// ���������б�ķ��
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// ��adapter ��ӵ�m_Spinner��
		m_Spinner1.setAdapter(adapter);

		// ����¼�Spinner�¼�����
		m_Spinner1.setOnItemSelectedListener(m_SpinnerListener1);

		// ����Ĭ��ֵ
		m_Spinner1.setVisibility(View.VISIBLE);
	}
	//��ӵ���¼�
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
				
				//��note��ӵ����ݿ���
				long id = DBUtil.addcourseNote(note);
				if (id > 0) {
					Toast.makeText(DeleteCourseActivity.this, "�h���ɹ�", Toast.LENGTH_SHORT).show();
				tohome();
				} else {
					Toast.makeText(DeleteCourseActivity.this, "�h��ʧ��", Toast.LENGTH_SHORT).show();
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
