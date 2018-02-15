package com.alation.selenium.automation;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;
import org.testng.ITestResult;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * A wrapper for reporter which helps in reporting the test results
 * @author Saravana Raguram Ravindran
 * @date 02/01/2018
 */

public class ExtentReporter 
{
	public static ExtentReports report;
	public static String reportName;
	static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();

	//Initialize object of ExtentReporter in BeforeSuite
	public ExtentReporter(String reportName)
	{
		report = new ExtentReports("test-output" + File.separator + reportName+ ".html");
		ExtentReporter.reportName = reportName;
	}
	
	public synchronized void initializeLoggerBeforeClass(String methodName) 
	{
		 initializeLoggerBeforeClass(methodName,null);
	}
	
	public synchronized void initializeLoggerBeforeClass(String methodName,String category) 
	{
		ExtentTest logger = report.startTest(methodName);	
		if(category!=null)
			logger.assignCategory(category);
		extentTestMap.put((int) (long) (Thread.currentThread().getId()), logger);
	}
	
	//Invoke in AfterMethod
	public synchronized void PrintReport(ITestResult result)
	{
		switch (result.getStatus()) {
        case ITestResult.FAILURE:
        	System.out.println(result.getThrowable());
        	fail(result.getThrowable());
        	break;
        case ITestResult.SKIP:
        	System.out.println(result.getThrowable());
        	skip(result.getThrowable());
            break;
        case ITestResult.SUCCESS:
        	pass("Passed");
            break;
        default:
            break;
        }
		report.flush();
		report.endTest(getTest());
	}
	
	//Invoke in AfterSuite
	public synchronized void zipReport()
	{
		System.out.println("Report accessible at: test-output" + File.separator + reportName+".html");
	}
	
	private synchronized ExtentTest getTest() 
	{
		return extentTestMap.get((int) (long) (Thread.currentThread().getId()));
	}
	
	/*
	 * Wrapper methods for TestNG asserts
	 */
	public void assertTrue(boolean condition) 
	{        
		assertTrue(condition, "");
	}
	
	public void assertTrue(boolean condition, String failureMsg) 
	{        
		assertTrue(condition, null, failureMsg);     
	}

	public void assertTrue(boolean condition, String passMsg, String failureMsg) 
	{        
		try
		{
			Assert.assertTrue(condition, failureMsg);
			if(passMsg!=null)
				getTest().log(LogStatus.PASS, passMsg);
			else
				getTest().log(LogStatus.PASS,"Assert condition is true");
		}
		catch(AssertionError e)
		{
			addToErrorBuffer(e);
		}         
	}

	public void assertFalse(boolean condition) 
	{
		assertFalse(condition, "");
	}
	
	public void assertFalse(boolean condition, String failureMsg) 
	{
		assertFalse(condition,null,failureMsg);
	}

	public void assertFalse(boolean condition, String passMsg, String failureMsg) 
	{
		try
		{
			Assert.assertFalse(condition, failureMsg);
			if(passMsg!=null)
				getTest().log(LogStatus.PASS, passMsg);
			else
				getTest().log(LogStatus.PASS,"Assert condition is false");
		}
		catch(AssertionError e)
		{
			addToErrorBuffer(e);
		}
	}

	public void assertEquals(Object actual, Object expected)
	{
		assertEquals(actual, expected, "");
	}
	
	public void assertEquals(Object actual, Object expected, String failureMsg)
	{
		assertEquals(actual,expected, null,failureMsg);
	}

	public void assertEquals(Object actual, Object expected, String passMsg, String failureMsg)
	{
		try
		{
			Assert.assertEquals(actual, expected, failureMsg);
			if(passMsg!=null)
				getTest().log(LogStatus.PASS, passMsg);
			else
				getTest().log(LogStatus.PASS, actual+" is equal to "+expected);
		}
		catch(AssertionError e)
		{
			addToErrorBuffer(e);
		}
	}

	public void assertNotEquals(Object obj1, Object obj2)
	{
		assertNotEquals(obj1, obj2, "");
	}
	
	public void assertNotEquals(Object obj1, Object obj2,String failureMsg)
	{
		assertNotEquals(obj1,obj2,null,failureMsg);
	}

	public void assertNotEquals(Object obj1, Object obj2,String passMsg, String failureMsg)
	{
		try
		{
			Assert.assertNotEquals(obj1,obj2,failureMsg);
			if(passMsg!=null)
				getTest().log(LogStatus.PASS, passMsg);
			else
				getTest().log(LogStatus.PASS, obj1+" is not equal to "+obj2);
		}
		catch(AssertionError e)
		{
			addToErrorBuffer(e);
		}
	}

	public void fail(String failureMsg) 
	{
		try
		{
			Assert.fail(failureMsg);
		}
		catch(AssertionError e)
		{
			addToErrorBuffer(e);
		}
	}

	public void pass(String msg) 
	{
		try
		{                
			getTest().log(LogStatus.PASS,msg);
		}
		catch(NullPointerException ex)
		{
			getTest().log(LogStatus.FAIL,ex.getMessage());
		}
	}

	public void skip(Throwable t) 
	{
		try
		{                
			getTest().log(LogStatus.SKIP,t);
		}
		catch(NullPointerException ex)
		{
			getTest().log(LogStatus.SKIP, ex.getMessage());
		}
	}

	public void fail(Throwable t) 
	{
		try
		{                
			getTest().log(LogStatus.FAIL,t);
		}
		catch(NullPointerException ex)
		{
			getTest().log(LogStatus.FAIL, ex.getMessage());
		}
	}

	/*
	 * Writes the status to logger incase of failure
	 */
	public void fail(Exception e) 
	{
		try
		{                
			getTest().log(LogStatus.FAIL,e);
		}
		catch(NullPointerException ex)
		{
			getTest().log(LogStatus.FAIL,ex.getMessage());
		}
	}
	
	public void log(String msg) 
	{
		try
		{                
			getTest().log(LogStatus.INFO, msg);
		}
		catch(NullPointerException ex)
		{
			getTest().log(LogStatus.FAIL,ex.getMessage());
		}
	}

	private void addToErrorBuffer(AssertionError e)
	{   
		try
		{                
			getTest().log(LogStatus.FAIL,e);
			e.printStackTrace(System.out);
		}
		catch(NullPointerException ex)
		{
			getTest().log(LogStatus.FAIL,ex.getMessage());
		}
	}
}
