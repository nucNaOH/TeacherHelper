package com.example.excelParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.teacher.helper.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MySDFileExplorer extends Activity {
	ListView listView;
	private int currentItem = 0;
	private List<String> excelList = new ArrayList<String>(); // excel�ļ��б�
	// ��¼��ǰ�ĸ��ļ���
	File currentParent;
	// ��¼��ǰ·���µ������ļ����ļ�����
	File[] currentFiles;
	private String result;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_sdfiles);
		listView = (ListView) findViewById(R.id.list);
		getLocalExcelList();
	}
	public void exit(View v){
		Intent i = getIntent();
		i.putExtra("result", " ");
		MySDFileExplorer.this.setResult(0, i);
		MySDFileExplorer.this.finish();
	}
	private void getLocalExcelList() {
		// TODO Auto-generated method stub
		getFiles("/sdcard/"); // ��ȡSD���ϵ�ȫ��excel�ļ�
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, excelList); // ����һ��������
		ListView listview = (ListView) findViewById(R.id.list); // ��ȡ���ֹ���������ӵ�ListView���
		listview.setAdapter(adapter); // ����������ListView����
		// �������б���ʱ��������
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> listView, View view,
					int position, long id) {
				currentItem = position; // ����ǰ�б��������ֵ��ֵ��currentItem
				result = excelList.get(currentItem); 
				Intent i = getIntent();
				i.putExtra("result", result);
				MySDFileExplorer.this.setResult(0, i);
				MySDFileExplorer.this.finish();
			}
		});
	}
	private void getFiles(String url) {
		File files = new File(url); // �����ļ�����
		File[] file = files.listFiles();
		try {
			for (File f : file) { // ͨ��forѭ��������ȡ�����ļ�����
				if (f.isDirectory()) { // �����Ŀ¼��Ҳ�����ļ���
					getFiles(f.getAbsolutePath()); // �ݹ����
				} else {
					if (isExcelFile(f.getPath())) { // �����excel�ļ�
						excelList.add(f.getPath()); // ���ļ���·����ӵ�list������
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); // ����쳣��Ϣ
		}
	}
	private static boolean isExcelFile(String path) {
			if (path.endsWith(".xls")) { // �ж��Ƿ�����кϷ���excel�ļ�
				return true;
			}
		return false;
	}

}