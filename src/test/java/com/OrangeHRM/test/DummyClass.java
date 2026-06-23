package com.OrangeHRM.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.OrangeHRM.base.BaseClass;

public class DummyClass extends BaseClass {

	@Test
	public void dummyTest() 
	{
		String tittle = getDriver().getTitle();
		Assert.assertEquals(tittle, "OrangeHRM",
		        "Title is not matching");
	//printing the status of the test case,//testing jenkins 
		System.out.println("Test Passed-Tittle is matching");
	}
}
