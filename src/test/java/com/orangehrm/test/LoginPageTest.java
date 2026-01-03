package com.orangehrm.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DataProviders;
import com.orangehrm.utilities.ExtentManager;

public class LoginPageTest extends BaseClass {

	private LoginPage loginPage;
	private HomePage homePage;
	
	@BeforeMethod
	public void setupPages() {
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
	}
	
	@Test(dataProvider="validLoginData", dataProviderClass=DataProviders.class)
	public void verifyValidLoginTest(String username, String password) {
		
//		ExtentManager.startTest("Valid Login Test"); -- Moved to TestListener
		System.out.println("Running testMethod1 on thread: " + Thread.currentThread().getId());
		ExtentManager.logStep("Navigating to Login Page and entering in username and password");
		loginPage.login(username, password);
		ExtentManager.logStep("Verifying Admin tab is visible after login");
		Assert.assertTrue(homePage.isAdminTabVisible(), "Admin tab is not visible, login might have failed.");		
		ExtentManager.logStep("Validation Successful");
		homePage.logout();
		ExtentManager.logStep("Logged out successfully");
		staticWait(2);
	}
	
	@Test(dataProvider="inValidLoginData", dataProviderClass=DataProviders.class)
	public void inValidLoginTest(String username, String password) {
//		ExtentManager.startTest("Invalid Login Test");-- Moved to TestListener
		System.out.println("Running testMethod2 on thread: " + Thread.currentThread().getId());
		ExtentManager.logStep("Navigating to Login Page and entering invalid username and invalid password");
		loginPage.login(username, password);
		String expectedErrorMessage = "Invalid credentials1";
		Assert.assertTrue(loginPage.verifyErrorMessage(expectedErrorMessage), "Test Failed: Error message is not displayed for invalid login.");
		ExtentManager.logStep("Validation Successful, Login failed as expected with invalid credentials and displaying error message.");
	}
	
}
