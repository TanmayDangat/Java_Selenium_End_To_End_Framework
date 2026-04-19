package com.orangehrm.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;

public class TestListener implements ITestListener{

	//Triggered when a test starts
	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		//Start logging in ExtentReport
		ExtentManager.startTest(testName);
		ExtentManager.logStep("Test started: " + testName);
	}

	//Triggered when a Test succeeds
	@Override
	public void onTestSuccess(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Test Passed Successfully", "Test End: " + testName + " - ✔ Test Passed");
	}

	//Triggered when a Test fails
	@Override
	public void onTestFailure(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		String failureMessage = result.getThrowable().getMessage();
		ExtentManager.logStep(failureMessage);
		ExtentManager.logFailure(BaseClass.getDriver(), "Test Failed", "Test End: " + testName + " - ❌ Test Failed");
	}

	//Triggered when a Test Skips
	@Override
	public void onTestSkipped(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		ExtentManager.logSkip("Test Skipped " + testName);
	}

	//Triggered when a suite starts
	@Override
	public void onStart(ITestContext context) {
		//Initialize Extent Reports
		ExtentManager.getReporter();
	}

	//Triggered when the suite ends
	@Override
	public void onFinish(ITestContext context) {
		//Flush the Extent Reports
		ExtentManager.endTest();
	}

	
	
}
