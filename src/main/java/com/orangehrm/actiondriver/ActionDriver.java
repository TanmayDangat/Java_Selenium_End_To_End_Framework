package com.orangehrm.actiondriver;

import java.time.Duration;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;


public class ActionDriver {
	private WebDriver driver;
	private WebDriverWait wait;
	public static final Logger logger = BaseClass.logger;
	
	public ActionDriver(WebDriver driver){
		this.driver = driver;
		int explicitWait = Integer.parseInt(BaseClass.getProperties().getProperty("explicitWait"));
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
		logger.info("WebDriver instance is created");
	}
	
	//Method to click an element
	public void click(By by) {
		String elementDescription = getElementDescription(by);
		try {
			applyBorder(by, "green");
			waitForElementsToBeClickable(by);
			driver.findElement(by).click();
			ExtentManager.logStep("Clicked an element :" + elementDescription);
			logger.info("Clicked an element-->" + elementDescription);
		} catch (Exception e) {
			applyBorder(by, "red");
			ExtentManager.logFailure(BaseClass.getDriver(), "Unable to click element. ", elementDescription + "Unable to click. ");
			logger.error("Unable to click element. " + e.getMessage());
		}
	}
	
	//Method to enter text into input field
	public void enterText(By by, String value) {
		try {
			waitForElementToBeVisible(by);
			applyBorder(by, "green");
			WebElement inputField = driver.findElement(by);
			inputField.clear();
			inputField.sendKeys(value);
			logger.info("Entered text on : " + getElementDescription(by) + "-->" + value);
		} catch (Exception e) {
			applyBorder(by, "red");
			logger.error("Unable to enter text. " + e.getMessage());
		}
	}
	
	//Method to get text from input field
	public String getText(By by) {
		try {
			waitForElementToBeVisible(by);
			applyBorder(by, "green");
			return driver.findElement(by).getText();
		} catch (Exception e) {
			applyBorder(by, "red");
			logger.error("Unable to get text. " + e.getMessage());
			return "";
		}
	}
	
	//Method to compare two text
	public boolean compareText(By by, String expectedText) {
		try {
			waitForElementToBeVisible(by);
			String actualText = driver.findElement(by).getText();
			if(expectedText.equals(actualText)) {
				applyBorder(by, "green");
				logger.info("Text are matching. " + expectedText + " equals" + actualText);
				ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Compare text", "Text Verified Successfully" + actualText + " equals" + expectedText);
				return true;
			}else {
				applyBorder(by, "red");
				logger.error("Text are not matching. " + expectedText + "not equals" + actualText);
				ExtentManager.logFailure(BaseClass.getDriver(), "Text Comparison Failed", "Text Comparison failed: " + actualText + " not equals " + expectedText);
				return false;
			}
		} catch (Exception e) {
			applyBorder(by, "red");
			logger.error("Unable to compare two text. " + e.getMessage());
		}
		return false;
	}
	
	//Method to check if element is displayed
	public boolean isDisplayed(By by) {
		try {
			waitForElementToBeVisible(by);
			applyBorder(by, "green");
			logger.info("Element is displayed : " + getElementDescription(by));
			ExtentManager.logStep("Element is displayed : " + getElementDescription(by));
			ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Element is displayed : ", "Element is displayed : " + getElementDescription(by));
			return driver.findElement(by).isDisplayed();	
		} catch (Exception e) {
			applyBorder(by, "red");
			logger.error("Element is not displayed. " + e.getMessage());
			ExtentManager.logFailure(BaseClass.getDriver(),"Element is not displayed : ", "Element is not displayed. " + getElementDescription(by));
			return false;
		}
	}
	
	//Method for page load
	public void waitForPageLoad(int timeOutInSec) {
		try {
	        new WebDriverWait(driver, Duration.ofSeconds(timeOutInSec))
	            .until(driver -> "complete".equals(
	                ((JavascriptExecutor) driver)
	                    .executeScript("return document.readyState")
	            ));
	    } catch (TimeoutException e) {
	    	logger.error("Page did not load within " + timeOutInSec + " seconds");
	    }
	}
	
	//Method for scroll to an element
	public void scrollToElement(By by) {
		try {
			applyBorder(by, "green");
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(by);
			js.executeScript("argument[0].scrollIntoView(true);", element);
		} catch (Exception e) {
			applyBorder(by, "red");
			logger.error("Unable to locate element. " + e.getMessage());
		}
	}

	//Wait for elements to be clickable
	private void waitForElementsToBeClickable(By by) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
			logger.error("Element is not clickable. " + e.getMessage());
		}
	}
	
	//Wait for element to be visible
	private void waitForElementToBeVisible(By by) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			logger.error("Element is not visible. " + e.getMessage());
		}
	}
	
	//Method to get description of an element using By locator
	public String getElementDescription(By locator) {
		//Check for NULL driver or locator to avoid Nullpointer exception
		if(driver==null) 
			return "driver is null";
		if(locator==null) 
			return "locator is null";
		
		try {
			//Find the element using the locator
			WebElement element = driver.findElement(locator);
			
			//Get element Attributes
			String name = element.getDomAttribute("name");
			String id = element.getDomAttribute("id");
			String text = element.getText();
			String className = element.getDomAttribute("class");
			String placeHolder = element.getDomAttribute("placeholder");
			
			//Return the description based on element attributes
			if(isNotEmpty(name)) {
				return "Element with name : " + name;
			}else if(isNotEmpty(id)) {
				return "Element with id : " + id;
			}else if(isNotEmpty(text)) {
				return "Element with text : " + truncate(text, 50);
			}else if(isNotEmpty(className)) {
				return "Element with class name : " + className;
			}else if(isNotEmpty(placeHolder)) {
				return "Element with placeholder : " + placeHolder;
			}
		} catch (Exception e) {
			logger.error("Unable to describe the element" + e.getMessage());
		}
		return "Unable to describe the element";
		
	}
	
	//Utility method to check the string is not NULL or empty
	private boolean isNotEmpty(String value) {
		return value!=null && !value.isEmpty();
	}
	
	
	//Utility method to truncate long String
	private String truncate(String value, int maxLength) {
		if(value==null || value.length()<=maxLength) {
			return value;
		}
		return value.substring(0, maxLength) + "...";
	}
	
	//Utility Method to Border an element
	public void applyBorder(By by, String color) {
		try {
			//Locate the element
			WebElement element = driver.findElement(by);
			//Apply the border
			String script = "arguments[0].style.border='3px solid "+color+"'";
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(script, element);
			logger.info("Applied the border with color" + color + "to element " + getElementDescription(by));
		} catch (Exception e) {
			logger.warn("Failed to apply the border to an element: " + getElementDescription(by), e);
		}
	}
	
	
	//This is demo comment for checking Git
	
	//Another comment to check username and email
}
