package com.orangehrm.test;



import org.testng.SkipException;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;

public class DummyClass extends BaseClass {
	
	@Test
	public void dummyTest() {
		ExtentManager.startTest("Dummy Test");
		String title = getDriver().getTitle();
		ExtentManager.logStep("Verifiying title");
		assert title.equals("OrangeHRM") : "Test Failed - Title is Not Matching " ;
		
		System.out.println("Test Passed - Title is Matching ");
		ExtentManager.logSkip("This case is skipped intentionally");
		throw new SkipException("Skipping this test intentionally part of Testing.");
	}

}