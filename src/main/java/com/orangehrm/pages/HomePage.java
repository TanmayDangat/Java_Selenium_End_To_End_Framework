package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class HomePage {
	private ActionDriver actionDriver;
	
//	//Initialize ActionDriver object using WebDriver instance
//	public HomePage(WebDriver driver) {
//		this.actionDriver = new ActionDriver(driver);
//	}
	
	public HomePage(WebDriver driver) {
		this.actionDriver = BaseClass.getActionDriver();
	}
	
	//Locators
	
	private By adminTab = By.xpath("//span[text()='Admin']");
	private By userIDBtn = By.xpath("//p[@class='oxd-userdropdown-name']");
	private By logoutBtn = By.xpath("//a[text()='Logout']");
	private By orangeHRMLogo = By.xpath("//div[@class='oxd-brand-banner']/img");
	
	//Verify admin page is visible
	public boolean isAdminTabVisible() {
		return actionDriver.isDisplayed(adminTab);
	}
	
	//Verify OrangeHRM Logo
	public boolean isOrangeHRMLogoVisible() {
		return actionDriver.isDisplayed(orangeHRMLogo);
	}
	
	//Method to perform logout operation
	public void logoutOperation() {
		actionDriver.click(userIDBtn);
		actionDriver.click(logoutBtn);
	}
	
	
	
}
