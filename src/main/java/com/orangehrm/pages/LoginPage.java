package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class LoginPage {
	
	private ActionDriver actionDriver;
	
//	//Initialize ActionDriver object by passing WebDriver instance
//	public LoginPage(WebDriver driver) {
//		this.actionDriver = new ActionDriver(driver);
//	}
	
	public LoginPage(WebDriver driver) {
		this.actionDriver = BaseClass.getActionDriver();
	}
	
	
	
	//Define locators
	
	private By usernameInput = By.xpath("//input[@name='username']");
	private By passwordInput = By.xpath("//input[@name='password']");
	private By loginBtn = By.xpath("//Button[@type='submit']");
	private By errorMsg = By.xpath("//p[text()='Invalid credentials']");
	
	//Login method
	public void login(String username, String password) {
		actionDriver.enterText(usernameInput, username);
		actionDriver.enterText(passwordInput, password);
		actionDriver.click(loginBtn);
	}
	
	//check error message is there or not
	public boolean isErrorMsgDisplayed() {
		return actionDriver.isDisplayed(errorMsg);
	}
	
	//Display error message
	public String displayErrorMsg() {
		return actionDriver.getText(errorMsg);
	}
	
	//Verify if error message is displayed correct or not
	public boolean checkErrorMsg(String expectedText) {
		return actionDriver.compareText(errorMsg, expectedText);
	}
}
