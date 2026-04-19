package com.orangehrm.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.ExtentManager;

public class LoginPageTest extends BaseClass {
	
	private LoginPage loginPage;
	private HomePage homePage;
	
	@BeforeMethod
	public void setupPage() {
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
	}
	
	@Test
	public void verifyValidLoginPage() {
		//ExtentManager.startTest("Valid Login Test");            ---This has been implemented in TestListener
		ExtentManager.logStep("Navigating to Login Page entering username and password");
		loginPage.login("Admin", "admin123");
		ExtentManager.logStep("Verifying Admin tab is visible or not");
		Assert.assertTrue(homePage.isAdminTabVisible(), "Admin tab should be visible after successful login");
		ExtentManager.logStep("Validation successful");
		homePage.logoutOperation();
		ExtentManager.logStep("Logged out successfully");
		staticWait(2);
	}
	
	@Test
	public void invalidLoginTest() {
		//ExtentManager.startTest("Invalid Login Test");            ---This has been implemented in TestListener
		ExtentManager.logStep("Navigating to Login Page entering username and password");
		loginPage.login("Adminn", "admin12345");
		String expectedErrorMessage = "Invalid credentials1";
		Assert.assertTrue(loginPage.isErrorMsgDisplayed(), "Error message should be displayed");
		Assert.assertTrue(loginPage.checkErrorMsg(expectedErrorMessage), "Tast failed: Invalid error message");
		ExtentManager.logStep("Validation successful");
		ExtentManager.logStep("Logged out successfully");
	}
}
