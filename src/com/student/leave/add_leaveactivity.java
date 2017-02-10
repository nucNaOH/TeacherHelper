package com.student.leave;

import java.util.Calendar;
import java.util.TimeZone;

import com.teacher.dbutil.DBUtil;
import com.teacher.helper.R;
import com.teacher.note.Note;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class add_leaveactivity extends Activity {
	private EditText ed_name;
	private TextView date;
	private TextView date2;
	private TextView tv_date;
	private TextView tv_date2;
	private EditText why;
	private int mYear;
	private int mMonth;
	private int mDay;
	private int Year;
	private int Month;
	private int Day;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addleave);
		ed_name = (EditText) findViewById(R.id.ed_name);
		date = (TextView) findViewById(R.id.date);
		date2 = (TextView) findViewById(R.id.date2);
		tv_date = (TextView) findViewById(R.id.tv_date);
		tv_date2 = (TextView) findViewById(R.id.tv_date2);
		why = (EditText) findViewById(R.id.ed_why);
		 initTime();
		 initTime1();
	}

	/**
	 * ѡ����ʼ����
	 */
	@SuppressWarnings("deprecation")
	public void date(View view) {
		showDialog(1);
	}

	/**
	 * ѡ����ֹ����
	 */
	@SuppressWarnings("deprecation")
	public void time(View view) {
		showDialog(2);
	}

	private void setDatetime() {
		if(Year<mYear){
			Toast.makeText(add_leaveactivity.this, "������������", Toast.LENGTH_SHORT).show();
		}else{
			if(Month<mMonth){
				Toast.makeText(add_leaveactivity.this, "������������", Toast.LENGTH_SHORT).show();
				
			}else{
				if(Day<mDay){
					Toast.makeText(add_leaveactivity.this, "������������", Toast.LENGTH_SHORT).show();
					
				}
				else{
					tv_date.setText(mYear + "-" + mMonth + "-" + mDay);
		          tv_date2.setText(Year + "-" + Month + "-" + Day);
				}
			}
			
		}
		
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {

		case 1:
			return new DatePickerDialog(this, mDateSetListener, mYear,
					mMonth - 1, mDay);
		case 2:
			return new DatePickerDialog(this, DateSetListener, Year,
					Month - 1, Day);
		}
		return null;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {

		case 1:
			((DatePickerDialog) dialog).updateDate(mYear, mMonth - 1, mDay);
			break;
		case 2:
			((DatePickerDialog) dialog).updateDate(Year, Month - 1, Day);
			break;
		}
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear + 1;
			mDay = dayOfMonth;
			

			setDatetime();
		}
	};
	private DatePickerDialog.OnDateSetListener DateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			
			Year = year;
			Month = monthOfYear + 1;
			Day = dayOfMonth;

			setDatetime();
		}
	};
	

	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	/**
	 * ��ʼ������ʱ��
	 */
	private void initTime() {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH) + 1;
		mDay = c.get(Calendar.DAY_OF_MONTH);

	}
	private void initTime1() {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		Year = c.get(Calendar.YEAR);
		Month = c.get(Calendar.MONTH) + 1;
		Day = c.get(Calendar.DAY_OF_MONTH);

	}
	/**
	 * ����
	 */
	public void save(View view){
		String name = ed_name.getText().toString().trim();
		String date = tv_date.getText().toString();
		String date2 = tv_date2.getText().toString();
		String reason = why.getText().toString().trim();
		if (TextUtils.isEmpty(name) || TextUtils.isEmpty(reason)) {
			Toast.makeText(add_leaveactivity.this, "���������ԭ����Ϊ��",
					Toast.LENGTH_SHORT).show();
			return;
		}else{
			//����Note������Ķ��󣬻�ȡ���⣬���ݣ�����	
            leaveNote note = new leaveNote();
            note.setName(name);
			note.setDate(date);
			note.setDate2(date2);
			note.setReason(reason);
			
			//��note��ӵ����ݿ���
			long id = DBUtil.addleaveNote(note);
			if (id > 0) {
				Toast.makeText(add_leaveactivity.this, "����ɹ�", Toast.LENGTH_SHORT).show();
			tohome();
			} else {
				Toast.makeText(add_leaveactivity.this, "����ʧ��", Toast.LENGTH_SHORT).show();
			}
		}
	}
	//����ɹ���ֱ����ת������һ���棬ͬʱfinish���activity
	private void tohome(){
		Intent intent = new Intent();
		intent.setClass(add_leaveactivity.this, leaveactivity.class);
		add_leaveactivity.this.startActivity(intent);
		add_leaveactivity.this.finish();	
		}
	/**
	 * ���
	 */
  public void empty(View view){
	  ed_name.setText("");
	  tv_date.setText("");
	  tv_date2.setText("");
	  why.setText("");
  }
}
