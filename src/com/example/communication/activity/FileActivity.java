package com.example.communication.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.teacher.helper.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ѡ���ļ���ͼ
 * 
 * @author Administrator
 * 
 */
public class FileActivity extends BaseActivity implements OnItemClickListener,
		OnItemLongClickListener, OnClickListener {

	// ��ǰ·��
	private String path = Environment.getExternalStorageState().toString();

	private ListView itemList;
	private TextView filePath;
	private Button sendButton;

	// ����
	private Vibrator vibrator;

	private List<Map<String, Object>> adapterList;

	private StringBuffer filePaths = new StringBuffer("");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.files);

		itemList = (ListView) findViewById(R.id.file_detail);
		filePath = (TextView) findViewById(R.id.file_path);
		sendButton = (Button) findViewById(R.id.file_send);
		sendButton.setOnClickListener(this);
		// ��ʼʱ���ɵ����ֻ��ѡ�е�·�����ļ�ʱ�ſ��Ե��
		sendButton.setEnabled(false);

		reConstructList(path);
	}

	/** ���¹���List */
	private void reConstructList(String path) {
		filePath.setText("");
		adapterList = constructList(path);
		SimpleAdapter listAdapter = new SimpleAdapter(this, adapterList,
				R.layout.file_item, new String[] { "name", "path", "img" },
				new int[] { R.id.file_name, R.id.file_path, R.id.file_img });

		itemList.setAdapter(listAdapter);
		itemList.setOnItemClickListener(this);
		itemList.setOnItemLongClickListener(this);
		itemList.setSelection(0);

	}

	/** ����List */
	private List<Map<String, Object>> constructList(String path) {
		File nowFile = new File(path);
		adapterList = new ArrayList<Map<String, Object>>();
		// ��Ŀ¼
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("name", "��Ŀ¼");
		root.put("img", R.drawable.file_root);
		root.put("path", "�ظ�Ŀ¼");
		adapterList.add(root);

		// �ϼ�Ŀ¼
		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("name", "��һ��");
		pMap.put("img", R.drawable.file_parent);
		pMap.put("path", "��һ��");
		adapterList.add(pMap);

		// ���ǵ�ǰ·����Ӧ�����ļ����򷵻�
		if (!nowFile.isDirectory()) {
			makeTextLong("��ѡ��" + path + ",�������Ͱ�ť���͸��ļ�");
			// ���Ͱ�ť����
			sendButton.setEnabled(true);
			return adapterList;
		} else {
			// �õ�path�������ļ�
			File[] files = nowFile.listFiles();
			if (files != null) {
				for (File file : files) {
					Map<String, Object> map = new TreeMap<String, Object>();
					map.put("name", file.getName());
					map.put("path", file.getPath());
					String fileName = file.getName();
					if (file.isDirectory()) {
						map.put("img", R.drawable.folder);
					} else if (checkEndName(fileName, this.getResources()
							.getStringArray(R.array.fileEndingMid))) {
						map.put("img", R.drawable.file_icon_mid);
					} else if (checkEndName(fileName, this.getResources()
							.getStringArray(R.array.fileEndingMp3))) {
						map.put("img", R.drawable.file_icon_mp3);
					} else if (checkEndName(fileName, this.getResources()
							.getStringArray(R.array.fileEndingOffice))) {
						map.put("img", R.drawable.file_icon_office);
					} else if (checkEndName(fileName, this.getResources()
							.getStringArray(R.array.fileEndingPdf))) {
						map.put("img", R.drawable.file_icon_pdf);
					} else if (checkEndName(fileName, this.getResources()
							.getStringArray(R.array.fileEndingPicture))) {
						map.put("img", R.drawable.file_icon_picture);
					} else if (checkEndName(fileName, this.getResources()
							.getStringArray(R.array.fileEndingRar))) {
						map.put("img", R.drawable.file_icon_rar);
					} else if (checkEndName(fileName, this.getResources()
							.getStringArray(R.array.fileEndingTheme))) {
						map.put("img", R.drawable.file_icon_theme);
					} else if (checkEndName(fileName, this.getResources()
							.getStringArray(R.array.fileEndingTxt))) {
						map.put("img", R.drawable.file_icon_txt);
					} else if (checkEndName(fileName, this.getResources()
							.getStringArray(R.array.fileEndingVideo))) {
						map.put("img", R.drawable.file_icon_video);
					} else if (checkEndName(fileName, this.getResources()
							.getStringArray(R.array.fileEndingWav))) {
						map.put("img", R.drawable.file_icon_wav);
					} else if (checkEndName(fileName, this.getResources()
							.getStringArray(R.array.fileEndingWma))) {
						map.put("img", R.drawable.file_icon_wma);
					} else if (checkEndName(fileName, this.getResources()
							.getStringArray(R.array.fileEndingZip))) {
						map.put("img", R.drawable.file_icon_zip);
					} else {
						map.put("img", R.drawable.file_icon_default);
					}
					adapterList.add(map);
				}
			}
			// ����
			Collections.sort(adapterList,
					new Comparator<Map<String, Object>>() {
						public int compare(Map<String, Object> o1,
								Map<String, Object> o2) {
							File f1file = new File(FileActivity.this.path
									+ File.separator + o1.get("name"));
							File f2file = new File(FileActivity.this.path
									+ File.separator + o2.get("name"));
							if (o1.get("name").equals("��һ��")
									&& !o2.get("name").equals("��һ��")) {
								return 1;
							} else if (o1.get("name").equals("��Ŀ¼")
									&& !o2.get("name").equals("��Ŀ¼")) {
								return 1;
							} else if (!o1.get("name").equals("��һ��")
									&& o2.get("name").equals("��һ��")) {
								return -1;
							} else if (!o1.get("name").equals("��Ŀ¼")
									&& o2.get("name").equals("��Ŀ¼")) {
								return -1;
							} else if (f1file.isDirectory() && f2file.isFile()) {
								return -1;
							} else if (f1file.isFile() && f2file.isDirectory()) {
								return 1;
							}
							return ((String) (o1.get("name")))
									.compareTo((String) (o2.get("name")));
						}
					});
		}
		// ��ǰ·����Ӧ�����ļ��У����Ͱ�ť������
		sendButton.setEnabled(false);
		return adapterList;

	}

	/** �����ϼ�Ŀ¼ */
	private void goToParent() {
		File file = new File(path);
		File pFile = file.getParentFile();
		if (pFile == null) {
			Toast.makeText(this, "��ǰ·���Ѿ��Ǹ�Ŀ¼����������һ��", Toast.LENGTH_SHORT)
					.show();
			reConstructList(path);
		} else {
			path = pFile.getAbsolutePath();
			reConstructList(path);
		}

	}

	/** ͨ���ļ���׺���ж��Ƿ�Ϊ�������͵��ļ� */
	private boolean checkEndName(String fileName, String[] fileEnds) {
		for (String endName : fileEnds) {
			if (fileName.endsWith(endName)) {
				return true;
			}
		}
		return false;
	}

	/** �õ���ǰĿ¼�ľ���·�����������ڲ���ʹ�� */
	public String getCurrentDirectory() {
		return this.path;
	}

	/** �����ļ��� */
	public void operateMenu(final File file) {
		DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (which == 0) {
					showAllFiles(file);
					Intent intent = new Intent();
					intent.putExtra("filePaths", filePaths.substring(2));
					setResult(RESULT_OK, intent);
					finish();
				}
			}
		};
		String[] menu = { "���͸��ļ���" };
		new AlertDialog.Builder(FileActivity.this).setTitle("��ѡ��Ҫ���еĲ���")
				.setItems(menu, onClickListener).show();
	}

	/** �ݹ�����ļ����µ������ļ� */
	public void showAllFiles(File dir) {
		File[] files = dir.listFiles();
		if (files == null) {
			return;
		}
		for (File f : files) {
			if (f.isDirectory()) {
				showAllFiles(f);
			} else if (f.isFile()) {
				String filePath = f.getAbsolutePath();
				filePaths.append("\0" + filePath);
			}
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		intent.putExtra("filePaths", path);
		setResult(RESULT_OK, intent);
		finish();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		if (((String) adapterList.get(position).get("name")).equals("��Ŀ¼")) {
			path = "/";
			reConstructList(path);
		} else if (((String) adapterList.get(position).get("name"))
				.equals("��һ��")) {
			goToParent();
		} else {
			path = (String) adapterList.get(position).get("path");
			reConstructList(path);
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> adapterView, View view,
			int itemId, long position) {
		if (!((String) adapterList.get((int) position).get("name"))
				.equals("��Ŀ¼")
				&& !((String) adapterList.get((int) position).get("name"))
						.equals("��һ��")) {
			vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			long[] pattern = { 0, 30 };
			vibrator.vibrate(pattern, -1);
			path = (String) adapterList.get((int) position).get("path");
			File tempcurrentFile = new File(path);
			operateMenu(tempcurrentFile);
		}
		return false;
	}

	@Override
	public void processMessage(Message msg) {

	}

	@Override
	protected void onStop() {
		super.onStop();
		// �ر�����
		if (vibrator != null) {
			vibrator.cancel();
		}
	}

}
