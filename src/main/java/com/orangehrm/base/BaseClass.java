package com.orangehrm.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseClass {

	protected Properties prop;
	protected WebDriver driver;

	@BeforeMethod
	public void setup() throws IOException {
		// Load the configuration file
		prop = new Properties();
		FileInputStream fis = new FileInputStream("src\\main\\resources\\config.properties");
		prop.load(fis);

		// Initialize WebDriver here based on browser defined in config.properties file
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

		// Implicit Wait
		int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

		driver.manage().window().maximize();

		// Navigate to the URL
		driver.get(prop.getProperty("url"));
	}
	
	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
