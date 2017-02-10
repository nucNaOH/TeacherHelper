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
	 //账目说明
	 private EditText shuoming;
	 //金额
	 private EditText jine;
	 //日期
	 private TextView date;
	 //时间
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
		 // 获取两个RadioButton对象，选择账目
        ru = (RadioButton) findViewById(R.id.ru);
        chu = (RadioButton) findViewById(R.id.chu);
        // 为RadioButton添加监听
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
					
				 if(rb.getText().equals("收入")){
					 srb2=rb.getText().toString().trim();
					 //srb2=rb.setText("null");
				}else if(rb.getText().equals("支出")){
					//支出
					srb=rb.getText().toString();
				}
				}
				String sshuoming = shuoming.getText().toString();
				sjine = jine.getText().toString().trim();
				String sdate = date.getText().toString();
				String stime = time.getText().toString();
				if(ru.isChecked()==false&& chu.isChecked()==false||TextUtils.isEmpty(sshuoming) || TextUtils.isEmpty(sjine) ){
					Toast.makeText(getApplicationContext(), "请输入账目信息", 0).show();
				}else {
				
		            moneyNote note = new moneyNote();
		            note.setRb(srb);
		            note.setOut(srb2);
					note.setShuoming(sshuoming);
					note.setJine(sjine);
					note.setDate(sdate);
					note.setTime(stime);
					
					
					//将note添加到数据库中
					long id = DBUtil.addmoneyNote(note);
					tohome();
					if (id > 0) {
						Toast.makeText(moneyactivity.this, "保存成功", Toast.LENGTH_SHORT).show();
					
					} else {
						Toast.makeText(moneyactivity.this, "保存失败", Toast.LENGTH_SHORT).show();
					}
					
					
					
					
				}
			}
        	
        });
        initTime();
        //将支出、收入的值赋给in，out
         //in =in(sjine);
		//out=out();
		//将小计赋给count
		//count=count();
	}
	  /**
     * 菜单
     */
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, 1, 0, "账目明细");
//		menu.add(0, 4, 0, "退 出");
//		menu.add(0, 5, 0, "关于ColaBox");	
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
			    .setMessage("作者:UntosiL Email:untosil@gmail.com Blog:blog.csdn.net/untosil") 
			    .show();
			return true;
		}
		return false;
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
	  
	    //选择日期
	    public void date(View view){
	    	showDialog(2);
	    }
	    
	  
		/**
		 * 选择时间
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
		 * 初始化日期时间
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
	 * 保存    
	 * @param view
	 */
	
	@SuppressLint("ShowToast")
//	public void save(View view){
//		//if(rb.getText().toString().equals(null)){
//			//Toast.makeText(this, "请选择类型", 0).show();
//		 if(rb.getText().equals("收入")){
//			 srb2=rb.getText().toString().trim();
//			 //srb2=rb.setText("null");
//		}else{
//			//支出
//			srb=rb.getText().toString();
//		}
//		
//		String sshuoming = shuoming.getText().toString();
//		sjine = jine.getText().toString();
//		String sdate = date.getText().toString();
//		String stime = time.getText().toString();
//		//String sbeizhu = beizhu.getText().toString();
//		if(TextUtils.isEmpty(srb) || TextUtils.isEmpty(srb2)&& TextUtils.isEmpty(sshuoming)&& TextUtils.isEmpty(sjine) &&TextUtils.isEmpty(sdate)&&TextUtils.isEmpty(stime)){
//			Toast.makeText(getApplicationContext(), "请输入账目信息", 0).show();
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
//			//将note添加到数据库中
//			long id = DBUtil.addmoneyNote(note);
//			tohome();
//			if (id > 0) {
//				Toast.makeText(moneyactivity.this, "保存成功", Toast.LENGTH_SHORT).show();
//			
//			} else {
//				Toast.makeText(moneyactivity.this, "保存失败", Toast.LENGTH_SHORT).show();
//			}
//			
//			
//			
//			
//		}
//	 }
	 private void tohome(){
		 //将支出、收入、小计的值传进上一个界面
			Intent intent = new Intent();
			intent.setClass(moneyactivity.this, moneylistactivity.class);
			moneyactivity.this.startActivity(intent);
			moneyactivity.this.finish();	
			}
	 /**
	  * 清空
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
	  * 收入总额
	  */
//	 private String in(String data){
//		 int in=0;
//		 if(rb.getText().toString().equals("收入")){
//			in=in+Integer.parseInt(data);
//		 }
//		return String.valueOf(in);
//		 
//	 }
	 /**
	  * 支出总额
	  */
//	 private String out(){
//		 int out=0;
//		 if(rb.getText().toString().equals("支出")){
//			out=out+Integer.parseInt(sjine);
//		 }
//		return String.valueOf(out);
//		 
//	 }
	 /**
	  * 小计
	  */
//	 private String count(){
//		int count =0;
//		count=Integer.parseInt(in)-Integer.parseInt(out);
//		return String.valueOf(count);
//		 
//	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
}
