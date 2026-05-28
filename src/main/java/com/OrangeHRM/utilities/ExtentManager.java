package com.OrangeHRM.utilities;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test=new ThreadLocal<>();
	private static Map<Long,WebDriver> driverMap= new HashMap<>();

//Initialize the extent report
	public static ExtentReports getReporter() 
	{
		if (extent==null) 
		{
			String reportPath=System.getProperty("user.dir")+"src\\test\\resources\\ExtentReports.html";
			ExtentSparkReporter spark=new ExtentSparkReporter(reportPath);
		//nb.spark is the report generator
		spark.config().setReportName("AUTOMATION BY INSIDER");
		spark.config().setDocumentTitle("OrangeHRM Report");
		spark.config().setTheme(Theme.DARK);
		
		//nb.extent is the reporting engine,“ExtentReports, use SparkReporter to generate the actual HTML report.”
		extent=new ExtentReports();
		//adding system info
		extent.attachReporter(spark);
		extent.setSystemInfo("operating System", System.getProperty("os.name"));
		extent.setSystemInfo("Java Version", System.getProperty("java.version"));
		extent.setSystemInfo("User Name", System.getProperty("user.name"));

		
		}
		return extent;
	}

	//Start creating tests
	public static ExtentTest startTest(String testName) 
	{
		
		ExtentTest extentTest=getReporter().createTest(testName);
		test.set(extentTest);//“ it tell us to Store this ExtentTest object separately for the CURRENT THREAD.”
		return extentTest;
		
	}
	
	//end test
	public static void endTest()
	{
		extent.flush();	
	}
	
	//Get current Thread test
	public static ExtentTest getTest() 
	{
		return test.get();	
	}
//Method to get the name of the current test
	
//Register WebDriver for current Thread
	public static void registerDriver(WebDriver driver) 
	{driverMap.put(Thread.currentThread().getId(), driver);
}}
