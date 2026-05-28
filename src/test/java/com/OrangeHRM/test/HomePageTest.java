package com.OrangeHRM.test;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.OrangeHRM.actiondriver.ActionDriver;
import com.OrangeHRM.base.BaseClass;
import com.OrangeHRM.pages.HomePage;
import com.OrangeHRM.pages.LoginPage;

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
		  
	@Test	  
public void verifyOrangeHRMLogo() {
	loginPage.login("Admin", "admin123");
	 Assert.assertTrue(homePage.OrangeHRMLogo(),"logo is not visible ");


			}
		  
		  
}
