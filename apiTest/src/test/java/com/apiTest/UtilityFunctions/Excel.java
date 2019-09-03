package com.apiTest.UtilityFunctions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.apiTest.BaseClass.BaseClass;

public class Excel extends BaseClass {

	static FileInputStream file = null;
	static XSSFWorkbook workbook = null;
	static XSSFSheet sheet = null;
	static String pathName = System.getProperty("user.dir") + "/TestDataSheets/";

	/**
	 * This method reads the Excel in 2D array located at the TestDataSheets Folder
	 * in the Root Directory
	 * 
	 * @author Priyank
	 * @param fileName
	 * @param sheetName
	 * @return tabArray
	 * @throws FrameworkException
	 */
	public static String[][] ReadExcelData(String fileName, String sheetName) throws FrameworkException {
		try {
			String[][] tabArray = null;
			String CellVal;
			DataFormatter df = new DataFormatter();
			String FilePath = pathName + fileName;
			file = new FileInputStream(new File(FilePath));
			workbook = new XSSFWorkbook(file);
			sheet = workbook.getSheet(sheetName);
			int totalRows = sheet.getLastRowNum();
			int startRow = 1;
			int startCol = 0;
			int totalCols = sheet.getRow(0).getLastCellNum();
			tabArray = new String[totalRows][totalCols];

			for (int i = startRow; i <= totalRows; i++) {
				for (int j = startCol; j < totalCols; j++) {
					Row row = sheet.getRow(i);
					CellVal = df.formatCellValue(row.getCell(j));
					tabArray[i - 1][j] = CellVal;
				}
			}

			workbook.close();
			return tabArray;
		} catch (FileNotFoundException e) {
			throw new FrameworkException("File " + fileName + " not found for reading.");
		} catch (IOException e) {
			throw new FrameworkException("Exception occured while reading " + fileName);
		} catch (Exception e) {
			throw new FrameworkException("Unknown Exception while reading " + fileName + "&" + sheetName + "---"
					+ e.getClass() + "---" + e.getMessage());
		}
	}
}
