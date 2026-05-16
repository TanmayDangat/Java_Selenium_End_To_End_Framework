package com.orangehrm.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.utilities.ExtentManager;
import com.orangehrm.utilities.LoggerManager;

public class BaseClass {

	protected static Properties properties;
//	protected static WebDriver driver;
//	private static ActionDriver actionDriver;
	
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private static ThreadLocal<ActionDriver> actionDriver = new ThreadLocal<>();
	
	public static final Logger logger = LoggerManager.getLogger(BaseClass.class);

	@BeforeSuite
	public void loadConfig() throws IOException {
		// Load the configuration file
		properties = new Properties();
		FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/config.properties");
		properties.load(fileInputStream);
		logger.info("config.properties file loaded");
		
		//Start the Extent Report
		//ExtentManager.getReporter();         ---This has been implemented in TestListener
	}

	@BeforeMethod
	public synchronized void setup() throws IOException {
		logger.info("Setting up webdriver for :" + this.getClass().getSimpleName());
		launchBrowser();
		configureBrowser();
		staticWait(2);
		logger.info("WebDriver initialized and browser maximized");
		logger.trace("This is Trace message");
		logger.error("This is Error message");
		logger.debug("This is Debug message");
		logger.fatal("This is Fatal message");
		logger.warn("This is Warn message");
		
//		//Initialize the actionDriver only once
//		if(actionDriver == null) {
//			actionDriver = new ActionDriver(driver);
//			logger.info("ActionDriver instance is created" + Thread.currentThread().getId());
//		}
		
		//Initialize ActionDriver for the current thread
		actionDriver.set(new ActionDriver(getDriver()));
		logger.info("ActionDriver initialized for thread : " + Thread.currentThread().getId());
	}

	private synchronized void launchBrowser() {
		// Initialize WebDriver based on browser defined in config.properties file
		String browser = properties.getProperty("browser");
		if (browser.equalsIgnoreCase("chrome")) {
			
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless"); // Run Chrome in headless mode
			options.addArguments("--disable-gpu"); // Disable GPU for headless mode
			//options.addArguments("--window-size=1920,1080"); // Set window size
			options.addArguments("--disable-notifications"); // Disable browser notifications
			options.addArguments("--no-sandbox"); // Required for some CI environments like Jenkins
			options.addArguments("--disable-dev-shm-usage");// Overcome limited resource problems in Docker and CI environments
			
			//driver = new ChromeDriver();
			driver.set(new ChromeDriver());
			ExtentManager.registerDriver(getDriver());
			logger.info("ChromeDriver instance is created");
		} else if (browser.equalsIgnoreCase("firefox")) {
			
			FirefoxOptions options = new FirefoxOptions();
			options.addArguments("--headless"); // Run Firefox in headless mode
			options.addArguments("--disable-gpu"); // Disable GPU rendering (useful for headless mode)
			options.addArguments("--width=1920"); // Set browser width
			options.addArguments("--height=1080"); // Set browser height
			options.addArguments("--disable-notifications"); // Disable browser notifications
			options.addArguments("--no-sandbox"); // Needed for CI/CD environments
			options.addArguments("--disable-dev-shm-usage"); // Prevent crashes in low-resource environments
			//driver = new FirefoxDriver();
			driver.set(new FirefoxDriver());
			ExtentManager.registerDriver(getDriver());
			logger.info("FireFox instance is created");
		} else if (browser.equalsIgnoreCase("edge")) {
			
			EdgeOptions options = new EdgeOptions();
			options.addArguments("--headless"); // Run Edge in headless mode
			options.addArguments("--disable-gpu"); // Disable GPU acceleration
			options.addArguments("--window-size=1920,1080"); // Set window size
			options.addArguments("--disable-notifications"); // Disable pop-up notifications
			options.addArguments("--no-sandbox"); // Needed for CI/CD
			options.addArguments("--disable-dev-shm-usage"); // Prevent resource-limited crashes
			//driver = new EdgeDriver();
			driver.set(new EdgeDriver());
			ExtentManager.registerDriver(getDriver());
			logger.info("EdgeDriver instance is created");
		} else {
			throw new IllegalArgumentException("Browser not supported : " + browser);
		}
	}

	// Configure browser settings such as wait, maximize, get URL
	private void configureBrowser() {
		// implicit wait
		int implicitWait = Integer.parseInt(properties.getProperty("implicitWait"));
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

		// maximize the browser
		getDriver().manage().window().maximize();

		// Navigate to URL
		try {
			getDriver().get(properties.getProperty("url"));
		} catch (Exception e) {
			logger.error("Failed to navigate to URL " + e.getMessage());
		}
	}

	@AfterMethod
	// Close the browser
	public synchronized void tearDown() {
		if (driver != null) {
			try {
				getDriver().quit();
			} catch (Exception e) {
				logger.error("Failed to quit the browser " + e.getMessage());
			}
		}
		logger.info("WebDriver instance is closed");
		driver.remove();
		actionDriver.remove();
		//driver = null;
		//actionDriver = null;
		//ExtentManager.endTest();            ---This has been implemented in TestListener
	}
	
	//Getter method for properties
	public static Properties getProperties() {
		return properties;
	}
	
	
	
//	//Driver getter methods
//	public WebDriver getDriver() {
//		return driver;
//	}
//	
	//Driver setter method
	public void setDriver(ThreadLocal<WebDriver> driver){
		this.driver = driver;
	}
	
	//Getter method for WebDriver
	public static WebDriver getDriver() {
		if(driver.get()==null) {
			logger.info("WebDriver is not initialised");
			throw new IllegalStateException("WebDriver is not initialised");
		}
		return driver.get();	
	}
	
	//Getter method for ActionDriver
		public static ActionDriver getActionDriver() {
			if(actionDriver.get()==null) {
				logger.info("ActionDriver is not initialised");
				throw new IllegalStateException("ActionDriver is not initialised");
			}
			return actionDriver.get();	
		}
	
	public void staticWait(int seconds) {
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
	}

}
