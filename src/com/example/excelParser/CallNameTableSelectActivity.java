package com.example.excelParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.teacher.helper.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CallNameTableSelectActivity extends Activity {

	private MyDatabaseHelper myDatabaseHelper;
	private String[] tablenames;
	private String[] stuNames;
	private TextView table_name;
	private ListView myTableList;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_table_selection);

		myDatabaseHelper = new MyDatabaseHelper(this, "myCourse.db3", 1);
		myTableList = (ListView) findViewById(R.id.lv_table_selection);
		table_name = (TextView) findViewById(R.id.table_name);

		// 得到数据库里所有的表名
		String sql = "select name from sqlite_master where type='table' order by name";
		Cursor tableNameCursor = myDatabaseHelper.getReadableDatabase()
				.rawQuery(sql, null);
		tablenames = new String[tableNameCursor.getCount()];
		int tableNum = 0;
		// 把"android_metadata"跟"sqlite_sequence"去掉。
		while (tableNameCursor.moveToNext()) {
			// 遍历出表名
			if ("android_metadata".equals(tableNameCursor.getString(0))) {
				;
			} else if ("sqlite_sequence".equals(tableNameCursor.getString(0))) {
				;
			} else if (tableNameCursor.getString(0).endsWith("的点名结果")) {
				// tablenames[tableNum++] = tableNameCursor.getString(0);
			} else {
				tablenames[tableNum++] = tableNameCursor.getString(0);
			}
		}
		tableNameCursor.close();

		// 创建一个List集合，List集合的元素是Map
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < tableNum; i++) {

			Map<String, Object> listItem = new HashMap<String, Object>();
			// 如果当前File是文件夹，使用folder图标；否则使用file图标
			listItem.put("tableName", tablenames[i]);
			// 添加List项
			listItems.add(listItem);

		}
		// 创建adapter
		ListAdapter adapter = new SimpleAdapter(this, listItems,
				R.layout.my_table_line, new String[] { "tableName" },
				new int[] { R.id.tv_my_table_name });
		myTableList.setAdapter(adapter);
		myTableList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				table_name.setText("您选择了：" + tablenames[position]);
			}
		});
	}

	public void startCall(View v) {
		// 根据已选择的表名，查到该表里所有的姓名，赋给stuName[]

		if (table_name.getText().toString().length() < 4) {
			Toast.makeText(this, "你还没有选择要点名的表", Toast.LENGTH_SHORT).show();
		} else {
			if (selectedTableIncludeName(table_name.getText().toString()
					.substring(5))) {

				String sql = "select 姓名 from "
						+ table_name.getText().toString().substring(5) + ";";
				System.out.println(sql);
				Cursor stuNameListCursor = myDatabaseHelper
						.getReadableDatabase().rawQuery(sql, null);
				stuNames = new String[stuNameListCursor.getCount()];

				int stuNum = 0;
				while (stuNameListCursor.moveToNext()) {
					// 遍历出表名
					stuNames[stuNum++] = stuNameListCursor.getString(0);
					System.out.println(stuNames[stuNum - 1]);
				}
				stuNameListCursor.close();
				// 提前创建存储学生签到信息的table
				if (!selectedTableAlreadyHaveCallNameTable(table_name.getText()
						.toString().substring(5))) {
					createCallNameResultTable(table_name.getText().toString()
							.substring(5), stuNames);
				}

				Intent intent = new Intent(CallNameTableSelectActivity.this,
						CallNameViewFlipperActivity.class);
				intent.putExtra("selectTableStuNames", stuNames);
				intent.putExtra("selectedTableName", table_name.getText()
						.toString().substring(5));
				startActivity(intent);
			} else {
				Toast.makeText(this, "您选择的表里没有“姓名”列", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	private boolean selectedTableAlreadyHaveCallNameTable(String tableName) {

		String sql = "select name from sqlite_master where type='table' order by name";
		Cursor tableNameCursor = myDatabaseHelper.getReadableDatabase()
				.rawQuery(sql, null);
		tablenames = new String[tableNameCursor.getCount()];
		while (tableNameCursor.moveToNext()) {
			// 遍历出表名
			if ((tableName + "的点名结果").equals(tableNameCursor.getString(0))) {
				tableNameCursor.close();
				return true;
			}
		}
		tableNameCursor.close();

		return false;
	}

	private boolean selectedTableIncludeName(String tableName) {
		String sql = "PRAGMA table_info(" + tableName + ")" + ";";
		System.out.println(sql);
		Cursor cursor = myDatabaseHelper.getReadableDatabase().rawQuery(sql,
				null);

		while (cursor.moveToNext()) {
			System.out.println(cursor.getString(1));
			if ("姓名".equals(cursor.getString(1))) {
				cursor.close();
				return true;
			}
		}
		cursor.close();
		return false;
	}

	private void createCallNameResultTable(String tableName, String[] stuNames) {
		// 构建建表语句
		StringBuilder sql = new StringBuilder("create table ");
		sql.append(table_name.getText().toString().substring(5) + "的点名结果");
		sql.append(" ( studentId integer primary key autoincrement ,");
		sql.append(" '姓名' TEXT");
		sql.append(", '缺席次数' integer");
		sql.append(", '请假次数' integer );");
		System.out.println(sql.toString());
		// 构建表
		myDatabaseHelper.getWritableDatabase().execSQL(sql.toString());
		// 插入学生信息
		for (int j = 0; j < stuNames.length; j++) {

			ContentValues values = new ContentValues();
			values.put("姓名", stuNames[j]);
			values.put("缺席次数", 0);
			values.put("请假次数", 0);
			myDatabaseHelper.getWritableDatabase().insert(
					table_name.getText().toString().substring(5) + "的点名结果",
					null, values);
		}

	}

}
