package com.OrangeHRM.test;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.OrangeHRM.actiondriver.ActionDriver;
import com.OrangeHRM.base.BaseClass;
import com.OrangeHRM.pages.HomePage;
import com.OrangeHRM.pages.LoginPage;
import com.OrangeHRM.utilities.DataProviders;

public class HomePageTest extends BaseClass {
private LoginPage loginPage;
private HomePage  homePage;
private WebDriver driver;
private ActionDriver actionDriver;
	
  @BeforeMethod
		    public void setupPages() 
		    {
		    	loginPage=new LoginPage(getDriver());
		    	homePage=new HomePage(getDriver());
	}
		  
 @Test(dataProvider="validLoginData", dataProviderClass=DataProviders.class)
	  
public void verifyDashboardIsDisplayed(String username, String password) {
	loginPage.login(username, password);
	 WebDriverWait wait= new WebDriverWait(getDriver(), Duration.ofSeconds(10));
     wait.until(ExpectedConditions.urlContains("/dashboard/index"));
	 Assert.assertTrue(homePage.isDashboardLoaded(),"dashboard is not loaded");

	 homePage.logout();
			}
		  
		  
}
