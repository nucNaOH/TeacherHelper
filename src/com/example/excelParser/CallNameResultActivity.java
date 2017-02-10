package com.example.excelParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.teacher.helper.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CallNameResultActivity extends Activity {

	private String presentStuNames;
	private String absentStuNames;
	private String leaveStuName;
	private String selectedTableName;

	private TextView presentResult;
	private TextView absentResult;
	private TextView leaveResult;

	private MyDatabaseHelper mDatabaseHelper;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.layout_call_name_result);
		mDatabaseHelper = new MyDatabaseHelper(this, "myCourse.db3", 1);

		Intent intent = getIntent();

		Bundle bundle = intent.getExtras();
		presentStuNames = bundle.getString("presentStuNames");
		absentStuNames = bundle.getString("absentStuNames");
		leaveStuName = bundle.getString("leaveStuName");
		selectedTableName = bundle.getString("selectedTableName");

		presentStuNames = getFormatNames(presentStuNames);
		absentStuNames = getFormatNames(absentStuNames);
		leaveStuName = getFormatNames(leaveStuName);

		presentResult = (TextView) findViewById(R.id.tv_present_stu_name);
		absentResult = (TextView) findViewById(R.id.tv_absent_stu_name);
		leaveResult = (TextView) findViewById(R.id.tv_leave_stu_name);

		presentResult.append(presentStuNames + "\n");
		absentResult.append(absentStuNames + "\n");
		leaveResult.append(leaveStuName + "\n");

		super.onCreate(savedInstanceState);
	}

	private String getFormatNames(String a) {
		String temp = new String();
		if (a != null) {
			if (a.length() == 0) {
				temp = "��";
			} else {
				temp = "    " + a.substring(1);
			}
			return temp;
		}
		return "��";
	}

	public void saveCallNameResult(View v) {
		// �ѵ�����
		Toast.makeText(this, "���ڱ���...", Toast.LENGTH_LONG).show();
		String sql = null;
		String[] temp = presentStuNames.split(",");
		for (int i = 0; i < temp.length; i++) {
			sql = "update " + selectedTableName + "�ĵ������"
					+ " set ȱϯ����=ȱϯ����+0 where ����='" + temp[i].trim() + "';";
			System.out.println(sql);
			mDatabaseHelper.getReadableDatabase().execSQL(sql);
		}
		String[] temp1 = absentStuNames.split(",");
		for (int i = 0; i < temp1.length; i++) {
			sql = "update " + selectedTableName + "�ĵ������"
					+ " set ȱϯ����=ȱϯ����+1 where ����='" + temp1[i].trim() + "';";
			System.out.println(sql);
			mDatabaseHelper.getReadableDatabase().execSQL(sql);
		}
		String[] temp2 = leaveStuName.split(",");
		for (int i = 0; i < temp2.length; i++) {
			sql = "update " + selectedTableName + "�ĵ������"
					+ " set ��ٴ���=��ٴ���+1 where ����='" + temp2[i].trim() + "';";
			System.out.println(sql);
			mDatabaseHelper.getReadableDatabase().execSQL(sql);
		}
	}

	public void importCallNameResult(View v) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet s = wb.createSheet();
		
		HSSFCellStyle cell_Style = (HSSFCellStyle) wb .createCellStyle();// ����������ʽ  
		cell_Style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
		cell_Style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// ��ֱ�������  
		cell_Style.setWrapText(true); // ����Ϊ�Զ�����  
		HSSFFont cell_Font = (HSSFFont) wb.createFont();  
		cell_Font.setFontName("����");  
		cell_Font.setFontHeightInPoints((short) 8);  
		cell_Style.setFont(cell_Font);  
		cell_Style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // �±߿�  
		cell_Style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// ��߿�  
		cell_Style.setBorderTop(HSSFCellStyle.BORDER_THIN);// �ϱ߿�  
		cell_Style.setBorderRight(HSSFCellStyle.BORDER_THIN);// �ұ߿� 

		wb.setSheetName(0, "�����ĵ�����Ϣ");
		HSSFRow r = s.createRow(0);
		HSSFCell c = r.createCell(0);
		c.setCellStyle(cell_Style);
		
		// ������һ��
		c.setCellValue("����");
		c = r.createCell(1);
		c.setCellStyle(cell_Style);
		c.setCellValue("ȱϯ����");
		c = r.createCell(2);
		c.setCellStyle(cell_Style);
		c.setCellValue("��ٴ���");

		Cursor cursor = mDatabaseHelper.getReadableDatabase()
				.rawQuery(
						"select ����,ȱϯ����,��ٴ��� from " + selectedTableName
								+ "�ĵ������", null);

		// ѭ�������������
		for (int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToNext();
			r = s.createRow(i+1);
			for (int j = 0; j < 3; j++) {
				if(j == 0){
				c = r.createCell(j);
				c.setCellStyle(cell_Style);
				c.setCellValue(cursor.getString(j));
				System.out.println(cursor.getString(j));
				}else{
					c = r.createCell(j);
					c.setCellStyle(cell_Style);
					c.setCellValue(cursor.getInt(j));
					System.out.println(cursor.getString(j));
				}
			}
		}
		cursor.close();
		// ����ļ�
		File exportFile = new File("/mnt/sdcard/exportFile.xls");
		FileOutputStream out;
		out = new FileOutputStream(exportFile);
		wb.write(out);
		out.close();
	}
}
