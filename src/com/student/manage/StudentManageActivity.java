package com.student.manage;

import java.util.List;

import com.teacher.helper.R;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class StudentManageActivity extends Activity {
	private TextView look_sinfo;
	private TextView add_sinfo;
	private TextView delete_sinfo;
	private EditText add_class;
	private Button ok;
	private Button cancel;
	private AlertDialog dialog;
	private ListView classlist;
	private AlertDialog.Builder builder;
	private Intent intent;
	private int index = 0;
	List<classNote> list;
	private String classname;
	private SharedPreferences sp;
	private classlistAdapter classlistadapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_manage);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		createbiao();
		classlist = (ListView) findViewById(R.id.classlist);
		list = DBUtil.getAllclassNote();

		classlist.setAdapter(new classlistAdapter(StudentManageActivity.this, list));
		classlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				index = arg2;
				selectdialog();
				classlistadapter = new classlistAdapter(StudentManageActivity.this, list);
				classlistadapter.notifyDataSetChanged();

			}

		});
	}

	

	public void addclass(View view) {
		showDialog();
	}

	private void showDialog() {

		builder = new Builder(StudentManageActivity.this);
		// 自定义一个布局文件
		View view = View.inflate(StudentManageActivity.this, R.layout.add_class_student, null);
		add_class = (EditText) view.findViewById(R.id.ed_add_class);
		ok = (Button) view.findViewById(R.id.ok);
		cancel = (Button) view.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 把这个对话框取消掉
				dialog.dismiss();
			}
		});
		ok.setOnClickListener(new OnClickListener() {

			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {

				String addclass = add_class.getText().toString().trim();
				list = DBUtil.getAllclassNote();
				if (list.size() >= 1) {

					for (int i = 0; i < list.size(); i++) {
						classNote note = list.get(i);
						classname = note.getClassname();
						if (classname.equals(addclass)) {
							Toast.makeText(StudentManageActivity.this, "已存在该班级，请重新添加", 1)
									.show();
							add_class.setText("");
							dialog.dismiss();
							return;
						}
					}
				}
				if (TextUtils.isEmpty(addclass)) {
					Toast.makeText(StudentManageActivity.this, "班级为空", 1).show();
					return;
				} else if (list.size() == 0 || !classname.equals(addclass)) {
					classNote note = new classNote();
					note.setClassname(addclass);

					// 将note添加到数据库中
					long id = DBUtil.addclassNote(note);
					if (id > 0) {
						final List<classNote> list = DBUtil.getAllclassNote();
						classlist.setAdapter(new classlistAdapter(
								StudentManageActivity.this, list));

						Toast.makeText(StudentManageActivity.this, "添加成功",
								Toast.LENGTH_SHORT).show();

						dialog.dismiss();
					} else {
						Toast.makeText(StudentManageActivity.this, "添加失败",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		dialog = builder.create();
		dialog.setTitle("添加班级");
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
	}

	private void selectdialog() {

		builder = new Builder(StudentManageActivity.this);
		// 自定义一个布局文件
		View view = View
				.inflate(StudentManageActivity.this, R.layout.dialog_sinfo_student, null);
		// 查看学生信息
		look_sinfo = (TextView) view.findViewById(R.id.look_sinfo);
		// 添加学生信息
		add_sinfo = (TextView) view.findViewById(R.id.add_sinfo);
		// 删除学生信息
		delete_sinfo = (TextView) view.findViewById(R.id.delete_sinfo);

		look_sinfo.setOnClickListener(new OnClickListener() {

			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
                //创建学生信息表
				final List<classNote> list = DBUtil.getAllclassNote();
				for (int i = 0; i <= list.size(); i++) {
					classNote note1 = list.get(index);
					classname = note1.getClassname();
				}
				DBUtil.classtable("班" + classname);
				Editor editor = sp.edit();
				editor.putString("classname", "班" + classname);
				editor.commit();

				intent = new Intent();
				intent.setClass(StudentManageActivity.this, StudentlistActivity.class);
				startActivity(intent);
				dialog.dismiss();

			}
		});
		add_sinfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			 final List<classNote> list = DBUtil.getAllclassNote();
				
				classNote note = list.get(index);
			
			 classname=note.getClassname();
			
				Bundle bd = new Bundle();
				bd.putString("name", classname);
				intent = new Intent();
				intent.putExtra("newname", bd);
				intent.setClass(StudentManageActivity.this, add_sinfoactivity.class);

				startActivity(intent);
				dialog.dismiss();

			}
			// 把这个对话框取消掉

		});
		delete_sinfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				builder = new Builder(StudentManageActivity.this);
				builder.setMessage("确定要删除该班学生信息吗？")
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
									}
								})
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										 list = DBUtil.getAllclassNote();
										for (int i = 0; i < list.size(); i++) {

											classNote note = list.get(index);
											classname = note.getClassname();
											DBUtil.deleteclassNote(String
													.valueOf(note.getId()));
											list.clear();
											DBUtil.deleteinfo("班" + classname);

											Toast.makeText(StudentManageActivity.this,
													"删除该班级学生信息!",
													Toast.LENGTH_SHORT).show();
										}

										list = DBUtil.getAllclassNote();
										classlist
												.setAdapter(new classlistAdapter(
														StudentManageActivity.this, list));
									}
								}).show();
				dialog.dismiss();
			}
		});

		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
	}

	// 创建数据库
	private void createbiao() {

		if (DBUtil.db == null) {
			DBUtil.db = StudentManageActivity.this.openOrCreateDatabase(DBUtil.DB_NAME,
					MODE_PRIVATE, null); // 数据库私有
			DBUtil.db.execSQL(DBUtil.CREATE_TABLE);

		}
	}
}
