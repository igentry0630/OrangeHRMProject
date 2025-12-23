package com.orangehrm.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.orangehrm.actiondriver.ActionDriver;

public class BaseClass {

	protected static Properties prop;
	protected static WebDriver driver;
	private static ActionDriver actionDriver;
	public static final Logger logger = LogManager.getLogger(BaseClass.class);

	@BeforeSuite
	public void loadConfig() throws IOException {
		// Load the configuration file
		prop = new Properties();
		FileInputStream fis = new FileInputStream("src\\main\\resources\\config.properties");
		prop.load(fis);
		logger.info("config.properties file loaded");
	}

	@BeforeMethod
	public void setup() throws IOException {
		System.out.println("Setting up WebDriver for:" + this.getClass().getSimpleName());
		launchBrowser();
		configureBrowser();
		staticWait(2);
		logger.info("Webdriver Initialized and Browser maximized");
		logger.trace("Trace Message");
		logger.error("Error Message");
		logger.debug("Debug Message");
		logger.fatal("Fatal Message");
		logger.warn("Warn Message");

		// Initialize ActionDriver only once
		if (actionDriver == null) {
			actionDriver = new ActionDriver(driver);
			logger.info("ActionDriver instance is created.");
		}
	}
	/*
	 * Initialize WebDriver here based on browser defined in config.properties file
	 */

	private void launchBrowser() {
		String browser = prop.getProperty("browser");

		if (browser.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
			logger.info("ChromeDriver instance is created.");
		} else if (browser.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
			logger.info("FirefoxDriver instance is created.");
		} else if (browser.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
			logger.info("EdgeDriver instance is created.");
		} else {
			throw new IllegalArgumentException("Browser not supported: " + browser);
		}
	}

	/*
	 * Configure browser settings such as implicit wait, maximizing the browser, and
	 * navigating to a specified URL.
	 */

	private void configureBrowser() {
		// Implicit Wait
		int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

		driver.manage().window().maximize();

		// Navigate to the URL
		try {
			driver.get(prop.getProperty("url"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Failed to Navigate to the URL:" + e.getMessage());
		}

	}

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			try {
				driver.quit();
			} catch (Exception e) {
				System.out.println("unable to quite the driver:" + e.getMessage());
			}
		}
		logger.info("Webdriver instance is closed.");
		driver = null;
		actionDriver = null;
	}

	/*
	 * //Driver getter method public WebDriver getDriver() { return driver; }
	 */

	// Getter Method for WebDriver
	public static WebDriver getDriver() {
		if (driver == null) {
			System.out.println("WebDriver instance is not intialized");
			throw new IllegalStateException("WebDriver instance is not initialized");
		}
		return driver;
	}

	// Getter method for prop
	public static Properties getProp() {
		return prop;
	}

	// Getter Method for ActionDriver
	public static ActionDriver getActionDriver() {
		if (actionDriver == null) {
			System.out.println("ActionDriver instance is not intialized");
			throw new IllegalStateException("ActionDriver instance is not initialized");
		}
		return actionDriver;
	}

	// Driver setter method
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	// Static wait for pause
	public void staticWait(int seconds) {
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds)); // 5 seconds
	}
}
