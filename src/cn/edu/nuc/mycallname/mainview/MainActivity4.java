package cn.edu.nuc.mycallname.mainview;



import com.example.excelParser.CallNameTableSelectActivity;
import com.example.excelParser.ImportExcelActivity;
import com.pwp.activity.CalendarActivity;
import com.student.manage.StudentManageActivity;
import com.teacher.call.callmanageactivity;
import com.teacher.course.TableCourseActivity;
import com.teacher.diary.Dailylistactivity;
import com.teacher.helper.R;

import cn.edu.nuc.mycallname.mainview.TurnplateView.OnTurnplateListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity4 extends Activity implements OnTurnplateListener {
	/** Called when the activity is first created. */

	@Override
	protected void onPause() {
		
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
		
			finish();
			
			
			return true;

		}
		return super.onKeyDown(keyCode, event);
	}
	
	/* 弟子规的标题和内容*/
	TextView tvTitle, tvContent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		setContentView(R.layout.activity_main3);

	
		LinearLayout layout = (LinearLayout) findViewById(R.id.view);
		int width = dm.widthPixels;
		int height = dm.heightPixels;
		TurnplateView view = new TurnplateView(getApplicationContext(),
				3*width / 5, 3*height / 5, width/3);
		layout.addView(view, 3600, 3600);
		view.setOnTurnplateListener(this);
	}

	@Override
	public void onPointTouch(int flag) {//此处添加要触发的事件
		switch (flag) {
		case 0:
			//课程表
			
			Intent intent = new Intent();
			intent.setClass(MainActivity4.this, TableCourseActivity.class);
			startActivity(intent);
			break;
		case 1:
			//班级管理
			Intent intent1 = new Intent();
			intent1.setClass(MainActivity4.this, StudentManageActivity.class);
			startActivity(intent1);
			break;
		case 2:
			//姓名查询
			Intent intent2 = new Intent();
			intent2.setClass(MainActivity4.this, TableCourseActivity.class);
			startActivity(intent2);
			break;
		case 3:
			//数据管理
			Intent intent3 = new Intent();
			intent3.setClass(MainActivity4.this, ImportExcelActivity.class);
			startActivity(intent3);
			break;
		case 4:
			//待办事项
			Intent intent4 = new Intent();
			intent4.setClass(MainActivity4.this, CalendarActivity.class);
			startActivity(intent4);
			break;
		case 5:
			//点名
			Intent intent5 = new Intent();
			intent5.setClass(MainActivity4.this, CallNameTableSelectActivity.class);
			startActivity(intent5);
			break;
		case 6:
			//通讯录
			Intent intent6 = new Intent();
			intent6.setClass(MainActivity4.this, callmanageactivity.class);
			startActivity(intent6);
			break;
		case 7:
			Intent intent7 = new Intent();
			intent7.setClass(MainActivity4.this, TableCourseActivity.class);
			startActivity(intent7);
			break;

		}

	}
}