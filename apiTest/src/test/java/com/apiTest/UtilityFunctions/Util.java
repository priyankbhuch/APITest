package com.apiTest.UtilityFunctions;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.apiTest.BaseClass.BaseClass;

public class Util extends BaseClass {

	static String content[][] = null;
	static FileInputStream file = null;
	static XSSFWorkbook workbook = null;
	static XSSFSheet sheet = null;
	static String pathName = System.getProperty("user.dir") + "/TestDataSheets/";
	static Properties props = new Properties();
	static String strFileName = "config.properties";
	static String strValue;

	/**
	 * This method will get the value against a specific key in the
	 * Config.properties file
	 * 
	 * @param strKey
	 * @return
	 * @throws FrameworkException
	 */
	public static String getProperty(String strKey) throws FrameworkException {
		try {
			File f = new File(strFileName);
			if (f.exists()) {
				FileInputStream in = new FileInputStream(f);
				props.load(in);
				strValue = props.getProperty(strKey);
				in.close();
			} else
				throw new FrameworkException("Configuration File not found.");
		} catch (Exception e) {
			throw new FrameworkException("Unknown Error encountered while reading " + strKey
					+ " from configuration file. ---" + e.getClass() + "---" + e.getMessage());
		}
		if (strValue != null) {
			return strValue;
		} else {

			throw new FrameworkException(
					"Value '" + strKey + "' not configured in config file. Contact automation team");
		}

	}

	/**
	 * This method will create a folder at a specific path
	 * 
	 * @param folderPath
	 * @return boolean
	 */
	public static boolean createFolder(String folderPath) {
		boolean result = false;
		File directory = new File(folderPath);
		if (!directory.exists()) {
			result = directory.mkdir();
		}
		return result;
	}

	/**
	 * This method will return Current Date in yyyyMMdd format
	 * 
	 * @return currentDate
	 */
	public static String currentDate() {
		return new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()).toString();
	}

	/**
	 * This method will return Current Time Stamp in yyyyMMdd_HHmmss format
	 * 
	 * @return currentTimeStamp
	 */
	public static String currentTimeStamp() {
		return new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()).toString();
	}

}
