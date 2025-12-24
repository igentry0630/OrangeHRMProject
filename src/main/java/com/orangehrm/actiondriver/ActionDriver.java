package com.orangehrm.actiondriver;

import java.time.Duration;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.orangehrm.base.BaseClass;

public class ActionDriver {

	private WebDriver driver;
	private WebDriverWait wait;
	public static final Logger logger = BaseClass.logger;

	public ActionDriver(WebDriver driver) {
		this.driver = driver;
		int explicit = Integer.parseInt(BaseClass.getProp().getProperty("explicitWait"));
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		logger.info("WebDriver instance is created.");
	}

	// Method to click an element
	public void click(By by) {
		String elementDescription = getElementDescription(by);
		try {
			waitForElementToBeClickable(by);
			driver.findElement(by).click();
			logger.info("Clicked on element--> " + elementDescription);
		} catch (Exception e) {
			logger.error("Unable to click on the element");
		}
	}

	// Method to type text into an input field -- Avoid code duplication - fix the
	// multiple calling methods
	public void enterText(By by, String text) {
		try {
//			waitForElementToBeVisible(by);
//			driver.findElement(by).clear();
			WebElement element = driver.findElement(by);
			element.clear();
			element.sendKeys(text);
			logger.info("Entered text on:" + getElementDescription(by)+"-->"+text);
		} catch (Exception e) {
			logger.error("Unable to enter the text into the input field: " + e.getMessage());
		}
	}

	// Method to get text from an input field
	public String getText(By by) {
		try {
			waitForElementToBeVisible(by);
			return driver.findElement(by).getText();
		} catch (Exception e) {
			logger.error("Unable to get text from the input field: " + e.getMessage());
			return "";
		}
	}

	// Method to compare Two strings -- changed return type
	public boolean compareText(By by, String expectedText) {
		try {
			waitForElementToBeVisible(by);
			String actualText = driver.findElement(by).getText();
			if (actualText.equals(expectedText)) {
				logger.info("Text is Matching:" + actualText + " equals " + expectedText);
				return true;
			} else {
				System.out.println("TText is Not Matching" + actualText + " does not equal " + expectedText);
				return false;
			}
		} catch (Exception e) {
			logger.error("Unable to compare the texts: " + e.getMessage());
		}
		return false;
	}

	// Method to check if element is displayed
//	public boolean isElementDisplayed(By by) {
//		try {
//			waitForElementToBeVisible(by);
//			boolean isDisplayed = driver.findElement(by).isDisplayed();
//			if(isDisplayed) {
//				System.out.println("Element is visible");
//				return isDisplayed;
//			} else {
//				return isDisplayed;
//			}
//		} catch (Exception e) {
//			System.out.println("Element is not displayed: "+e.getMessage());
//			return false;
//		}
//	}

	// Simplified the method and removed redundant code
	public boolean isDisplayed(By by) {
		try {
			waitForElementToBeVisible(by);
			logger.info("Element is displayed " + getElementDescription(by));
			return driver.findElement(by).isDisplayed();
		} catch (Exception e) {
			logger.error("Element is not displayed: " + e.getMessage());
			return false;
		}
	}

	// Wait for the page to load
	public void waitForPageToLoad(int timeInSeconds) {
		try {
			wait.withTimeout(Duration.ofSeconds(timeInSeconds)).until(WebDriver -> ((JavascriptExecutor) WebDriver)
					.executeScript("return document.readyState").equals("complete"));
			logger.info("Page loaded successfully");
		} catch (Exception e) {
			logger.error("Page did not load within " + timeInSeconds + " seconds. Exception: " + e.getMessage());
		}
	}

	// Scroll to an element
	public void scrollToElement(By by) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(by);
			js.executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception e) {
			logger.error("Unable to locate the element to scroll to: " + e.getMessage());
		}
	}

	// Wait for element to be clickable
	private void waitForElementToBeClickable(By by) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
			logger.error("element is not clickable: " + e.getMessage());
		}
	}

	// Wait for element to be visible
	private void waitForElementToBeVisible(By by) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			logger.error("element is not visible: " + e.getMessage());
		}
	}

	// Method to get the description of an element using By locator

	public String getElementDescription(By locator) {
		// Check for null driver or locator to avoid NullPointerException
		if (driver == null)
			return "driver is null";
		if (locator == null)
			return "Locator is null";

		try {
			// find element using the locator
			WebElement element = driver.findElement(locator);

			// Get Element Attributes
			String name = element.getDomAttribute("name");
			String id = element.getAttribute("id");
			String className = element.getAttribute("class");
			String text = element.getText();
			String placeHolder = element.getAttribute("placeholder");

			if (isNotEmpty(name)) {
				return "Element with name: " + name;
			} else if (isNotEmpty(id)) {
				return "Element with id: " + id;
			} else if (isNotEmpty(placeHolder)) {
				return "Element with placeholder: " + placeHolder;
			} else if (isNotEmpty(text)) {
				return "Element with text: " + truncate(text, 50);
			} else if (isNotEmpty(className)) {
				return "Element with class: " + className;
			}
		} catch (Exception e) {
			logger.error("Unable to locate the element: " + e.getMessage());
		}
		return "Element description not available";
	}

	

	// Utility method to check if a String is not Null or empty
	private boolean isNotEmpty(String value) {
		return value != null && !value.isEmpty();
	}

	// Utility Method to truncate long String
	private String truncate(String value, int maxLength) {
		if (value == null || value.length() <= maxLength) {
			return value;
		}
		return value.substring(0, maxLength) + "...";
	}
}
