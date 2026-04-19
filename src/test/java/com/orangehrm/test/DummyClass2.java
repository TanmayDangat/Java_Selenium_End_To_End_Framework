package com.orangehrm.test;

import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;

public class DummyClass2 extends BaseClass {
	
	@Test
	public void dummyTest() {
		
		//ExtentManager.startTest("Dummy2 Test");            ---This has been implemented in TestListener
		String title = getDriver().getTitle();
		ExtentManager.logStep("Verifying the title");
		assert title.equals("OrangeHRM"):"Test case failed. Title not matching";
		
		System.out.println("Testcase passed. Title is matching");
		ExtentManager.logStep("Validation successful");
	}

}
