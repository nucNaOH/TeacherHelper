package com.teacher.dbutil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import com.student.leave.leaveNote;
import com.student.money.moneyNote;
import com.teacher.call.callNote;
import com.teacher.course.courseNote;
import com.teacher.diary.dailyNote;
import com.teacher.family.studentNote;
import com.teacher.helper.userNote;
import com.teacher.helper.zhuceactivity;
import com.teacher.note.Note;
import com.teacher.schoolweb.schoolwebNote;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBUtil {
	private static String mYear;
	private static String mMonth;
	private static String mDay;
	private static String mWay;
	public static SQLiteDatabase db;
	public static final String DB_NAME = "mycourse";
	final static Calendar c = Calendar.getInstance();
//	// 课程表
//	public static String TABLE_NAME_NOTE = "course";
//	// 课程表
//	public static String CREATE_TABLE = "create table  if not exists "
//			+ TABLE_NAME_NOTE + " (" + "id Integer Primary key,"
//			+ "xingqi TEXT," + "jieci NVARCHAR(100),"
//			+ "classroom NVARCHAR(100)," + "beizhu TEXT)";

	/**
	 * 保存课程
	 * 
	 * @param note
	 * @return
	 */
//	public static long addNote(Note note) {
//		ContentValues cv = new ContentValues();
//		cv.put("xingqi", note.getXingqi());
//		cv.put("jieci", note.getJieci());
//		cv.put("classroom", note.getClassroom());
//		cv.put("beizhu", note.getBeizhu());
//
//		long id = db.insert(TABLE_NAME_NOTE, null, cv);
//		return id;
//	}
//
//	// 课程表
//	public static List<Note> getAllNote() {
//		List<Note> list = new ArrayList<Note>();
//		Cursor cur = db.query(TABLE_NAME_NOTE, null, null, null, null, null,
//				null);
//		if (cur != null) {
//			if (cur.moveToFirst()) {
//				do {
//					Note note = new Note();
//					note.setId(cur.getInt(cur.getColumnIndex("id")));
//					note.setXingqi(cur.getString(cur.getColumnIndex("xingqi")));
//					note.setJieci(cur.getString(cur.getColumnIndex("jieci")));
//					note.setClassroom(cur.getString(cur
//							.getColumnIndex("classroom")));
//					note.setBeizhu(cur.getString(cur.getColumnIndex("beizhu")));
//					list.add(note);
//				} while (cur.moveToNext());
//			}
//			cur.close();
//			
//		}
//		return list;
//	}
//
//	// 删除课程
//	public static void deleteNote(String id) {
//
//		String sql1 = "delete from " + TABLE_NAME_NOTE + " where id=" + id;
//		db.execSQL(sql1);
//	}
	/**
	 * 温馨提示，查询课程
	 */
	
//    public static String courseremind(){
//    	Calendar c = Calendar.getInstance();
//    	c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
//    	mWay =String.valueOf(c.get(Calendar.DAY_OF_WEEK));
//    	if ("4".equals(mWay)) {
//			mWay = "三";
//    	}
//    	String remind=null;
//		Cursor cursor = db.rawQuery(
//				"select jieci,classroom from "+ TABLE_NAME_NOTE +" where xingqi="+"\"星期"+mWay+"\"",null);
//				
//		
//			if (cursor.moveToNext()) {
//				
//					remind= cursor.getString(0);
//					remind= cursor.getString(1);
//                    
//				
//			}
//			
//			cursor.close();
//		  
//	return remind;
//    }
//    public String findMode(String number){
//		String result = null;
//		SQLiteDatabase db = helper.getReadableDatabase();
//		Cursor cursor = db.rawQuery("select mode from blacknumber where number=?", new String[]{number});
//		if(cursor.moveToNext()){
//			result = cursor.getString(0);
//		}
//		cursor.close();
//		db.close();
//		return result;
//	}
//	
	
	
	// 待办事项
//	public static String TABLE_NAME_NOTE3 = "banshi";
//	// 待办事项
//	public static String CREATE_TABLE3 = "create table  if not exists "
//			+ TABLE_NAME_NOTE3 + " (" + "id Integer Primary key,"
//			+ "date TEXT," + "time TEXT," + "matter TEXT)";
//
//	/**
//	 * 保存待办事项
//	 * 
//	 * @param note
//	 * @return
//	 */
//	public static long addMatter(matterNote note) {
//		ContentValues cv = new ContentValues();
//		cv.put("date", note.getDate());
//		cv.put("time", note.getTime());
//		cv.put("matter", note.getMatter());
//		// cv.put("file", note.getFile());
//		long id = db.insert(TABLE_NAME_NOTE3, null, cv);
//		return id;
//	}
//
//	// 待办事项
//	public static List<matterNote> getAllmatterNote() {
//		List<matterNote> list = new ArrayList<matterNote>();
//		Cursor cur = db.query(TABLE_NAME_NOTE3, null, null, null, null, null,
//				null);
//		if (cur != null) {
//			if (cur.moveToFirst()) {
//				do {
//					matterNote note = new matterNote();
//					note.setId(cur.getInt(cur.getColumnIndex("id")));
//					note.setDate(cur.getString(cur.getColumnIndex("date")));
//					note.setTime(cur.getString(cur.getColumnIndex("time")));
//					note.setMatter(cur.getString(cur.getColumnIndex("matter")));
//					// note.setMatter(cur.getString(cur.getColumnIndex("file")));
//					list.add(note);
//				} while (cur.moveToNext());
//			}
//			cur.close();
//		}
//		return list;
//	}
//
//	public static matterNote getNotematterById(String id) {
//		matterNote note = new matterNote();
//		Cursor cur = db.query(TABLE_NAME_NOTE3, null, "id=?",
//				new String[] { id }, null, null, null, null);
//		if (cur != null) {
//			if (cur.moveToFirst()) {
//				note.setId(cur.getInt(cur.getColumnIndex("id")));
//				note.setDate(cur.getString(cur.getColumnIndex("date")));
//				note.setTime(cur.getString(cur.getColumnIndex("time")));
//				note.setMatter(cur.getString(cur.getColumnIndex("matter")));
//				// note.setMatter(cur.getString(cur.getColumnIndex("file")));
//			}
//			cur.close(); // 关闭游标，释放资源
//		}
//		return note;
//	}
//
//	/**
//	 * 得到日期
//	 * 
//	 * @param id
//	 */
//	public static String getDate() {
//		Cursor cursor = db.rawQuery("select date from banshi ", null);
//		cursor.moveToFirst();
//
//		String date = cursor.getString(0);
//		cursor.close();
//		return date;
//	}
//
//	/**
//	 * 得到时间
//	 * 
//	 * @param id
//	 */
//	public static String getTime() {
//		Cursor cursor = db.rawQuery("select time from banshi ", null);
//		cursor.moveToFirst();
//
//		String time = cursor.getString(0);
//		cursor.close();
//		return time;
//	}
//
//	// 删除事项
//	public static void deleteNote3(String id) {
//		String sql = "delete from " + TABLE_NAME_NOTE3 + " where id=" + id;
//		db.execSQL(sql);
//	}

	// 通讯录
	public static String TABLE_NAME_NOTE2 = "call";
	// 通讯录
	public static String CREATE_TABLE2 = "create table  if not exists "
			+ TABLE_NAME_NOTE2 + " (" + "id Integer Primary key,"
			+ "callname TEXT," + "callnumber TEXT)";

	/**
	 * 保存联系人
	 * 
	 * @param note
	 * @return
	 */
	public static long addNote(callNote note) {
		ContentValues cv = new ContentValues();
		cv.put("callname", note.getCallname());
		cv.put("callnumber", note.getCallnumber());
		long id = db.insert(TABLE_NAME_NOTE2, null, cv);
		return id;
	}

	// 通讯录
	public static List<callNote> getAllcallNote() {
		List<callNote> list = new ArrayList<callNote>();
		Cursor cur = db.query(TABLE_NAME_NOTE2, null, null, null, null, null,
				null);
		if (cur != null) {
			if (cur.moveToFirst()) {
				do {
					callNote note = new callNote();
					note.setId(cur.getInt(cur.getColumnIndex("id")));
					note.setCallname(cur.getString(cur
							.getColumnIndex("callname")));
					note.setCallnumber(cur.getString(cur
							.getColumnIndex("callnumber")));
					list.add(note);
				} while (cur.moveToNext());
			}
			cur.close();
		}
		return list;
	}

	/**
	 * 删除联系人
	 * 
	 * @param id
	 */
	public static void deleteNote2(String id) {
		String sql = "delete from " + TABLE_NAME_NOTE2 + " where id=" + id;
		db.execSQL(sql);
	}

	// 日记表
	public static String TABLE_NAME_NOTE1 = "daily";
	// 日记
	public static String CREATE_TABLE1 = "create table  if not exists "
			+ TABLE_NAME_NOTE1 + " (" + "id Integer Primary key,"
			+ "title TEXT," + "riqi TEXT," + "content TEXT)";

	/**
	 * 添加日记
	 * 
	 * @param note
	 * @return
	 */
	public static long addDaily(dailyNote note) {
		ContentValues cv = new ContentValues();
		cv.put("title", note.getTitle());
		cv.put("content", note.getContent());
		@SuppressWarnings("unused")
		Calendar calendar = Calendar.getInstance();
		String riqi = StringData();
		// String riqi = calendar.get(Calendar.YEAR) + "年"
		// + calendar.get(Calendar.MONTH) + "月"
		// + calendar.get(Calendar.DAY_OF_MONTH) + "日"
		// +"星期"+calendar.get(Calendar.DAY_OF_WEEK) +
		// + calendar.get(Calendar.HOUR_OF_DAY) + "时"
		// + calendar.get(Calendar.MINUTE) + "分";
		cv.put("riqi", riqi);

		long id = db.insert(TABLE_NAME_NOTE1, null, cv);
		return id;
	}

	// 日记
	public static List<dailyNote> getAlldailyNote() {
		List<dailyNote> list = new ArrayList<dailyNote>();
		Cursor cur = db.query(TABLE_NAME_NOTE1, null, null, null, null, null,
				null);
		if (cur != null) {
			if (cur.moveToFirst()) {
				do {
					dailyNote note = new dailyNote();
					note.setId(cur.getInt(cur.getColumnIndex("id")));
					note.setTitle(cur.getString(cur.getColumnIndex("title")));
					note.setContent(cur.getString(cur.getColumnIndex("content")));
					note.setRiqi(cur.getString(cur.getColumnIndex("riqi")));
					list.add(note);
				} while (cur.moveToNext());
			}
			cur.close();
		}
		return list;
	}

	public static dailyNote getNoteById(String id) {
		dailyNote note = new dailyNote();
		Cursor cur = db.query(TABLE_NAME_NOTE1, null, "id=?",
				new String[] { id }, null, null, null, null);
		if (cur != null) {
			if (cur.moveToFirst()) {
				note.setId(cur.getInt(cur.getColumnIndex("id")));
				note.setTitle(cur.getString(cur.getColumnIndex("title")));
				note.setContent(cur.getString(cur.getColumnIndex("content")));
				note.setRiqi(cur.getString(cur.getColumnIndex("riqi")));
			}
			cur.close(); // 关闭游标，释放资源
		}
		return note;
	}

	// 删除日记
	public static void deleteNote1(String id) {
		String sql = "delete from " + TABLE_NAME_NOTE1 + " where id=" + id;
		db.execSQL(sql);
	}

	// 学生家庭信息
	public static String TABLE_NAME_NOTE4 = "sInfo";
	/**
	 * 学生家庭信息
	 */
	public static String CREATE_TABLE4 = "create table  if not exists "
			+ TABLE_NAME_NOTE4 + " (" + "id Integer Primary key,"
			+ "name TEXT," + "pname TEXT," + "phonenumber NVARCHAR(100),"
			+ "address Text," + "beizhu TEXT)";

	/**
	 * 添加学生家庭信息
	 * 
	 * @return
	 */
	public static long addinfoNote(studentNote note) {
		ContentValues cv = new ContentValues();
		cv.put("name", note.getName());
		cv.put("pname", note.getPname());
		cv.put("phonenumber", note.getPhonenumber());
		cv.put("address", note.getAddress());
		cv.put("beizhu", note.getBeizhu());

		long id = db.insert(TABLE_NAME_NOTE4, null, cv);
		return id;
	}

	// 学生信息
	public static List<studentNote> getAllsinfoNote() {
		List<studentNote> list = new ArrayList<studentNote>();
		Cursor cur = db.query(TABLE_NAME_NOTE4, null, null, null, null, null,
				null);
		if (cur != null) {
			if (cur.moveToFirst()) {
				do {
					studentNote note = new studentNote();
					note.setId(cur.getInt(cur.getColumnIndex("id")));
					note.setName(cur.getString(cur.getColumnIndex("name")));
					note.setPname(cur.getString(cur.getColumnIndex("pname")));
					note.setPhonenumber(cur.getString(cur
							.getColumnIndex("phonenumber")));
					note.setAddress(cur.getString(cur.getColumnIndex("address")));
					note.setBeizhu(cur.getString(cur.getColumnIndex("beizhu")));
					list.add(note);
				} while (cur.moveToNext());
			}
			cur.close();
		}
		return list;
	}

	public static studentNote getNotesinfoById(String id) {
		studentNote note = new studentNote();
		Cursor cur = db.query(TABLE_NAME_NOTE4, null, "id=?",
				new String[] { id }, null, null, null, null);
		if (cur != null) {
			if (cur.moveToFirst()) {
				note.setId(cur.getInt(cur.getColumnIndex("id")));
				note.setName(cur.getString(cur.getColumnIndex("name")));
				note.setPname(cur.getString(cur.getColumnIndex("pname")));
				note.setPhonenumber(cur.getString(cur
						.getColumnIndex("phonenumber")));
				note.setAddress(cur.getString(cur.getColumnIndex("address")));
				note.setBeizhu(cur.getString(cur.getColumnIndex("beizhu")));
			}
			cur.close(); // 关闭游标，释放资源
		}
		return note;
	}

	// 查找学生信息
	public static String selectNote4(String name1) {
		String sql1 = " select name from" + TABLE_NAME_NOTE4 + "where name="
				+ name1;
		db.execSQL(sql1);
		return name1;
	}

	// 删除学生信息
	public static void deleteNote4(String id) {
		String sql = "delete from " + TABLE_NAME_NOTE4 + " where id=" + id;
		db.execSQL(sql);
	}

	// 宿舍信息
//	public static String TABLE_NAME_NOTE5 = "susheinfo";
//	/**
//	 * 学生宿舍信息
//	 */
//	public static String CREATE_TABLE5 = "create table  if not exists "
//			+ TABLE_NAME_NOTE5 + " (" + "id Integer Primary key,"
//			+ "ssnumber TEXT," + "sszhang TEXT," + "sscyuan TEXT)";
//
//	/**
//	 * 添加宿舍信息
//	 * 
//	 * @return
//	 */
//	public static long addsusheNote(susheNote note) {
//		ContentValues cv = new ContentValues();
//		cv.put("ssnumber", note.getSushenumber());
//		cv.put("sszhang", note.getSushezhang());
//		cv.put("sscyuan", note.getSscyuan());
//
//		long id = db.insert(TABLE_NAME_NOTE5, null, cv);
//		return id;
//	}
//
//	// 宿舍信息
//	public static List<susheNote> getAllsusheNote() {
//		List<susheNote> list = new ArrayList<susheNote>();
//		Cursor cur = db.query(TABLE_NAME_NOTE5, null, null, null, null, null,
//				null);
//		if (cur != null) {
//			if (cur.moveToFirst()) {
//				do {
//					susheNote note = new susheNote();
//					note.setId(cur.getInt(cur.getColumnIndex("id")));
//					note.setSushenumber(cur.getString(cur
//							.getColumnIndex("ssnumber")));
//					note.setSushezhang(cur.getString(cur
//							.getColumnIndex("sszhang")));
//					note.setSscyuan(cur.getString(cur.getColumnIndex("sscyuan")));
//
//					list.add(note);
//				} while (cur.moveToNext());
//			}
//			cur.close();
//		}
//		return list;
//	}
//
//	public static susheNote getNotesusheById(String id) {
//		susheNote note = new susheNote();
//		Cursor cur = db.query(TABLE_NAME_NOTE5, null, "id=?",
//				new String[] { id }, null, null, null, null);
//		if (cur != null) {
//			if (cur.moveToFirst()) {
//				note.setId(cur.getInt(cur.getColumnIndex("id")));
//				note.setSushenumber(cur.getString(cur
//						.getColumnIndex("ssnumber")));
//				note.setSushezhang(cur.getString(cur.getColumnIndex("sszhang")));
//				note.setSscyuan(cur.getString(cur.getColumnIndex("sscyuan")));
//
//			}
//			cur.close(); // 关闭游标，释放资源
//		}
//		return note;
//	}

	// 请假信息
	public static String TABLE_NAME_NOTE7 = "leave";
	/**
	 * 请假信息
	 */
	public static String CREATE_TABLE7 = "create table  if not exists "
			+ TABLE_NAME_NOTE7 + "(" + "id Integer Primary key," + "name TEXT,"
			+ "date TEXT," + "date2 Text," + "reason TEXT)";

	/**
	 * 保存请假记录
	 * 
	 * @return
	 */
	public static long addleaveNote(leaveNote note) {
		ContentValues cv = new ContentValues();
		cv.put("name", note.getName());
		cv.put("date", note.getDate());
		cv.put("date2", note.getDate2());
		cv.put("reason", note.getReason());
		long id = db.insert(TABLE_NAME_NOTE7, null, cv);
		return id;
	}

	// 请假记录
	public static List<leaveNote> getAllleaveNote() {
		List<leaveNote> list = new ArrayList<leaveNote>();
		Cursor cur = db.query(TABLE_NAME_NOTE7, null, null, null, null, null,
				null);
		if (cur != null) {
			if (cur.moveToFirst()) {
				do {
					leaveNote note = new leaveNote();
					note.setId(cur.getInt(cur.getColumnIndex("id")));
					note.setName(cur.getString(cur.getColumnIndex("name")));
					note.setDate(cur.getString(cur.getColumnIndex("date")));
					note.setDate2(cur.getString(cur.getColumnIndex("date2")));
					note.setReason(cur.getString(cur.getColumnIndex("reason")));
					list.add(note);
				} while (cur.moveToNext());
			}
			cur.close();
		}
		return list;
	}

	// 删除假条
	public static void deleteleaveNote(String id) {

		String sql = "delete from " + TABLE_NAME_NOTE7 + " where id=" + id;
		db.execSQL(sql);
	}

	// 删除假条
	public static void updateleaveNote(String id) {

		String sql = "update  from " + TABLE_NAME_NOTE7 + " where id=" + id;
		db.execSQL(sql);
	}

	// 账目信息
	public static String TABLE_NAME_NOTE8 = "money";
	/**
	 * 收入金额信息
	 */
	public static String CREATE_TABLE8 = "create table if not exists "
			+ TABLE_NAME_NOTE8 + " (" + "id Integer Primary key,"
			+ "comein TEXT," + "out TEXT," + "shuoming TEXT,"
			+ "jine NVARCHAR(100)," + "date TEXT," + "time TEXT)";

	/**
	 * 保存金额
	 * 
	 * @return
	 */
	public static long addmoneyNote(moneyNote note) {
		ContentValues cv = new ContentValues();
		cv.put("comein", note.getRb());
		cv.put("out", note.getOut());
		cv.put("shuoming", note.getShuoming());
		cv.put("jine", note.getJine());
		cv.put("date", note.getDate());
		cv.put("time", note.getTime());
		// cv.put("beizhu", note.getBeizhu());

		long id = db.insert(TABLE_NAME_NOTE8, null, cv);
		return id;
	}
	/**
	 * 给支出和收入初始为0
	 * @param id
	 */
	
	public static void initialmoneyNote() {

		String sql = "insert into money(comein,out) values (0,0)";
		db.execSQL(sql);
	}
	// 金额
	public static List<moneyNote> getAllmoneyNote() {
		List<moneyNote> list = new ArrayList<moneyNote>();
		// Cursor cur = db.rawQuery("select * from money order by _id desc",
		// null);
		Cursor cur = db.query(TABLE_NAME_NOTE8, null, null, null, null, null,
				null);
		if (cur != null) {
			if (cur.moveToFirst()) {
				do {
					moneyNote note = new moneyNote();
					note.setId(cur.getInt(cur.getColumnIndex("id")));
					note.setRb(cur.getString(cur.getColumnIndex("comein")));
					note.setOut(cur.getString(cur.getColumnIndex("out")));
					note.setShuoming(cur.getString(cur
							.getColumnIndex("shuoming")));
					note.setJine(cur.getString(cur.getColumnIndex("jine")));
					note.setDate(cur.getString(cur.getColumnIndex("date")));
					note.setTime(cur.getString(cur.getColumnIndex("time")));
					// note.setBeizhu(cur.getString(cur.getColumnIndex("beizhu")));
					list.add(note);
				} while (cur.moveToNext());
			}
			cur.close();
		}
		return list;
	}

	/**
	 * 收入总额
	 */
	public static float getinCount() {
		// Cursor cursor = db.rawQuery("select sum(jine) from "+TABLE_NAME_NOTE8
		// ,null);
		Cursor cursor = db.rawQuery(
				"select sum(jine) from money group by comein", null);
		cursor.moveToFirst();

		float count = cursor.getFloat(0);
		System.out.print(count);
		cursor.close();
		return count;
	}

	/**
	 * 支出总额
	 */
	public static float getoutCount() {
		// Cursor cursor = db.rawQuery("select sum(jine) from "+TABLE_NAME_NOTE8
		// ,null);
		Cursor cursor = db.rawQuery("select sum(jine) from money group by out",
				null);
		cursor.moveToFirst();

		float count = cursor.getFloat(0);

		cursor.close();
		return count;
	}

	/**
	 * 删除班费
	 * 
	 * @param id
	 */
	public static void deletemoneyNote(String id) {

		String sql = "delete from " + TABLE_NAME_NOTE8 + " where id=" + id;
		db.execSQL(sql);
	}

	// 学校网站
	public static String TABLE_NAME_NOTE9 = "schoolweb";
	
	public static String CREATE_TABLE9 = "create table if not exists "
			+ TABLE_NAME_NOTE9 + " (" + "id Integer Primary key,"+ "schoolweb TEXT)";
	public static long addschoolwebNote(schoolwebNote note) {
		ContentValues cv = new ContentValues();
		cv.put("schoolweb", note.getSchoolWeb());
		
	

		long id = db.insert(TABLE_NAME_NOTE9, null, cv);
		return id;
	}
	public static List<schoolwebNote> getAllschoolwebNote() {
		List<schoolwebNote> list = new ArrayList<schoolwebNote>();
		// Cursor cur = db.rawQuery("select * from money order by _id desc",
		// null);
		Cursor cur = db.query(TABLE_NAME_NOTE9, null, null, null, null, null,
				null);
		if (cur != null) {
			if (cur.moveToFirst()) {
				do {
					schoolwebNote note = new schoolwebNote();
					note.setId(cur.getInt(cur.getColumnIndex("id")));
					note.setSchoolWeb(cur.getString(cur.getColumnIndex("schoolweb")));
					
					list.add(note);
				} while (cur.moveToNext());
			}
			cur.close();
		}
		return list;
	}
	public static void deleteschoolwebNote(String id) {

		String sql = "delete from " + TABLE_NAME_NOTE9 + " where id=" + id;
		db.execSQL(sql);
	}
	
    public static String TABLE_NAME_NOTE10 = "course";
	
 	public static String CREATE_TABLE10 = "create table  if not exists "
 			+ TABLE_NAME_NOTE10 + " (" + "id Integer Primary key,"
 			+ "xingqi TEXT," + "jieci NVARCHAR(100),"
 			+ "classroom TEXT," + "beizhu TEXT)";
 	/**
	 * 
	 * 
	 * @param note
	 * @return
	 */
	public static long addcourseNote(courseNote note) {
		ContentValues cv = new ContentValues();
		cv.put("xingqi", note.getXingqi());
		cv.put("jieci", note.getJieci());
		cv.put("classroom", note.getClassroom());
		cv.put("beizhu", note.getBeizhu());

		long id = db.insert(TABLE_NAME_NOTE10, null, cv);
		return id;
	}


	public static String getCourse() {
		String course = null;
		Cursor cursor = db.rawQuery("select xingqi from course where xingqi="+"\'星期一\'",null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				// course = cur.getString(0);
					 course =  cursor.getString(0);
				
			}
			cursor.close();
			
		}
	
		return course;
	}
	public static String getCourse2() {
		String course = null;
		
		Cursor cursor = db.rawQuery("select xingqi from course where xingqi="+"\'星期二\'", null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
			
					 course =  cursor.getString(0);
				
			}
			cursor.close();
			
		}
	
		return course;
	}
	public static String getCourse3() {
		String course = null;
		Cursor cursor = db.rawQuery("select xingqi from course where xingqi="+"\'星期三\'",null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
					 course =  cursor.getString(0);
				
			}
			cursor.close();
			
		}
	
		return course;
	}
	public static String getCourse4() {
		String course = null;
		Cursor cursor = db.rawQuery("select xingqi from course where xingqi="+"\'星期四\'",null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
					 course =  cursor.getString(0);
				
			}
			cursor.close();
			
		}
	
		return course;
	}
	public static String getCourse5() {
		String course = null;
		Cursor cursor = db.rawQuery("select xingqi from course where xingqi="+"\'星期五\'",null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
			
					 course =  cursor.getString(0);
				
			}
			cursor.close();
			
		}
	
		return course;
	}
	public static String getCourse6() {
		String course = null;
		Cursor cursor = db.rawQuery("select xingqi from course where xingqi="+"\'星期六\'",null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
			
					 course =  cursor.getString(0);
				
			}
			cursor.close();
			
		}
	
		return course;
	}
	
	public static String getCourse7() {
		String course = null;
		Cursor cursor = db.rawQuery("select xingqi from course where xingqi="+"\'星期日\'",null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
			  course =  cursor.getString(0);
			}
			cursor.close();
		}
		return course;
	}
	public static String getClass(String xingqi,String jieci) {
		String course = null;
		String beizhu = null;
		Cursor cur = db.rawQuery("select classroom,beizhu from course where xingqi=? and jieci=?",new String[]{xingqi,jieci});
		if (cur != null) {
			if (cur.moveToFirst()) {
				do {
				 course = cur.getString(0);
				 beizhu = cur.getString(1);
				} while (cur.moveToNext());
			}
			cur.close();
		}
		return course+"  "+beizhu;
	}
	
	public static void deletecourseNote(String id) {

		String sql1 = "delete from " + TABLE_NAME_NOTE10 + " where id=" + id;
		db.execSQL(sql1);
	}
	 public static void addinfo(){

		    	  ContentValues values = new ContentValues();
				  values.put("xingqi", "星期一");
			     db.insert(TABLE_NAME_NOTE10, null,values);
		      }
	
	 public static void addinfo2(){

   	  ContentValues values = new ContentValues();
		  values.put("xingqi", "星期二");
	     db.insert(TABLE_NAME_NOTE10, null,values);
     }
	 public static void addinfo3(){

	   	  ContentValues values = new ContentValues();
			  values.put("xingqi","星期三");
		     db.insert(TABLE_NAME_NOTE10, null,values);
	     }
	 public static void addinfo4(){

	   	  ContentValues values = new ContentValues();
		  values.put("xingqi", "星期四");
		     db.insert(TABLE_NAME_NOTE10, null,values);
	     }
	 public static void addinfo5(){

	   	  ContentValues values = new ContentValues();
			  values.put("xingqi", "星期五");
		     db.insert(TABLE_NAME_NOTE10, null,values);
	     }
	 public static void addinfo6(){

	   	  ContentValues values = new ContentValues();
			  values.put("xingqi", "星期六");
		     db.insert(TABLE_NAME_NOTE10, null,values);
	     }
	 public static void addinfo7(){

	   	  ContentValues values = new ContentValues();
		  values.put("xingqi", "星期日");
		     db.insert(TABLE_NAME_NOTE10, null,values);
	     }

	
	
	
	
	
	public static String StringData() {
		
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
		mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
		mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
		mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
		if ("1".equals(mWay)) {
			mWay = "天";

		} else if ("2".equals(mWay)) {
			mWay = "一";

		} else if ("3".equals(mWay)) {
			mWay = "二";
		} else if ("4".equals(mWay)) {
			mWay = "三";
		} else if ("5".equals(mWay)) {
			mWay = "四";
		} else if ("6".equals(mWay)) {
			mWay = "五";
		} else if ("7".equals(mWay)) {
			mWay = "六";
		}
		return mYear + "年" + mMonth + "月" + mDay + "日" + "/星期" + mWay;
	}

}
