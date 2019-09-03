package com.apiTest.BaseClass;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.apiTest.UtilityFunctions.FrameworkException;
import com.apiTest.UtilityFunctions.Util;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class BaseClass {

	public static ExtentTest Report;
	public static ExtentReports extent;
	public static ExtentHtmlReporter htmlReporter;
	public static String systemDir = System.getProperty("user.dir");
	public static String resultFolderPath = systemDir + "\\Test Result" + "\\" + Util.currentDate() + "\\";

	@BeforeSuite
	public void initialization(ITestContext contextContext) {
		Util.createFolder(resultFolderPath);
		String reportName = resultFolderPath + "APITest" + Util.currentTimeStamp() + ".html";
		htmlReporter = new ExtentHtmlReporter(reportName);
		htmlReporter.config().setDocumentTitle("API Test Report");
		htmlReporter.config().setReportName("API Test Execution Report");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

	}

	@BeforeMethod
	public void createTest(ITestResult contextContext) throws FrameworkException {
		Report = extent.createTest(contextContext.getMethod().getMethodName(),
				contextContext.getMethod().getMethodName());
		Report.info(MarkupHelper.createLabel("API Test Execution started", ExtentColor.AMBER));
		Report.info("BaseURL : " + Util.getProperty("BaseURL"));
	}

	@AfterMethod
	public void flushReport() {
		Report.info(MarkupHelper.createLabel("API Test Execution completed", ExtentColor.AMBER));
		extent.flush();
	}

}
