package com.OrangeHRM.test;

import org.testng.annotations.Test;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import com.OrangeHRM.utilities.DataProviders;
import org.testng.annotations.Test;

import com.OrangeHRM.actiondriver.ActionDriver;
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
    
    @Test(dataProvider="validLoginData", dataProviderClass=DataProviders.class)
    public void verifyValidLoginTest(String username, String password) 
    {
    	loginPage.login(username,password);
    	System.out.println(getDriver().getCurrentUrl());
    	System.out.println("Page Title = " + getDriver().getTitle());

    	Assert.assertTrue(homePage.isAdminTabVisible(),"Admin tab should be visible after a successful login");
        homePage.logout();
        System.out.println("Logged out successfully");
        staticWait(2);
    }
    
  /*  @Test(dataProvider="invalidLoginData", dataProviderClass=DataProviders.class)
    public void invalidLoginTest(String username, String password) 
    {
    	loginPage.login(username, password);
    	//wait for error message to appear
    	//getActionDriver().waitForPageToLoad();
    	Assert.assertTrue(loginPage.isErrorMessageDisplayed());
    	String expectedErrorMessage= "Invalid credentials";
    	
    	 boolean isErrorPresent = loginPage.isErrorMessageDisplayed();
    	    System.out.println("Error message displayed: " + isErrorPresent);
    	    
    	    if(isErrorPresent) {
    	        String actualError = loginPage.getErrorMessageText();
    	        System.out.println("Actual error message: " + actualError);
    	    }
    	Assert.assertTrue(loginPage.verifyErrorMessage(expectedErrorMessage),"Test failed");
    	System.out.println("Invalid logintest passed");
    			
}*/

    @Test(dataProvider="invalidLoginData", dataProviderClass=DataProviders.class) 
    public void invalidLoginTest(String username, String password) 
    {
        loginPage.login(username, password);
        getActionDriver().waitForPageToLoad();
        
        // Debug: Print what we actually get
        if(loginPage.isErrorMessageDisplayed()) {
            String actualError = loginPage.getErrorMessageText();
            System.out.println("===== ACTUAL ERROR MESSAGE: '" + actualError + "' =====");
        } else {
            System.out.println("===== NO ERROR MESSAGE DISPLAYED =====");
            System.out.println("===== CURRENT URL: " + getDriver().getCurrentUrl() + " =====");
        }
        
        String expectedErrorMessage = "Invalid credentials";
        Assert.assertTrue(loginPage.verifyErrorMessage(expectedErrorMessage),
            "Test failed - Expected error message not found");
    }  



}
