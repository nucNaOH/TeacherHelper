package com.student.manage;


import java.util.ArrayList;
import java.util.List;




import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class DBUtil {
	public static SQLiteDatabase db;
	
	public static final String DB_NAME = "mycourse";
	public static String TABLE_NAME_NOTE = "class";
	public static String CREATE_TABLE = "create table  if not exists "
			+ TABLE_NAME_NOTE + " (" + "id Integer Primary key,"
			+ "class TEXT)";
	public static long addclassNote(classNote note) {
		ContentValues cv = new ContentValues();
		cv.put("class", note.getClassname());
		long id = db.insert(TABLE_NAME_NOTE, null, cv);
		return id;
	}
	
		public static List<classNote> getAllclassNote() {
			List<classNote> list = new ArrayList<classNote>();
			Cursor cur = db.query(TABLE_NAME_NOTE, null, null, null, null, null,
					null);
			if (cur != null) {
				if (cur.moveToFirst()) {
					do {
						classNote note = new classNote();
						note.setId(cur.getInt(cur.getColumnIndex("id")));
						note.setClassname(cur.getString(cur.getColumnIndex("class")));
						list.add(note);
					} while (cur.moveToNext());
				}
				cur.close();
				
			}
			return list;
		}
		
		/**
		 * 创建学生信息表
		 * @param classname
		 */
		public static void classtable(String classname){
			
			String classtable = "create table  if not exists "
					+ classname + " (" + "id Integer Primary key,"
					+"snumber TEXT,"+"sname TEXT,"+"sex TEXT,"+"sphone TEXT,"+"phone TEXT,"+"address TEXT,"+ "beizhu TEXT)";
			db.execSQL(classtable);
		}
		public static long addsinfoNote(addinfoNote note,String classname) {
			ContentValues cv = new ContentValues();
			cv.put("snumber", note.getSnumber());
			cv.put("sname", note.getSname());
			cv.put("sex", note.getSex());
			cv.put("sphone", note.getSphone());
			cv.put("phone", note.getPhone());
			cv.put("address", note.getAddress());
			cv.put("beizhu", note.getBeizhu());
			long id = db.insert(classname, null, cv);
			return id;
		}
		
		public static List<addinfoNote> getAllstudentNote(String classname) {
			List<addinfoNote> list = new ArrayList<addinfoNote>();
			Cursor cur = db.query(classname, null, null, null, null, null,
					null);
			if (cur != null) {
				if (cur.moveToFirst()) {
					do {
						addinfoNote note = new addinfoNote();
						note.setId(cur.getInt(cur.getColumnIndex("id")));
						note.setSname(cur.getString(cur.getColumnIndex("snumber")));
						note.setSnumber(cur.getString(cur.getColumnIndex("sname")));
						note.setSex(cur.getString(cur
								.getColumnIndex("sex")));
						note.setSphone(cur.getString(cur.getColumnIndex("sphone")));
						note.setPhone(cur.getString(cur.getColumnIndex("phone")));
						note.setAddress(cur.getString(cur.getColumnIndex("address")));
						note.setBeizhu(cur.getString(cur.getColumnIndex("beizhu")));
						list.add(note);
					} while (cur.moveToNext());
				}
				cur.close();
				
			}
			return list;
		}
		
		public static addinfoNote getNoteById(String id,String classname) {
			addinfoNote note = new addinfoNote();
			Cursor cur = db.query(classname, null, "id=?",
					new String[] { id }, null, null, null, null);
			if (cur != null) {
				if (cur.moveToFirst()) {
					note.setId(cur.getInt(cur.getColumnIndex("id")));
					note.setSname(cur.getString(cur.getColumnIndex("sname")));
					note.setSnumber(cur.getString(cur.getColumnIndex("snumber")));
					note.setSex(cur.getString(cur.getColumnIndex("sex")));
					note.setSphone(cur.getString(cur.getColumnIndex("sphone")));
					note.setPhone(cur.getString(cur.getColumnIndex("phone")));
					note.setAddress(cur.getString(cur.getColumnIndex("address")));
					note.setBeizhu(cur.getString(cur.getColumnIndex("beizhu")));
				}
				cur.close(); // 关闭游标，释放资源
			}
			return note;
		}
		/**
		 * 删除班级序列号
		 */
		public static void deleteclassNote(String id) {

			String sql = "delete from " + TABLE_NAME_NOTE + " where id=" + id;
			db.execSQL(sql);
			
		}
		/**
		 * 删除学生信息表
		 */
       public static void deleteinfo(String classname){
    		String sql= " DROP TABLE IF EXISTS " + classname ;
			db.execSQL(sql);
       }
       /**
        * 修改学生信息
        */
      public static void updateinfo(String classname,String name,String number,String sex,
    	String sphone,String phone,String address,String beizhu,String id){

    	  ContentValues values = new ContentValues();
		  values.put("snumber", number);
		  values.put("sname", name);
		  values.put("sex", sex);
		  values.put("phone", phone);
		  values.put("address", address);
		  values.put("beizhu", beizhu);
			db.update(classname, values, "id=?", new String[]{id});
			
      }
       

       
       
       
       
       
       
       
       
}
