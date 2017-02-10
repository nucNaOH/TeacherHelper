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
				temp = "无";
			} else {
				temp = "    " + a.substring(1);
			}
			return temp;
		}
		return "无";
	}

	public void saveCallNameResult(View v) {
		// 已到的人
		Toast.makeText(this, "正在保存...", Toast.LENGTH_LONG).show();
		String sql = null;
		String[] temp = presentStuNames.split(",");
		for (int i = 0; i < temp.length; i++) {
			sql = "update " + selectedTableName + "的点名结果"
					+ " set 缺席次数=缺席次数+0 where 姓名='" + temp[i].trim() + "';";
			System.out.println(sql);
			mDatabaseHelper.getReadableDatabase().execSQL(sql);
		}
		String[] temp1 = absentStuNames.split(",");
		for (int i = 0; i < temp1.length; i++) {
			sql = "update " + selectedTableName + "的点名结果"
					+ " set 缺席次数=缺席次数+1 where 姓名='" + temp1[i].trim() + "';";
			System.out.println(sql);
			mDatabaseHelper.getReadableDatabase().execSQL(sql);
		}
		String[] temp2 = leaveStuName.split(",");
		for (int i = 0; i < temp2.length; i++) {
			sql = "update " + selectedTableName + "的点名结果"
					+ " set 请假次数=请假次数+1 where 姓名='" + temp2[i].trim() + "';";
			System.out.println(sql);
			mDatabaseHelper.getReadableDatabase().execSQL(sql);
		}
	}

	public void importCallNameResult(View v) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet s = wb.createSheet();
		
		HSSFCellStyle cell_Style = (HSSFCellStyle) wb .createCellStyle();// 设置字体样式  
		cell_Style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
		cell_Style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直对齐居中  
		cell_Style.setWrapText(true); // 设置为自动换行  
		HSSFFont cell_Font = (HSSFFont) wb.createFont();  
		cell_Font.setFontName("宋体");  
		cell_Font.setFontHeightInPoints((short) 8);  
		cell_Style.setFont(cell_Font);  
		cell_Style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框  
		cell_Style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框  
		cell_Style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框  
		cell_Style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框 

		wb.setSheetName(0, "导出的点名信息");
		HSSFRow r = s.createRow(0);
		HSSFCell c = r.createCell(0);
		c.setCellStyle(cell_Style);
		
		// 创建第一行
		c.setCellValue("姓名");
		c = r.createCell(1);
		c.setCellStyle(cell_Style);
		c.setCellValue("缺席次数");
		c = r.createCell(2);
		c.setCellStyle(cell_Style);
		c.setCellValue("请假次数");

		Cursor cursor = mDatabaseHelper.getReadableDatabase()
				.rawQuery(
						"select 姓名,缺席次数,请假次数 from " + selectedTableName
								+ "的点名结果", null);

		// 循环创建后面的行
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
		// 输出文件
		File exportFile = new File("/mnt/sdcard/exportFile.xls");
		FileOutputStream out;
		out = new FileOutputStream(exportFile);
		wb.write(out);
		out.close();
	}
}
