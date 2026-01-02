package com.orangehrm.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;

public class TestListener implements ITestListener {

	//Triggered when the Test starts
	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		//Start logging in Extent Reports
		ExtentManager.startTest(testName);
		ExtentManager.logStep("Test Started: "+testName);
	}
	

	//Triggered when a Test succeeds
	@Override
	public void onTestSuccess(ITestResult result) {
		String testname =  result.getMethod().getMethodName();
		ExtentManager.LogStepWithScreenshot(BaseClass.getDriver(), "Test Passed Successfully!", "Test End: " +testname+ "✅ Passed");
	}

	//Triggered when a Test fails
	@Override
	public void onTestFailure(ITestResult result) {
		String testname =  result.getMethod().getMethodName();
		String failureMessage=result.getThrowable().getMessage();
		ExtentManager.logStep("Failure Reason: " + failureMessage);
		ExtentManager.logFailure(BaseClass.getDriver(), "Test Failed!", "Test End: "+ testname + "Failed");
	}

	//Triggered when a Test is skipped
	@Override
	public void onTestSkipped(ITestResult result) {
		String testname =  result.getMethod().getMethodName();
		ExtentManager.logSkip("Test Skipped: " + testname + "⏭️ Skipped");
	}

	//Triggered when a suite starts
	@Override
	public void onStart(ITestContext context) {
		//Initialize Extent Reports
		ExtentManager.getReporter();
	}

	//Triggered when the suite finishes
	@Override
	public void onFinish(ITestContext context) {
		//Flush the Extent Reports
		ExtentManager.endTest();
	}
	
	

}
