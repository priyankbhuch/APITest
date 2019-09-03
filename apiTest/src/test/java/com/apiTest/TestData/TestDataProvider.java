package com.apiTest.TestData;

import org.testng.annotations.DataProvider;

import com.apiTest.UtilityFunctions.Excel;
import com.apiTest.UtilityFunctions.FrameworkException;
import com.apiTest.UtilityFunctions.Util;

public class TestDataProvider {

	@DataProvider(name = "APITestData")
	public static Object[][] testAPIData() throws FrameworkException {
		return Excel.ReadExcelData(Util.getProperty("TestDataFileName"), Util.getProperty("TestDataSheetName"));
	}

}
