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
		
		// TTSģ���
		SpeechUtility.createUtility(this, SpeechConstant.APPID + "=54252927");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_lesson);

		preview = (WebView) findViewById(R.id.content);
		importPath = (TextView) findViewById(R.id.importpath);
		tableName = (EditText) findViewById(R.id.et_table_name);
		filePath = null;
	}

	// ����
	public void callName(View v) {
		Intent intent = new Intent(ImportExcelActivity.this,
				CallNameTableSelectActivity.class);
		startActivity(intent);
	}

	// ��ʼ����
	public void start(View v) {
		//����û��ṩ�˱�������ʼ����
		if (tableName.getText().toString().length() != 0 ) {
			MyCourseImporter importer = new MyCourseImporter(this, tableName
					.getText().toString(), filePath);
			importer.parser();
			Toast.makeText(this, "���ڵ���,���Ժ�...", Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(this, "����Ҫ�洢�ı��������Ժ���ʾ", Toast.LENGTH_SHORT).show();
		}
	}

	// Ԥ��
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
			importPath.setText("�����ļ���" + file);
			tableName.setText(file.substring(file.lastIndexOf("/")+1,file.lastIndexOf(".xls")));
			filePath = importPath.getText().toString().substring(5);
		}

	}
}
