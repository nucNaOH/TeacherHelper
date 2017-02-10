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
	private List<String> excelList = new ArrayList<String>(); // excel文件列表
	// 记录当前的父文件夹
	File currentParent;
	// 记录当前路径下的所有文件的文件数组
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
		getFiles("/sdcard/"); // 获取SD卡上的全部excel文件
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, excelList); // 创建一个适配器
		ListView listview = (ListView) findViewById(R.id.list); // 获取布局管理器中添加的ListView组件
		listview.setAdapter(adapter); // 将适配器与ListView关联
		// 当单击列表项时播放音乐
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> listView, View view,
					int position, long id) {
				currentItem = position; // 将当前列表项的索引值赋值给currentItem
				result = excelList.get(currentItem); 
				Intent i = getIntent();
				i.putExtra("result", result);
				MySDFileExplorer.this.setResult(0, i);
				MySDFileExplorer.this.finish();
			}
		});
	}
	private void getFiles(String url) {
		File files = new File(url); // 创建文件对象
		File[] file = files.listFiles();
		try {
			for (File f : file) { // 通过for循环遍历获取到的文件数组
				if (f.isDirectory()) { // 如果是目录，也就是文件夹
					getFiles(f.getAbsolutePath()); // 递归调用
				} else {
					if (isExcelFile(f.getPath())) { // 如果是excel文件
						excelList.add(f.getPath()); // 将文件的路径添加到list集合中
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); // 输出异常信息
		}
	}
	private static boolean isExcelFile(String path) {
			if (path.endsWith(".xls")) { // 判断是否包含有合法的excel文件
				return true;
			}
		return false;
	}

}