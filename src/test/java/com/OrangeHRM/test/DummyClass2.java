package com.OrangeHRM.test;

import org.testng.annotations.Test;

import com.OrangeHRM.base.BaseClass;

public class DummyClass2 extends BaseClass {

	@Test
	public void dummyTest() 
	{
		String tittle = getDriver().getTitle();
	assert tittle.equals("OrangeHRM"):"Test Failed-tittle is not matching";
	
	System.out.println("Test Passed-Tittle is matching");
	}
}
