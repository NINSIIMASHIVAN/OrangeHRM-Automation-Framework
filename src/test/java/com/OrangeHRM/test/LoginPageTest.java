package com.OrangeHRM.test;

import org.testng.annotations.Test;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

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
    public void verifyValidLoginTest(String username, String password) throws InterruptedException 
    {
    	 loginPage.login(username, password);
      homePage = new HomePage(getDriver());
      WebDriverWait wait= new WebDriverWait(getDriver(), Duration.ofSeconds(10));
      wait.until(ExpectedConditions.urlContains("/dashboard/index"));
      
        		
        
        // Add explicit wait for dashboard to fully load
       // getActionDriver().waitForPageToLoad();
        //WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        
        // Wait for main menu to load first
       // wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".oxd-main-menu");
        
        
        // Extra delay for headless rendering
      // Thread.sleep(2000);
        
        //Assert.assertTrue(homePage.OrangeHRMLogo(), "logo is not visible");
        
        System.out.println("Current URL = " + getDriver().getCurrentUrl());
        System.out.println("Page title = " + getDriver().getTitle());
        System.out.println("Logging in with user: " + username);

        
       
       Assert.assertTrue(homePage.isAdminTabVisible(), "Admin tab should be visible after a successful login");
        
        homePage.logout();
        System.out.println("Logged out successfully");
     
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
