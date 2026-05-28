package com.OrangeHRM.test;

import org.testng.annotations.Test;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.OrangeHRM.base.BaseClass;
import com.OrangeHRM.pages.HomePage;
import com.OrangeHRM.pages.LoginPage;

public class LoginPageTest extends BaseClass{

	private LoginPage loginPage;
    private HomePage homePage;
   
    
    @BeforeMethod
    public void setupPages() 
    {
    	loginPage=new LoginPage(getDriver());
    	homePage=new HomePage(getDriver());
    }
    
    @Test
    public void verifyValidLoginTest() 
    {
    	loginPage.login(prop.getProperty("username"),prop.getProperty("password"));
    	System.out.println(getDriver().getCurrentUrl());
    	Assert.assertTrue(homePage.isAdminTabVisible(),"Admin tab should be visible after a successful login");
        homePage.logout();
        staticWait(2);
    }
    
    @Test
    public void invalidLoginTest() 
    {
    	loginPage.login("admin", "admi126");
    	String expectedErrorMessage= "Invalid credentials";
    	Assert.assertTrue(loginPage.verifyErrorMessage(expectedErrorMessage),"Test failed");
    	System.out.println();
    }
    
    
    
}
