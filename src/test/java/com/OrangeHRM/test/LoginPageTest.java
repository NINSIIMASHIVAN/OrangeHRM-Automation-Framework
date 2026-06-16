package com.OrangeHRM.test;

import org.testng.annotations.Test;
import org.testng.annotations.Test;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
    	
    	getActionDriver().waitForPageToLoad();
        Assert.assertTrue(homePage.OrangeHRMLogo(), "logo is not visible");
    	
    	System.out.println("Current URL = " + getDriver().getCurrentUrl());
    	System.out.println("Page title = " + getDriver().getTitle());
    	System.out.println(getDriver().getCurrentUrl());
    	System.out.println("Page Title = " + getDriver().getTitle());

    	System.out.println("Username = [" + username + "]");
    	System.out.println("Password = [" + password + "]");
    	
    	
    	Assert.assertTrue(homePage.isAdminTabVisible(),"Admin tab should be visible after a successful login");
        homePage.logout();
        System.out.println("Logged out successfully");
        staticWait(2);
    }
    @Test(dataProvider="invalidLoginData", dataProviderClass=DataProviders.class)
    public void verifyInvalidLoginTest(String username, String password) {
        loginPage.login(username, password);
        
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(".oxd-alert-content-text")  
        ));
        
        Assert.assertTrue(loginPage.isErrorMessageDisplayed());
        String expectedErrorMessage = "Invalid credentials";
        Assert.assertTrue(loginPage.verifyErrorMessage(expectedErrorMessage), "Test failed");
        System.out.println("Invalid login test passed");
    }
 
}
