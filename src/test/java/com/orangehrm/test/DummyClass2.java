package com.orangehrm.test;



import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;

public class DummyClass2 extends BaseClass {
	
	@Test
	public void dummyTest() {
//		ExtentManager.startTest("Dummy Test 2"); -- Moved to TestListener
		String title = getDriver().getTitle();
		ExtentManager.logStep("Verifiying title");
		assert title.equals("OrangeHRM") : "Test Failed - Title is Not Matching " ;
		
		System.out.print("Test Passed - Title is Matching ");
		ExtentManager.logStep("Validation Successful");
	}

}