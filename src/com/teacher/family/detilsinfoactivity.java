package com.teacher.family;

import com.teacher.dbutil.DBUtil;
import com.teacher.helper.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class detilsinfoactivity extends Activity {
	private TextView name = null;
	private TextView pname = null;
	private TextView phonenumber = null;
	private TextView address = null;
	private TextView beizhu = null;
	

	private String mark = "1";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detilsinfo);
		Intent intent = getIntent();
		String id = intent.getExtras().getString("id");
		mark = intent.getExtras().getString("mark");
		//得到学生信息列表
		studentNote note = DBUtil.getNotesinfoById(id);
        //学生姓名
		name = (TextView) findViewById(R.id.tvname);
		name.setText(note.getName());
		//家长姓名
		pname = (TextView) findViewById(R.id.tvpname);
		pname.setText(note.getPname());
		//联系电话
		phonenumber = (TextView) findViewById(R.id.tvphonenumber);
		phonenumber.setText(note.getPhonenumber());
		//可以点击
		phonenumber.setClickable(true);
	    phonenumber.setFocusable(true);
	    // 增加TextView的点击事件
        // 单击事件 拨打电话
	    // 单击事件
//	 phonenumber.setOnClickListener(new OnClickListener() {
//     public void onClick(View v) {
//    	 String callnumber=phonenumber.getText().toString().trim();
//    	 Intent intent = new Intent();
//		 intent.setAction(Intent.ACTION_CALL);
//		 intent.setData(Uri.parse("tel:"+callnumber));
//		 startActivity(intent);
//    	
//    	
// }
//	  });
	    //家庭住址
		address = (TextView) findViewById(R.id.tvaddress);
		address.setText(note.getAddress());
		//备注
		beizhu = (TextView) findViewById(R.id.tvbeizhu);
		beizhu.setText(note.getBeizhu());
	}
	

}
