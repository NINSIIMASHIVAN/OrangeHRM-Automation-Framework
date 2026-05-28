package com.OrangeHRM.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.OrangeHRM.actiondriver.ActionDriver;

public class HomePage {
WebDriver driver;
ActionDriver actionDriver;
	public HomePage(WebDriver driver) {
		this.driver=driver;
		this.actionDriver=new ActionDriver(driver);
	}

//define locators using By class
	private By adminTab=By.xpath("//span[text()='Admin']");
	private By userIDButton=By.cssSelector(".oxd-userdropdown-icon");
	private By OrangeHRMlogo=By.cssSelector("img[alt='client brand banner']");
	private By LogoutButton=By.xpath("//a[text()='Logout']");
	
	//method to verify if Admin Tab is visible 
	public boolean isAdminTabVisible() 
	{
		return actionDriver.isDisplayed(adminTab);
	}
	
	
	
	// method to verify if Orange HRM logo is displayed
	public boolean OrangeHRMLogo() 
	{
		return actionDriver.isDisplayed(OrangeHRMlogo);
	} 
	
	//method to perform logout operation
	public void logout() 
	{
		actionDriver.click(userIDButton);
		actionDriver.click(LogoutButton);
	}
	
}
