package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class HomePage {
	
	private ActionDriver actionDriver;
	
	//Define locators using By class
	private By adminTab = By.xpath("//span[text()='Admin']");
	private By userIDButton = By.className("oxd-userdropdown-name");
	private By logoutLink = By.xpath("//a[text()='Logout']");
	private By orangeHRMLogo = By.xpath("//div[@class='oxd-brand-banner']//img");
	
	//Initialize the ActionDriver by passing WebDriver instance
	/*
	 * public HomePage(WebDriver driver) {
		this.actionDriver = new ActionDriver(driver);
	}  */
	
	public HomePage(WebDriver driver) {
		this.actionDriver = BaseClass.getActionDriver();
	}
	
	//Method to verify if Admin tab is visible
	public boolean isAdminTabVisible() {
		return actionDriver.isDisplayed(adminTab);
	}
	
	public boolean verifyOrangeHRMLogo() {
		return actionDriver.isDisplayed(orangeHRMLogo);
	}
	
	//Method to perform logout
	public void logout() {
		actionDriver.click(userIDButton);
		actionDriver.click(logoutLink);
	}
	
}
