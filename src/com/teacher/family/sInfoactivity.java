package com.teacher.family;

import java.util.List;

import com.teacher.dbutil.DBUtil;
import com.teacher.helper.R;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class sInfoactivity extends Activity {
   private ListView slist;
   public SharedPreferences spCount;
// 获取到所有学生信息
	List<studentNote> list = DBUtil.getAllsinfoNote();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sinfo);
		slist = (ListView) findViewById(R.id.sinforlist);
		slist.setAdapter(new sinfoAdapter(sInfoactivity.this, list));
		spCount = getSharedPreferences("counter" , Context.MODE_WORLD_READABLE);
        // 取出保存的NAME
		String deleteitem = spCount.getString("deleteitem", "");
		if(deleteitem.equals("1")){
			 list = DBUtil.getAllsinfoNote();
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1000, 100, 1, "添加");
		menu.add(1000, 101, 1, "查找");
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 100:
			Intent intent = new Intent(sInfoactivity.this,
					addsInfoactivity.class);
			startActivity(intent);
			sInfoactivity.this.finish();	
			break;
		
			

		}
		return true;
	}

}
