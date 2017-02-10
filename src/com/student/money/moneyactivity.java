package com.student.money;

import java.util.Calendar;
import java.util.TimeZone;

import com.student.leave.add_leaveactivity;
import com.student.leave.leaveactivity;
import com.teacher.dbutil.DBUtil;
import com.teacher.helper.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class moneyactivity extends Activity {
	 private RadioButton ru;
	 private RadioButton chu;
	 //��Ŀ˵��
	 private EditText shuoming;
	 //���
	 private EditText jine;
	 //����
	 private TextView date;
	 //ʱ��
	 private TextView time;
	
	 
	 private RadioButton rb;
	
	 private Button save;
	    private int mYear;
		private int mMonth;
		private int mDay;
		private int mHour;
		private int mMinute;
		
		private String sjine;
//		private String in;
//		private String out;
//		private String count;
		private String srb;
		private String srb2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.money);
		 // ��ȡ����RadioButton����ѡ����Ŀ
        ru = (RadioButton) findViewById(R.id.ru);
        chu = (RadioButton) findViewById(R.id.chu);
        // ΪRadioButton��Ӽ���
        ru.setOnClickListener(radio_listener);
        chu.setOnClickListener(radio_listener);
        shuoming=(EditText) findViewById(R.id.edzhangmu);
        jine = (EditText) findViewById(R.id.editText1);
        date = (TextView) findViewById(R.id.tvdate);
        time = (TextView) findViewById(R.id.tvtime);
      
        save = (Button) findViewById(R.id.BtnSave);
        save.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if(ru.isChecked()|| chu.isChecked() ){
					
				 if(rb.getText().equals("����")){
					 srb2=rb.getText().toString().trim();
					 //srb2=rb.setText("null");
				}else if(rb.getText().equals("֧��")){
					//֧��
					srb=rb.getText().toString();
				}
				}
				String sshuoming = shuoming.getText().toString();
				sjine = jine.getText().toString().trim();
				String sdate = date.getText().toString();
				String stime = time.getText().toString();
				if(ru.isChecked()==false&& chu.isChecked()==false||TextUtils.isEmpty(sshuoming) || TextUtils.isEmpty(sjine) ){
					Toast.makeText(getApplicationContext(), "��������Ŀ��Ϣ", 0).show();
				}else {
				
		            moneyNote note = new moneyNote();
		            note.setRb(srb);
		            note.setOut(srb2);
					note.setShuoming(sshuoming);
					note.setJine(sjine);
					note.setDate(sdate);
					note.setTime(stime);
					
					
					//��note��ӵ����ݿ���
					long id = DBUtil.addmoneyNote(note);
					tohome();
					if (id > 0) {
						Toast.makeText(moneyactivity.this, "����ɹ�", Toast.LENGTH_SHORT).show();
					
					} else {
						Toast.makeText(moneyactivity.this, "����ʧ��", Toast.LENGTH_SHORT).show();
					}
					
					
					
					
				}
			}
        	
        });
        initTime();
        //��֧���������ֵ����in��out
         //in =in(sjine);
		//out=out();
		//��С�Ƹ���count
		//count=count();
	}
	  /**
     * �˵�
     */
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, 1, 0, "��Ŀ��ϸ");
//		menu.add(0, 4, 0, "�� ��");
//		menu.add(0, 5, 0, "����ColaBox");	
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			
			Intent intent = new Intent();
			intent.setClass(moneyactivity.this,moneylistactivity.class);;
			startActivity(intent);
			
			return true;
		case 2:
			
			int nOrientation = getRequestedOrientation();  
			if (nOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)  
			  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  
			else  
			  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			    
			return true;
		case 3:
			
			return true;
		case 4:
			
		case 5:
			  new AlertDialog.Builder(this) 
			    .setTitle("ColaBox") 
			    .setMessage("����:UntosiL Email:untosil@gmail.com Blog:blog.csdn.net/untosil") 
			    .show();
			return true;
		}
		return false;
	}
	 private OnClickListener radio_listener = new OnClickListener() {

	        @Override
	        public void onClick(View v) {
	            // ��RadionButtonѡ��ʱ��Ҫ���Ĳ���
	           rb = (RadioButton) v;
	            Toast.makeText(getApplicationContext(), rb.getText(),
	                   Toast.LENGTH_SHORT).show();
	          
	        }
	    };
	  
	    //ѡ������
	    public void date(View view){
	    	showDialog(2);
	    }
	    
	  
		/**
		 * ѡ��ʱ��
		 */
		public void time1(View view) {
			showDialog(1);
		} 
		private void setDatetime(){
			date.setText(mYear+"-"+mMonth+"-"+mDay);
	        time.setText(pad(mHour)+":"+pad(mMinute));
		}
		
		@Override
	    protected Dialog onCreateDialog(int id) {
	        switch (id) {
	            case 1:
	                return new TimePickerDialog(this,
	                        mTimeSetListener, mHour, mMinute, false);
	            case 2:
	                return new DatePickerDialog(this,
	                            mDateSetListener,
	                            mYear, mMonth-1, mDay);
	        }
	        return null;
	    }

	    @Override
	    protected void onPrepareDialog(int id, Dialog dialog) {
	        switch (id) {
	            case 1:            	
	                ((TimePickerDialog) dialog).updateTime(mHour, mMinute);
	                break;
	            case 2:
	                ((DatePickerDialog) dialog).updateDate(mYear, mMonth-1, mDay);
	                break;
	        }
	    }    
		
	    private DatePickerDialog.OnDateSetListener mDateSetListener =
	        new DatePickerDialog.OnDateSetListener() {

	            public void onDateSet(DatePicker view, int year, int monthOfYear,
	                    int dayOfMonth) {
	                mYear = year;
	                mMonth = monthOfYear+1;
	                mDay = dayOfMonth;

	                setDatetime();
	            }
	        };

	    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
	        new TimePickerDialog.OnTimeSetListener() {

	            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	                mHour = hourOfDay;
	                mMinute = minute;
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
			mHour = c.get(Calendar.HOUR_OF_DAY);
			mMinute = c.get(Calendar.MINUTE);
		}

	/**
	 * ����    
	 * @param view
	 */
	
	@SuppressLint("ShowToast")
//	public void save(View view){
//		//if(rb.getText().toString().equals(null)){
//			//Toast.makeText(this, "��ѡ������", 0).show();
//		 if(rb.getText().equals("����")){
//			 srb2=rb.getText().toString().trim();
//			 //srb2=rb.setText("null");
//		}else{
//			//֧��
//			srb=rb.getText().toString();
//		}
//		
//		String sshuoming = shuoming.getText().toString();
//		sjine = jine.getText().toString();
//		String sdate = date.getText().toString();
//		String stime = time.getText().toString();
//		//String sbeizhu = beizhu.getText().toString();
//		if(TextUtils.isEmpty(srb) || TextUtils.isEmpty(srb2)&& TextUtils.isEmpty(sshuoming)&& TextUtils.isEmpty(sjine) &&TextUtils.isEmpty(sdate)&&TextUtils.isEmpty(stime)){
//			Toast.makeText(getApplicationContext(), "��������Ŀ��Ϣ", 0).show();
//		}else{
//			
//            moneyNote note = new moneyNote();
//            note.setRb(srb);
//            note.setOut(srb2);
//			note.setShuoming(sshuoming);
//			note.setJine(sjine);
//			note.setDate(sdate);
//			note.setTime(stime);
//		//	note.setBeizhu(sbeizhu);
//			
//			
//			//��note��ӵ����ݿ���
//			long id = DBUtil.addmoneyNote(note);
//			tohome();
//			if (id > 0) {
//				Toast.makeText(moneyactivity.this, "����ɹ�", Toast.LENGTH_SHORT).show();
//			
//			} else {
//				Toast.makeText(moneyactivity.this, "����ʧ��", Toast.LENGTH_SHORT).show();
//			}
//			
//			
//			
//			
//		}
//	 }
	 private void tohome(){
		 //��֧�������롢С�Ƶ�ֵ������һ������
			Intent intent = new Intent();
			intent.setClass(moneyactivity.this, moneylistactivity.class);
			moneyactivity.this.startActivity(intent);
			moneyactivity.this.finish();	
			}
	 /**
	  * ���
	  * @param view
	  */
	 public void clean(View view){
		 //rb.setText("");
		 shuoming.setText("");
		 jine.setText("");
		 date.setText("");
		 time.setText("");
		
	 }
	 /**
	  * �����ܶ�
	  */
//	 private String in(String data){
//		 int in=0;
//		 if(rb.getText().toString().equals("����")){
//			in=in+Integer.parseInt(data);
//		 }
//		return String.valueOf(in);
//		 
//	 }
	 /**
	  * ֧���ܶ�
	  */
//	 private String out(){
//		 int out=0;
//		 if(rb.getText().toString().equals("֧��")){
//			out=out+Integer.parseInt(sjine);
//		 }
//		return String.valueOf(out);
//		 
//	 }
	 /**
	  * С��
	  */
//	 private String count(){
//		int count =0;
//		count=Integer.parseInt(in)-Integer.parseInt(out);
//		return String.valueOf(count);
//		 
//	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
}
