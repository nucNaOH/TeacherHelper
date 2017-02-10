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

		// �õ����ݿ������еı���
		String sql = "select name from sqlite_master where type='table' order by name";
		Cursor tableNameCursor = myDatabaseHelper.getReadableDatabase()
				.rawQuery(sql, null);
		tablenames = new String[tableNameCursor.getCount()];
		int tableNum = 0;
		// ��"android_metadata"��"sqlite_sequence"ȥ����
		while (tableNameCursor.moveToNext()) {
			// ����������
			if ("android_metadata".equals(tableNameCursor.getString(0))) {
				;
			} else if ("sqlite_sequence".equals(tableNameCursor.getString(0))) {
				;
			} else if (tableNameCursor.getString(0).endsWith("�ĵ������")) {
				// tablenames[tableNum++] = tableNameCursor.getString(0);
			} else {
				tablenames[tableNum++] = tableNameCursor.getString(0);
			}
		}
		tableNameCursor.close();

		// ����һ��List���ϣ�List���ϵ�Ԫ����Map
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < tableNum; i++) {

			Map<String, Object> listItem = new HashMap<String, Object>();
			// �����ǰFile���ļ��У�ʹ��folderͼ�ꣻ����ʹ��fileͼ��
			listItem.put("tableName", tablenames[i]);
			// ���List��
			listItems.add(listItem);

		}
		// ����adapter
		ListAdapter adapter = new SimpleAdapter(this, listItems,
				R.layout.my_table_line, new String[] { "tableName" },
				new int[] { R.id.tv_my_table_name });
		myTableList.setAdapter(adapter);
		myTableList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				table_name.setText("��ѡ���ˣ�" + tablenames[position]);
			}
		});
	}

	public void startCall(View v) {
		// ������ѡ��ı������鵽�ñ������е�����������stuName[]

		if (table_name.getText().toString().length() < 4) {
			Toast.makeText(this, "�㻹û��ѡ��Ҫ�����ı�", Toast.LENGTH_SHORT).show();
		} else {
			if (selectedTableIncludeName(table_name.getText().toString()
					.substring(5))) {

				String sql = "select ���� from "
						+ table_name.getText().toString().substring(5) + ";";
				System.out.println(sql);
				Cursor stuNameListCursor = myDatabaseHelper
						.getReadableDatabase().rawQuery(sql, null);
				stuNames = new String[stuNameListCursor.getCount()];

				int stuNum = 0;
				while (stuNameListCursor.moveToNext()) {
					// ����������
					stuNames[stuNum++] = stuNameListCursor.getString(0);
					System.out.println(stuNames[stuNum - 1]);
				}
				stuNameListCursor.close();
				// ��ǰ�����洢ѧ��ǩ����Ϣ��table
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
				Toast.makeText(this, "��ѡ��ı���û�С���������", Toast.LENGTH_SHORT)
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
			// ����������
			if ((tableName + "�ĵ������").equals(tableNameCursor.getString(0))) {
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
			if ("����".equals(cursor.getString(1))) {
				cursor.close();
				return true;
			}
		}
		cursor.close();
		return false;
	}

	private void createCallNameResultTable(String tableName, String[] stuNames) {
		// �����������
		StringBuilder sql = new StringBuilder("create table ");
		sql.append(table_name.getText().toString().substring(5) + "�ĵ������");
		sql.append(" ( studentId integer primary key autoincrement ,");
		sql.append(" '����' TEXT");
		sql.append(", 'ȱϯ����' integer");
		sql.append(", '��ٴ���' integer );");
		System.out.println(sql.toString());
		// ������
		myDatabaseHelper.getWritableDatabase().execSQL(sql.toString());
		// ����ѧ����Ϣ
		for (int j = 0; j < stuNames.length; j++) {

			ContentValues values = new ContentValues();
			values.put("����", stuNames[j]);
			values.put("ȱϯ����", 0);
			values.put("��ٴ���", 0);
			myDatabaseHelper.getWritableDatabase().insert(
					table_name.getText().toString().substring(5) + "�ĵ������",
					null, values);
		}

	}

}
