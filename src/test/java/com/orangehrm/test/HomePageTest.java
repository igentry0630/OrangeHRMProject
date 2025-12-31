package com.orangehrm.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.ExtentManager;

public class HomePageTest extends BaseClass {
	
	private LoginPage loginPage;
	private HomePage homePage;
	
	@BeforeMethod
	public void setupPages() {
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
	}
	
	@Test
	public void verifyOrangeHRMLogoTest() {
		ExtentManager.startTest("Verify OrangeHRM Logo Test");
		ExtentManager.logStep("Logging in with valid credentials");
		loginPage.login("admin", "admin123");
		ExtentManager.logStep("Verifying OrangeHRM Logo is visible on Home Page");
		Assert.assertTrue(homePage.verifyOrangeHRMLogo(), "Logo is not visible");
		ExtentManager.logStep("Validation Successful");
		homePage.logout();
		ExtentManager.logStep("Logged out successfully");
		staticWait(2);
	}
	
	

}
