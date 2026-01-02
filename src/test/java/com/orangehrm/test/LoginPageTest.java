package com.orangehrm.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.ExtentManager;

public class LoginPageTest extends BaseClass {

	private LoginPage loginPage;
	private HomePage homePage;
	
	@BeforeMethod
	public void setupPages() {
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
	}
	
	@Test
	public void verifyValidLoginTest() {
		
//		ExtentManager.startTest("Valid Login Test"); -- Moved to TestListener
		System.out.println("Running testMethod1 on thread: " + Thread.currentThread().getId());
		ExtentManager.logStep("Navigating to Login Page and entering in username and password");
		loginPage.login("admin", "admin123");
		ExtentManager.logStep("Verifying Admin tab is visible after login");
		Assert.assertTrue(homePage.isAdminTabVisible(), "Admin tab is not visible, login might have failed.");		
		ExtentManager.logStep("Validation Successful");
		homePage.logout();
		ExtentManager.logStep("Logged out successfully");
		staticWait(2);
	}
	
	@Test
	public void inValidLoginTest() {
//		ExtentManager.startTest("Invalid Login Test");-- Moved to TestListener
		System.out.println("Running testMethod2 on thread: " + Thread.currentThread().getId());
		ExtentManager.logStep("Navigating to Login Page and entering invalid username and invalid password");
		loginPage.login("invalidUser", "invalidPass");
		String expectedErrorMessage = "Invalid credentials";
		Assert.assertTrue(loginPage.verifyErrorMessage(expectedErrorMessage), "Test Failed: Error message is not displayed for invalid login.");
		ExtentManager.logStep("Validation Successful, Login failed as expected with invalid credentials and displaying error message.");
	}
	
}
