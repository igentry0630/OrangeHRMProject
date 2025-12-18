package com.orangehrm.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BaseClass {

	protected static Properties prop;
	protected static WebDriver driver;

	@BeforeSuite
	public void loadConfig() throws IOException {
		// Load the configuration file
		prop = new Properties();
		FileInputStream fis = new FileInputStream("src\\main\\resources\\config.properties");
		prop.load(fis);
	}

	
	@BeforeMethod
	public void setup() throws IOException {
		System.out.println("Setting up WebDriver for:"+this.getClass().getSimpleName());
		launchBrowser();
		configureBrowser();
		staticWait(2);
	
	}
/*Initialize WebDriver here based on browser defined in
 *  config.properties file */

private void launchBrowser() {
		String browser = prop.getProperty("browser");

		if (browser.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
			// Initialize ChromeDriver
			// System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
			// driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
			// Initialize FirefoxDriver
			// System.setProperty("webdriver.gecko.driver", "path/to/geckodriver");
			// driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("edge")) {
			// Initialize EdgeDriver
			// System.setProperty("webdriver.edge.driver", "path/to/edgedriver");
			driver = new EdgeDriver();
		} else {
			throw new IllegalArgumentException("Browser not supported: " + browser);
		}
	}

	/*Configure browser settings such as implicit wait, maximizing the
	 *  browser, and navigating to a specified URL.*/
	
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
	}
	
	//Driver getter method
	public WebDriver getDriver() {
		return driver;
	}
	
	//Driver setter method
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	
	//Static wait for pause
	public void staticWait(int seconds) {
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds)); // 5 seconds
	}
}
