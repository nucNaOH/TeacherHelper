package com.example.excelParser;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

public class MyCourseImporter {

	private String filePath;
	private Context context;
	private String tableName;
	public String[] course;

	private MyDatabaseHelper dbHelper;

	public MyCourseImporter(Context context, String tableName,String filePath) {
		this.filePath = filePath;
		this.context = context;
		this.tableName = tableName;
		this.course = new String[100];
		dbHelper = new MyDatabaseHelper(context, "myCourse.db3", 1);
	}

	// 根据filePath创建excel工作薄
	private static HSSFWorkbook readMyCourseFile(String filePath)
			throws IOException {
		return new HSSFWorkbook(new FileInputStream(filePath));
	}

	//得到excel第一行的内容，存到course[]里，用来构建表
	public void getTableColumnsName(HSSFSheet sheet) {

		// 得到第一行
		int firstRowNum = sheet.getFirstRowNum();
		HSSFRow firstRow = sheet.getRow(firstRowNum);
		// 得到第一行的列数
		int firstRowColumnNum = firstRow.getPhysicalNumberOfCells();
		// 循环得到第一行里每一列的cell
		for (int i = 0; i < firstRowColumnNum; i++) {
			HSSFCell cell = firstRow.getCell(i);
			String value = null;
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_FORMULA:
				value = "" + cell.getCellFormula();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				value = "" + cell.getNumericCellValue();
				break;
			case HSSFCell.CELL_TYPE_STRING:
				value = "" + cell.getStringCellValue();
				break;

			default:
				value = "空";
				break;
			}
			course[i] = value;
			// System.out.println("firstRowCell:" + cell.getColumnIndex()
			// + "; VALUE=" + value + " to "+ course[i]);

		}
	}

	//得到并保存所有cell里的数据，一行一行的保存
	public void setAllTableCellIntoDB(HSSFWorkbook wb) {
		// for (int k = 0; k < wb.getNumberOfSheets(); k++) {
		HSSFSheet sheet = wb.getSheetAt(0);
		int rows = sheet.getPhysicalNumberOfRows();

		// System.out.println("Sheet " + k + " \"" + wb.getSheetName(k)
		// + "\" has " + rows + " row(s).");

		for (int r = 1; r < rows; r++) {
			HSSFRow row = sheet.getRow(r);

			if (row == null) {
				continue;
			}

			int cells = row.getPhysicalNumberOfCells();
			String[] rowValue = new String[cells];
			// System.out.println("\nROW " + row.getRowNum() + " has " + cells
			// + " cell(s).");
			for (int c = 0; c < cells; c++) {
				HSSFCell cell = row.getCell(c);

				switch (cell.getCellType()) {

				case HSSFCell.CELL_TYPE_FORMULA:
					rowValue[c] = "" + cell.getCellFormula();
					break;

				case HSSFCell.CELL_TYPE_NUMERIC:
					rowValue[c] = "" + cell.getNumericCellValue();
					break;

				case HSSFCell.CELL_TYPE_STRING:
					rowValue[c] = "" + cell.getStringCellValue();
					break;

				default:
					rowValue[c] = "空";
					break;
				}
				// value = value.substring(value.lastIndexOf("value=") + 6);
				// dbHelper.getWritableDatabase().insert("mycourse",
				// course[c], value);
				System.out.println("CELL =" + rowValue[c]);
			}
			StringBuilder sb = new StringBuilder("insert into "+tableName+" values(null ");
			for (int i = 0; i < cells; i++) {
				sb.append(", ?");
			}
			sb.append(");");
			dbHelper.getWritableDatabase().execSQL(sb.toString(), rowValue);
		}
		// }

	}

	public void parser() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					// 创建工作薄
					HSSFWorkbook wb = MyCourseImporter
							.readMyCourseFile(filePath);
					// 解析第一列的第一行数据，用String[] course存放
					HSSFSheet sheet0 = wb.getSheetAt(0);
					getTableColumnsName(sheet0);
					// 若解析到数据，根据course[]创建表
					dbHelper.createMyCourseTable(
							dbHelper.getWritableDatabase(), tableName, course);
//					Toast.makeText(context, tableName + "表创建成功", Toast.LENGTH_SHORT).show();
					setAllTableCellIntoDB(wb);
//					Toast.makeText(context, tableName + "表导入成功", Toast.LENGTH_SHORT).show();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();
	}

}