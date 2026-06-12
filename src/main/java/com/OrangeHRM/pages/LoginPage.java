package com.OrangeHRM.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.OrangeHRM.actiondriver.ActionDriver;
import com.OrangeHRM.base.BaseClass;

public class LoginPage   {

	
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		this.actionDriver=new ActionDriver(driver);
	}
private ActionDriver actionDriver;
private WebDriver driver;
//define locators using By class

private By userNamefield=By.name("username");
private By passwordfield=By.cssSelector("input[placeholder='Password']");
private By loginbtnfield=By.cssSelector("button[type='submit']");
private By errormessage=By.xpath("//div[@class='oxd-alert-content oxd-alert-content--error']");

//method to perform login
public void login(String username,String password) 
{
actionDriver.enterText(userNamefield, username);
actionDriver.enterText(passwordfield, password);
actionDriver.click(loginbtnfield);

actionDriver.waitForPageToLoad();

//create a wait specifically for the dashboard.
//By adminTab =By.xpath("//span[text()='Admin']");
//actionDriver.waitForElement(By.xpath("//span[text()='Admin']"));
System.out.println(driver.getCurrentUrl());
}

//method to check if error message is displayed
public boolean isErrorMessageDisplayed() 
{
	return actionDriver.isDisplayed(errormessage);
}

//method to get text from error message
public String getErrorMessageText() 
{
    String text = actionDriver.getText(errormessage);
    System.out.println("Error message from page: '" + text + "'");
    return text;
}

public boolean isErrorMessageDisplayed1() 
{
    return actionDriver.isDisplayed(errormessage);
}
//method to verify if error is correct or not
public boolean verifyErrorMessage(String expectedError) 
{
	return actionDriver.compareText(errormessage, expectedError);
}

}
