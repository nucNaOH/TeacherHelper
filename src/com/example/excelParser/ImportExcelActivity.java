package com.example.excelParser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.teacher.helper.R;

public class ImportExcelActivity extends Activity {

	private final static int RC_IMPORT = 1;

	private WebView preview;
	private TextView importPath;
	private EditText tableName;

	private String filePath;

	protected void onCreate(Bundle savedInstanceState) {
		
		// TTS模块儿
		SpeechUtility.createUtility(this, SpeechConstant.APPID + "=54252927");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_lesson);

		preview = (WebView) findViewById(R.id.content);
		importPath = (TextView) findViewById(R.id.importpath);
		tableName = (EditText) findViewById(R.id.et_table_name);
		filePath = null;
	}

	// 点名
	public void callName(View v) {
		Intent intent = new Intent(ImportExcelActivity.this,
				CallNameTableSelectActivity.class);
		startActivity(intent);
	}

	// 开始导入
	public void start(View v) {
		//如果用户提供了表名，开始导入
		if (tableName.getText().toString().length() != 0 ) {
			MyCourseImporter importer = new MyCourseImporter(this, tableName
					.getText().toString(), filePath);
			importer.parser();
			Toast.makeText(this, "正在导入,请稍候...", Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(this, "输入要存储的表名，供以后显示", Toast.LENGTH_SHORT).show();
		}
	}

	// 预览
	public void preview(View v) {
		// ExcelParser parser = new ExcelParser(this, filepath);
		// parser.parse();
		String content = new String();
		MyExcelWebViewer viewer = new MyExcelWebViewer(filePath);
		try {
			content = viewer.convert();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		preview.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
	}

	public void findFile(View v) {

		Intent intent = new Intent(ImportExcelActivity.this,
				MySDFileExplorer.class);
		startActivityForResult(intent, RC_IMPORT);

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == RC_IMPORT) {
			Bundle bundle = data.getExtras();
			String file = bundle.getString("result");
			importPath.setText("导入文件：" + file);
			tableName.setText(file.substring(file.lastIndexOf("/")+1,file.lastIndexOf(".xls")));
			filePath = importPath.getText().toString().substring(5);
		}

	}
}
