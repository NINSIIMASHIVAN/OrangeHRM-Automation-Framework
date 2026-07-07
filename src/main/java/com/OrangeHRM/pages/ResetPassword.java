package com.OrangeHRM.pages;

import org.openqa.selenium.WebDriver;

import com.OrangeHRM.actiondriver.ActionDriver;

public class ResetPassword {
	
	WebDriver driver;
	ActionDriver actionDriver;

	public ResetPassword() {
		this.driver=driver;
		this.actionDriver=new ActionDriver(driver);
		
	}

	/*locators [flow:loginWith wrong password-> confirm invalid credentials error message->
	click on forgot your password,enterUserName, click ResetName                   */
}
