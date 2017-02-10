package com.teacher.diary;

import com.teacher.dbutil.DBUtil;
import com.teacher.helper.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class detilactivity extends Activity {
	private TextView title = null;
	private TextView showtime = null;
	private TextView content = null;

	private String mark = "1";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_detail_note);
		Intent intent = getIntent();
		String id = intent.getExtras().getString("id");
		mark = intent.getExtras().getString("mark");
		dailyNote note = DBUtil.getNoteById(id);

		title = (TextView) findViewById(R.id.title);
		title.setText(note.getTitle());
		
		
		showtime = (TextView) findViewById(R.id.showtime);
		showtime.setText(note.getRiqi());
		
		
		
		content = (TextView) findViewById(R.id.content);
		content.setText(note.getContent());
	}
	

}
