package cn.edu.nuc.mycallname.mainview;


import android.net.Uri;
import android.provider.BaseColumns;

public class NoteData implements BaseColumns {

		//�������ݿ���
		public static final String DATABASE_NAME="notepad_manager.db";
		//�������ݿ�汾��
		public static final int VERSION=1;
		//�������������
		public static final String TABLE_NAME="notepad";
		public static final String TITLE="title";
		public static final String CONTENT="content";
		public static final String TIME="time";
		//�����ṩ����Ȩ��·��
		public static final String AUTHORIES="com.nuc.lewen.welcome.provider.note_pad_content_provider";
		public static final Uri uri=Uri.parse("content://"+AUTHORIES+"/notepad");

}
