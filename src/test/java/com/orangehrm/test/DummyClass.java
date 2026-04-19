package com.orangehrm.test;

import org.testng.SkipException;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;

public class DummyClass extends BaseClass {
	
	@Test
	public void dummyTest() {
		
		//ExtentManager.startTest("Dummy1 Test");             ---This has been implemented in TestListener
		String title = getDriver().getTitle();
		ExtentManager.logStep("Verifying the title");
		assert title.equals("OrangeHRM"):"Test case failed. Title not matching";
		
		System.out.println("Testcase passed. Title is matching");
		ExtentManager.logSkip("This case is skipped");
		throw new SkipException("Skipping the test as part of testing");
	}

}
