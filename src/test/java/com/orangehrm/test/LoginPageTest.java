package com.orangehrm.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;

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
		loginPage.login("admin", "admin123");
		Assert.assertTrue(homePage.isAdminTabVisible(), "Admin tab is not visible, login might have failed.");
		homePage.logout();
		staticWait(2);
	}
	
	@Test
	public void inValidLoginTest() {
		loginPage.login("invalidUser", "invalidPass");
		String expectedErrorMessage = "Invalid credentials";
		Assert.assertTrue(loginPage.verifyErrorMessage(expectedErrorMessage), "Test Failed: Error message is not displayed for invalid login.");
		String actualError = loginPage.getErrorMessageText();
		Assert.assertEquals(actualError, "Invalid credentials", "Error message text does not match expected text");
	}
	
}
