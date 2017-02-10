package cn.edu.nuc.mycallname.mainview;


import android.net.Uri;
import android.provider.BaseColumns;

public class NoteData implements BaseColumns {

		//定义数据库名
		public static final String DATABASE_NAME="notepad_manager.db";
		//定义数据库版本号
		public static final int VERSION=1;
		//定义表名，列名
		public static final String TABLE_NAME="notepad";
		public static final String TITLE="title";
		public static final String CONTENT="content";
		public static final String TIME="time";
		//内容提供者授权的路径
		public static final String AUTHORIES="com.nuc.lewen.welcome.provider.note_pad_content_provider";
		public static final Uri uri=Uri.parse("content://"+AUTHORIES+"/notepad");

}
