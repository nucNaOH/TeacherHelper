package com.pwp.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AbsListView.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pwp.borderText.BorderTextView;
import com.pwp.constant.CalendarConstant;
import com.pwp.dao.ScheduleDAO;
import com.pwp.vo.ScheduleVO;
import com.teacher.helper.R;

public class ScheduleInfoView extends Activity {

	private LinearLayout layout = null;
	private BorderTextView textTop = null;
	private BorderTextView info = null;
	private BorderTextView date = null;
	private BorderTextView type = null;
	private EditText editInfo = null;
	private ScheduleDAO dao = null;
	private ScheduleVO scheduleVO = null;
	private Button save=null;
	private String scheduleInfo = "";    //�ճ���Ϣ���޸�ǰ������
	private String scheduleChangeInfo = "";  //�ճ���Ϣ���޸�֮�������
	private final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
	private ArrayList<String> scheduleDate;
	private String[] scheduleIDs;
	private ScheduleVO scheduleVO1;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		dao = new ScheduleDAO(this);
		
        //final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 5, 0, 0);
		layout = new LinearLayout(this); // ʵ�������ֶ���
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setBackgroundResource(R.drawable.schedule_bk);
		layout.setLayoutParams(params);
		
		textTop = new BorderTextView(this, null);
		textTop.setTextColor(Color.BLACK); 
		textTop.setBackgroundResource(R.drawable.top_day);
		textTop.setText("�ճ�����");
		textTop.setHeight(78);
		textTop.setGravity(Gravity.CENTER);

		layout.addView(textTop);
		
		
		Intent intent = getIntent();
		//scheduleID = Integer.parseInt(intent.getStringExtra("scheduleID"));
		//һ�����ڿ��ܶ�Ӧ�������ճ�(scheduleID)
		scheduleDate=intent.getStringArrayListExtra("scheduleDate");
		scheduleVO1=(ScheduleVO) intent.getExtras().getSerializable("scheduleVO");
		//��ʾ�ճ���ϸ��Ϣ
				handlerInfo(scheduleVO1);
		setContentView(layout);
		
				
	}
	
	/**
	 * ��ʾ�ճ�������Ϣ
	 */
	public void handlerInfo(ScheduleVO scheduleVO2){
		BorderTextView date = new BorderTextView(this, null);
		date.setTextColor(Color.BLACK); 
		date.setBackgroundColor(Color.WHITE);
		date.setLayoutParams(params);
		date.setGravity(Gravity.CENTER_VERTICAL);
		date.setHeight(40);
		date.setPadding(10, 0, 10, 0);
		
		BorderTextView type = new BorderTextView(this, null);
		type.setTextColor(Color.BLACK); 
		type.setBackgroundColor(Color.WHITE);
		type.setLayoutParams(params);
		type.setGravity(Gravity.CENTER);
		type.setHeight(67);
		type.setPadding(10, 0, 10, 0);
		type.setTag(scheduleVO2);
		
		final BorderTextView info = new BorderTextView(this, null);
		info.setTextColor(Color.BLACK); 
		info.setBackgroundColor(Color.WHITE);
		info.setGravity(Gravity.CENTER_VERTICAL);
		info.setLayoutParams(params);
		info.setPadding(10, 5, 10, 5);
		
		
		layout.addView(type);
		layout.addView(date);
		layout.addView(info);
		/*Intent intent = getIntent();
		int scheduleID = Integer.parseInt(intent.getStringExtra("scheduleID"));*/
		date.setText(scheduleVO2.getScheduleDate());
		//type.setText(CalendarConstant.sch_type[scheduleVO2.getScheduleTypeID()]);
		info.setText(scheduleVO2.getScheduleContent());
		
		
		
		//��ʱ�䰴ס�ճ�����textview����ʾ�Ƿ�ɾ���ճ���Ϣ
		type.setOnLongClickListener(new OnLongClickListener() {
			
			
			public boolean onLongClick(View v) {

				scheduleVO = (ScheduleVO) v.getTag();
				
				new AlertDialog.Builder(ScheduleInfoView.this).setTitle("ɾ���ճ�").setMessage("ȷ��ɾ��").setPositiveButton("ȷ��", new OnClickListener() {
					
					
					public void onClick(DialogInterface dialog, int which) {
						dao.delete(scheduleVO.getScheduleID());
						Toast.makeText(ScheduleInfoView.this, "�ճ���ɾ��", 0).show();
						ScheduleView.setAlart(ScheduleInfoView.this);
						finish();
					}
				}).setNegativeButton("ȡ��", null).show();
				
				return true;
			}
		});
		
		
	}
}
