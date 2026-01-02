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
import com.orangehrm.utilities.ExtentManager;

public class BaseClass {

	protected static Properties prop;
//	protected static WebDriver driver;
//	private static ActionDriver actionDriver;
	
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private static ThreadLocal<ActionDriver> actionDriver = new ThreadLocal<>();
	
	public static final Logger logger = LogManager.getLogger(BaseClass.class);

	@BeforeSuite
	public void loadConfig() throws IOException {
		// Load the configuration file
		prop = new Properties();
		FileInputStream fis = new FileInputStream("src\\main\\resources\\config.properties");
		prop.load(fis);
		logger.info("config.properties file loaded");
		
		//Start the Extent Report
//		ExtentManager.getReporter(); -- This method has been moved to TestListener class
	}

	@BeforeMethod
	public synchronized void setup() throws IOException {
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
	

		/*// Initialize ActionDriver only once
		if (actionDriver == null) {
			actionDriver = new ActionDriver(driver);
			logger.info("ActionDriver instance is created."+Thread.currentThread().getId());
		} 
		
		
	} */
	
	//Initialize ActionDriver for the current Thread		
		actionDriver.set(new ActionDriver(getDriver()));
		logger.info("ActionDriver initialized for thread: "+Thread.currentThread().getId());
	}
	
	/*
	 * Initialize WebDriver here based on browser defined in config.properties file
	 */

	//New changes per ThreadLocal
	private synchronized void launchBrowser() {
		String browser = prop.getProperty("browser");

		if (browser.equalsIgnoreCase("chrome")) {
			//driver = new ChromeDriver();
			driver.set(new ChromeDriver()); //New changes per ThreadLocal
			ExtentManager.registerDriver(getDriver());
			logger.info("ChromeDriver instance is created.");
		} else if (browser.equalsIgnoreCase("firefox")) {
			//driver = new FirefoxDriver();
			driver.set(new FirefoxDriver());
			ExtentManager.registerDriver(getDriver());
			logger.info("FirefoxDriver instance is created.");
		} else if (browser.equalsIgnoreCase("edge")) {
			//driver = new EdgeDriver();
			driver.set(new EdgeDriver());
			ExtentManager.registerDriver(getDriver());
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
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

		getDriver().manage().window().maximize();

		// Navigate to the URL
		try {
			getDriver().get(prop.getProperty("url"));
		} catch (Exception e) {
			System.out.println("Failed to Navigate to the URL:" + e.getMessage());
		}

	}

	@AfterMethod
	public synchronized void tearDown() {
		if (getDriver() != null) {
			try {
				getDriver().quit();
			} catch (Exception e) {
				System.out.println("unable to quite the driver:" + e.getMessage());
			}
		}
		logger.info("Webdriver instance is closed.");
		driver.remove();
		actionDriver.remove();
		//driver = null;
		//actionDriver = null;
//		ExtentManager.endTest(); -- This method has been moved to TestListener class
	}

	/*
	 * //Driver getter method public WebDriver getDriver() { return driver; }
	 */

	// Getter Method for WebDriver
	public static WebDriver getDriver() {
		if (driver.get() == null) {
			System.out.println("WebDriver instance is not intialized");
			throw new IllegalStateException("WebDriver instance is not initialized");
		}
		return driver.get();
	}

	// Getter method for prop
	public static Properties getProp() {
		return prop;
	}

	// Getter Method for ActionDriver
	public static ActionDriver getActionDriver() {
		if (actionDriver.get() == null) {
			System.out.println("ActionDriver instance is not intialized");
			throw new IllegalStateException("ActionDriver instance is not initialized");
		}
		return actionDriver.get();
	}

	// Driver setter method
	public void setDriver(ThreadLocal<WebDriver> driver) {
		this.driver = driver;
	}

	// Static wait for pause
	public void staticWait(int seconds) {
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds)); // 5 seconds
	}
}
