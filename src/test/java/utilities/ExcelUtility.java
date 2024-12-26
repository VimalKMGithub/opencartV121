package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
	private FileInputStream fi;
	private FileOutputStream fo;
	private XSSFWorkbook workBook;
	private XSSFSheet sheet;
	private XSSFRow row;
	private XSSFCell cell;
	private CellStyle style;
	private String path;

	public ExcelUtility(String path) {
		this.path = path;
	}

	public int getRowCount(String sheetName) throws IOException {
		fi = new FileInputStream(path);
		workBook = new XSSFWorkbook(fi);
		sheet = workBook.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum();
		workBook.close();
		fi.close();
		return rowCount;
	}

	public int getCellCount(String sheetName, int rowNum) throws IOException {
		fi = new FileInputStream(path);
		workBook = new XSSFWorkbook(fi);
		sheet = workBook.getSheet(sheetName);
		row = sheet.getRow(rowNum);
		int cellCount = row.getLastCellNum();
		workBook.close();
		fi.close();
		return cellCount;
	}

	public String getCellData(String sheetName, int rowNum, int colNum) throws IOException {
		fi = new FileInputStream(path);
		workBook = new XSSFWorkbook(fi);
		sheet = workBook.getSheet(sheetName);
		row = sheet.getRow(rowNum);
		cell = row.getCell(colNum);

		DataFormatter forMatter = new DataFormatter();
		String data;
		try {
			data = forMatter.formatCellValue(cell);
		} catch (Exception e) {
			data = "";
		}
		workBook.close();
		fi.close();
		return data;
	}

	public void setCellData(String sheetName, int rowNum, int colNum, String data) throws IOException {
		File xlFile = new File(path);
		if (!xlFile.exists()) {
			workBook = new XSSFWorkbook();
			fo = new FileOutputStream(path);
			workBook.write(fo);
		}

		fi = new FileInputStream(path);
		workBook = new XSSFWorkbook(fi);

		if (workBook.getSheetIndex(sheetName) == -1) {
			workBook.createSheet(sheetName);
		}
		sheet = workBook.getSheet(sheetName);

		if (sheet.getRow(rowNum) == null) {
			sheet.createRow(rowNum);
		}
		row = sheet.getRow(rowNum);

		if (row.getCell(colNum) == null) {
			row.createCell(colNum);
		}
		cell = row.getCell(colNum);

		cell.setCellValue(data);
		fo = new FileOutputStream(path);
		workBook.write(fo);

		fo.close();
		workBook.close();
		fi.close();
	}

	public void fillGreenColor(String sheetName, int rowNum, int colNum) throws IOException {
		fi = new FileInputStream(path);
		workBook = new XSSFWorkbook(fi);
		sheet = workBook.getSheet(sheetName);
		row = sheet.getRow(rowNum);
		cell = row.getCell(colNum);

		style = workBook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cell.setCellStyle(style);

		fo = new FileOutputStream(path);
		workBook.write(fo);

		fo.close();
		workBook.close();
		fi.close();
	}

	public void fillRedColor(String sheetName, int rowNum, int colNum) throws IOException {
		fi = new FileInputStream(path);
		workBook = new XSSFWorkbook(fi);
		sheet = workBook.getSheet(sheetName);
		row = sheet.getRow(rowNum);
		cell = row.getCell(colNum);

		style = workBook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cell.setCellStyle(style);

		fo = new FileOutputStream(path);
		workBook.write(fo);

		fo.close();
		workBook.close();
		fi.close();
	}
}