package cn.edu.nuc.mycallname.mainview;

import cn.edu.nuc.mycallname.mainview.NoteData;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class NotePadContentProvider extends ContentProvider {

	SQLiteDatabase mDatabase;

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {

		return mDatabase.delete(NoteData.TABLE_NAME, selection, selectionArgs);
	}

	@Override
	public String getType(Uri uri) {

		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long rowId = this.mDatabase.insert(NoteData.TABLE_NAME, null, values);

		Uri newUri = null;
		if (rowId > 0) {
			newUri = ContentUris.withAppendedId(NoteData.uri, rowId);
		} else {
			newUri = uri;
		}

		return newUri;
	}

	@Override
	public boolean onCreate() {
		// 实例化NoteHelper数据库辅助对象
		NoteHelper noteHelper = new NoteHelper(getContext());
		try {
			mDatabase = noteHelper.getWritableDatabase();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		return mDatabase.query(NoteData.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		return mDatabase.update(NoteData.TABLE_NAME, values, selection, selectionArgs);
	}

	// 创建一个数据库
	class NoteHelper extends SQLiteOpenHelper {

		public NoteHelper(Context context) {
			super(context, NoteData.DATABASE_NAME, null, NoteData.VERSION);

		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String sql = "create table " + NoteData.TABLE_NAME + "("
					+ NoteData._ID + " integer primary key," + NoteData.TITLE
					+ " text not null," + NoteData.CONTENT + " text not null,"
					+ NoteData.TIME + " text not null)";

			db.execSQL(sql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			onCreate(db);
		}

	}

}
