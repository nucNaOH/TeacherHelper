package com.teacher.course;

import java.util.List;

import com.teacher.dbutil.DBUtil;
import com.teacher.helper.R;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TableCourseActivity extends Activity {
	// 菜单的选择
	private static final int INSERT_ID = Menu.FIRST;
	private static final int DELETE_ID = Menu.FIRST + 1;
	private int colors[] = { Color.rgb(0xee, 0xff, 0xff),
			Color.rgb(0xf0, 0x96, 0x09), Color.rgb(0x8c, 0xbf, 0x26),
			Color.rgb(0x00, 0xab, 0xa9), Color.rgb(0x99, 0x6c, 0x33),
			Color.rgb(0x3b, 0x92, 0xbc), Color.rgb(0xd5, 0x4d, 0x34),
			Color.rgb(0xcc, 0xcc, 0xcc) };

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_main);

	

		// 分别表示周一到周日
		LinearLayout ll1 = (LinearLayout) findViewById(R.id.ll1);
		LinearLayout ll2 = (LinearLayout) findViewById(R.id.ll2);
		LinearLayout ll3 = (LinearLayout) findViewById(R.id.ll3);
		LinearLayout ll4 = (LinearLayout) findViewById(R.id.ll4);
		LinearLayout ll5 = (LinearLayout) findViewById(R.id.ll5);
		LinearLayout ll6 = (LinearLayout) findViewById(R.id.ll6);
		LinearLayout ll7 = (LinearLayout) findViewById(R.id.ll7);
		// 每天的课程设置
		DBUtil.addinfo();
		if (DBUtil.getCourse().equals("星期一")) {
			for (int i = 1; i <= 7; i++) {
				String classroom = DBUtil.getClass("星期一", "第" + zhuanhuan(i)
						+ "节");
				if (classroom.equals("null" + "  null")) {
					setClass(ll1, "", "", "", "", 2, 3);
				} else {
					setClass(ll1, classroom, "", "", "", 2, 4);
				}

			}

		}
		DBUtil.addinfo2();
		if (DBUtil.getCourse2().equals("星期二")) {
			for (int i = 1; i <= 7; i++) {
				String classroom = DBUtil.getClass("星期二", "第" + zhuanhuan(i)
						+ "节");
				if (classroom.equals("null" + "  null")) {
					setClass(ll2, "", "", "", "", 2, 1);
				} else {
					setClass(ll2, classroom, "", "", "", 2, 2);
				}
			}
		}
		DBUtil.addinfo3();
		if (DBUtil.getCourse3().equals("星期三")) {
			for (int i = 1; i <= 7; i++) {
				String classroom = DBUtil.getClass("星期三", "第" + zhuanhuan(i)
						+ "节");
				if (classroom.equals("null" + "  null")) {
					setClass(ll3, "", "", "", "", 2, 2);
				} else {
					setClass(ll3, classroom, "", "", "", 2, 3);
				}
			}
		}
		DBUtil.addinfo4();
		if (DBUtil.getCourse4().equals("星期四")) {
			for (int i = 1; i <= 7; i++) {
				String classroom = DBUtil.getClass("星期四", "第" + zhuanhuan(i)
						+ "节");
				if (classroom.equals("null" + "  null")) {
					setClass(ll4, "", "", "", "", 2, 3);
				} else {
					setClass(ll4, classroom, "", "", "", 2, 4);
				}
			}
		}
		DBUtil.addinfo5();
		if (DBUtil.getCourse5().equals("星期五")) {
			for (int i = 1; i <= 7; i++) {
				String classroom = DBUtil.getClass("星期五", "第" + zhuanhuan(i)
						+ "节");
				if (classroom.equals("null" + "  null")) {
					setClass(ll5, "", "", "", "", 2, 4);
				} else {
					setClass(ll5, classroom, "", "", "", 2, 5);
				}
			}
		}
		DBUtil.addinfo6();
		if (DBUtil.getCourse6().equals("星期六")) {
			for (int i = 1; i <= 7; i++) {
				String classroom = DBUtil.getClass("星期六", "第" + zhuanhuan(i)
						+ "节");
				if (classroom.equals("null" + "  null")) {
					setClass(ll6, "", "", "", "", 2, 1);
				} else {
					setClass(ll6, classroom, "", "", "", 2, 5);
				}
			}
		}
		DBUtil.addinfo7();
		if (DBUtil.getCourse7().equals("星期日")) {
			for (int i = 1; i <= 7; i++) {
				String classroom = DBUtil.getClass("星期日", "第" + zhuanhuan(i)
						+ "节");
				if (classroom.equals("null" + "  null")) {
					setClass(ll7, "", "", "", "", 2, 2);
				} else {
					setClass(ll7, classroom, "", "", "", 2, 5);
				}
			}
		}

	}

	/**
	 * 设置课程的方法
	 * 
	 * @param ll
	 * @param title
	 *            课程名称
	 * @param place
	 *            地点
	 * @param last
	 *            时间
	 * @param time
	 *            周次
	 * @param classes
	 *            节数
	 * @param color
	 *            背景色
	 */
	void setClass(LinearLayout ll, String title, String place, String last,
			String time, int classes, int color) {
		View view = LayoutInflater.from(this).inflate(R.layout.item, null);
		view.setMinimumHeight(dip2px(this, classes * 48));
		view.setBackgroundColor(colors[color]);
		((TextView) view.findViewById(R.id.title)).setText(title);
		((TextView) view.findViewById(R.id.place)).setText(place);

		// 为课程View设置点击的监听器
		// view.setOnClickListener(new OnClickClassListener());
		TextView blank1 = new TextView(this);
		TextView blank2 = new TextView(this);
		blank1.setHeight(dip2px(this, classes));
		blank2.setHeight(dip2px(this, classes));
		ll.addView(blank1);
		ll.addView(view);
		ll.addView(blank2);
	}

	/**
	 * 设置无课（空百）
	 * 
	 * @param ll
	 * @param classes
	 *            无课的节数（长度）
	 * @param color
	 */
	void setNoClass(LinearLayout ll, int classes, int color) {
		TextView blank = new TextView(this);
		if (color == 0)
			blank.setMinHeight(dip2px(this, classes * 50));
		blank.setBackgroundColor(colors[color]);
		ll.addView(blank);
	}

	// 点击课程的监听器
	// class OnClickClassListener implements OnClickListener {
	//
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// String title;
	//
	// title = (String) ((TextView) v.findViewById(R.id.title)).getText();
	// Toast.makeText(getApplicationContext(), "你点击的是:" + title,
	// Toast.LENGTH_SHORT).show();
	// }
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, INSERT_ID, 0, "添加").setIcon(R.drawable.new_course);
		menu.add(0, DELETE_ID, 0, "删除").setIcon(R.drawable.delete);
		return true;
	}

	// 菜单选择
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case INSERT_ID:
			Intent intent = new Intent();
			intent.setClass(TableCourseActivity.this, CreateCourseActivity.class);
			startActivity(intent);
			TableCourseActivity.this.finish();
			return true;
		case DELETE_ID:
			Intent intent1 = new Intent();
			intent1.setClass(TableCourseActivity.this, DeleteCourseActivity.class);
			startActivity(intent1);
			TableCourseActivity.this.finish();
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}



	public String zhuanhuan(int i) {
		switch (i) {
		case 1:
			return "一";
		case 2:
			return "二";
		case 3:
			return "三";
		case 4:
			return "四";
		case 5:
			return "五";
		case 6:
			return "六";
		case 7:
			return "七";
		}

		return null;

	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/** * 根据手机的分辨率从 px(像素) 的单位 转成为 dp */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

}