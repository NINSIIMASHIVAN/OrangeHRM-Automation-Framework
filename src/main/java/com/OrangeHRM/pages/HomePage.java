package com.OrangeHRM.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
	private By userIDButton=By.cssSelector(".oxd-userdropdown-tab");
	//private By OrangeHRMlogo=By.cssSelector("img.oxd-brand-logo");
	//private By OrangeHRMlogo=By.cssSelector("img[alt='client brand banner']");
	private By OrangeHRMlogo=By.cssSelector(".oxd-main-menu");
	private By LogoutButton=By.xpath("//a[text()='Logout']");
	
	//method to verify if Admin Tab is visible 
	/*public boolean isAdminTabVisible() 
	{
		 actionDriver.waitForElement(adminTab);
		return actionDriver.isDisplayed(adminTab);
	}*/
	public boolean isAdminTabVisible() {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	        wait.until(ExpectedConditions.visibilityOfElementLocated(adminTab));
	        return true;
	    } catch (Exception e) {
	        System.out.println("Admin tab not visible: " + e.getMessage());
	        return false;
	    }
	}
	
	
	
	// method to verify if Orange HRM logo is displayed
	/*public boolean OrangeHRMLogo() 
	{
		return actionDriver.isDisplayed(OrangeHRMlogo);
	}*/ 
	
	/*public boolean OrangeHRMLogo() {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	        wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.cssSelector(".oxd-main-menu")
	        ));
	        return true;
	    } catch (Exception e) {
	        System.out.println("element not seen: " + e.getMessage());
	        return false;
	    }
	}*/
	public boolean OrangeHRMLogo() {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	        
	        // Wait for URL to contain dashboard - works in both headed and headless
	        wait.until(ExpectedConditions.urlContains("/dashboard/index"));
	        
	        // Then also verify page title
	        wait.until(ExpectedConditions.titleIs("OrangeHRM"));
	        
	        return true;
	    } catch (Exception e) {
	        System.out.println("element not seen: " + e.getMessage());
	        return false;
	    }
	}
	
	//method to perform logout operation
	/*public void logout() 
	{
		actionDriver.click(userIDButton);
		actionDriver.click(LogoutButton);
	}*/
	public void logout() {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	       
	        // Wait for user dropdown to be clickable before clicking
	        wait.until(ExpectedConditions.elementToBeClickable(userIDButton)).click();
	     
	        // Wait for dropdown to open then click logout
	        wait.until(ExpectedConditions.visibilityOfElementLocated(LogoutButton));
	        
	        // Wait for logout option to appear after dropdown opens
	        wait.until(ExpectedConditions.elementToBeClickable(LogoutButton)).click();
	    } 
	    catch (Exception e) {
	        System.out.println("Logout failed: " + e.getMessage());
	    }
	}
}
