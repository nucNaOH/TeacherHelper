package com.teacher.schoolweb;


import com.teacher.helper.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebView;

public class httpactivity extends Activity {
	//创建SharedPreferences的一个对象
	//public SharedPreferences spCount;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schoolweb);
		WebView wv= (WebView) findViewById(R.id.wv);
		Intent i = getIntent();
		Bundle b = i.getBundleExtra("http");
		String temp = b.getString("xxhttp");
		httpactivity.this.finish();
		wv.loadUrl(temp);
		
		
	}
	
	

}
