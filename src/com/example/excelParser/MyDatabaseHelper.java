package com.example.excelParser;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class MyDatabaseHelper extends SQLiteOpenHelper
{
	public MyDatabaseHelper(Context context, String name, int version)
	{
		super(context, name, null, version);
	}
	//���ݵ�һ�з��ؽ����������
	public void createMyCourseTable(SQLiteDatabase db,String tableName, String[] course){
		StringBuilder CREATE_TABLE_SQL = new StringBuilder(
				"create table ");
		CREATE_TABLE_SQL.append(tableName);
		CREATE_TABLE_SQL.append(" ( studentId integer primary key autoincrement ,");
		int i = 0;
		while(course[i++] != null){
			CREATE_TABLE_SQL.append("'"+course[i-1]+"' TEXT");
			CREATE_TABLE_SQL.append(",");
		}
		CREATE_TABLE_SQL.deleteCharAt(CREATE_TABLE_SQL.lastIndexOf(","));
		CREATE_TABLE_SQL.append(");");
		
		System.out.println(CREATE_TABLE_SQL.toString());
		
		db.execSQL(CREATE_TABLE_SQL.toString());

	}
	
	public void onCreate(SQLiteDatabase db)
	{
		// ��һ��ʹ�����ݿ�ʱ�Զ�����
//		db.execSQL(CREATE_TABLE_SQL);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db
		, int oldVersion, int newVersion)
	{
	}
}
