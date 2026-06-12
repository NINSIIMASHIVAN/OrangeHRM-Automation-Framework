package com.OrangeHRM.test;

import org.openqa.selenium.WebDriver;
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
	  
public void verifyOrangeHRMLogo(String username, String password) {
	loginPage.login(username, password);
	 Assert.assertTrue(homePage.OrangeHRMLogo(),"logo is not visible ");

	 homePage.logout();
			}
		  
		  
}
