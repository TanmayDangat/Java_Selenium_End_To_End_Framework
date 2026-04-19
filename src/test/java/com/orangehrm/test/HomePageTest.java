package com.orangehrm.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.ExtentManager;

public class HomePageTest extends BaseClass {
	
	private LoginPage loginPage;
	private HomePage homePage;
	
	@BeforeMethod
	public void setupPage() {
	
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
	}
	
	@Test
	public void verifyOrangeHRMLogo() {
		//ExtentManager.startTest("Verify Logo Test");          ---This has been implemented in TestListener
		ExtentManager.logStep("Navigating to Login Page entering username and password");
		loginPage.login("Admin", "admin123");
		ExtentManager.logStep("Verifying Logo is visible or not");
		Assert.assertTrue(homePage.isOrangeHRMLogoVisible(), "Logo is not visible");
		ExtentManager.logStep("Validation successful");
		ExtentManager.logStep("Logged out successfully");
	}
	
	@Test
	public void verifyAdminTab() {
		//ExtentManager.startTest("Verify Admin page Test");         ---This has been implemented in TestListener
		ExtentManager.logStep("Navigating to Login Page entering username and password");
		loginPage.login("Admin", "admin123");
		ExtentManager.logStep("Verifying Admin tab is visible or not");
		Assert.assertTrue(homePage.isAdminTabVisible(), "Admin tab is not visible");
		ExtentManager.logStep("Validation successful");
		ExtentManager.logStep("Logged out successfully");
	}
	
	@Test
	public void verifyLogoutTab() {
		//ExtentManager.startTest("Verify Logout Test");          ---This has been implemented in TestListener
		ExtentManager.logStep("Navigating to Login Page entering username and password");
		loginPage.login("Admin", "admin123");
		ExtentManager.logStep("Verifying Logout tab is visible or not");
		homePage.logoutOperation();
		ExtentManager.logStep("Validation successful");
		ExtentManager.logStep("Logged out successfully");
	}

}
