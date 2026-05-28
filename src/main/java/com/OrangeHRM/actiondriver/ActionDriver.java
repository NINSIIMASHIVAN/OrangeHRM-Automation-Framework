package com.OrangeHRM.actiondriver;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.OrangeHRM.base.BaseClass;

public class ActionDriver {

	private WebDriver driver;
	private WebDriverWait wait;//we put this because we are going to use explicit wait.
	
	//initialize the above 2 declared variables while forming a constructor for this class
	public ActionDriver(WebDriver driver) 
	{this.driver=driver;
    int explicitWait=Integer.parseInt(BaseClass.getProp().getProperty("explicitWait"));
	
	this.wait=new WebDriverWait(driver,Duration.ofSeconds(explicitWait));
	}
	
	//method to click an element
	public void click(By by)
	{
		try {
			waitForElementToBeClickable(by);
			driver.findElement(by).click();
		} catch (Exception e) {
			System.out.println("unable to click element:" +e.getMessage());
		}
	}	
	
	//method to enter text into field
	
	
	public void enterText(By by, String value) 
	{
		try {
			waitForElementToBeVisible(by);
			WebElement element=driver.findElement(by);
			element.clear();
			element.sendKeys(value);
		} catch (Exception e) {
			System.out.println("Unable to enter the value in input field;" +e.getMessage());
		}
	}
	
	//method to get text from an input field
	public String getText(By by) 
	{
		try {
			waitForElementToBeVisible(by); 
			return driver.findElement(by).getText();
		} catch (Exception e) {
			System.out.println("Unable to get Text;" +e.getMessage());
		}
		return " ";
	}
	
	//method to wait for page to load 
	public void waitForPageToLoad() {
	    try {
	        JavascriptExecutor js = (JavascriptExecutor) driver;

	        for (int i = 0; i < 20; i++) {
	            String state = js.executeScript("return document.readyState").toString();

	            if (state.equals("complete")) {
	                break;
	            }

	            Thread.sleep(1000);
	        }

	    } catch (Exception e) 
	    {
	    System.out.println("page unable to load:" +e.getMessage());	
	    }
	    }
	
	//method to check if an element is displayed
		public boolean isDisplayed(By by) 
		{
			try {
				waitForElementToBeVisible(by); 
				return driver.findElement(by).isDisplayed();
				}	
			 catch (Exception e) {
				System.out.println("element not displayed:" +e.getMessage());
			}
			return false;
		}
		
		
		//method to scroll to element
		public void scrollToElement(WebElement element) 
		{
			try {
				JavascriptExecutor js=(JavascriptExecutor) driver;
				js.executeScript("arguments[0].scrollIntoView(true);", element);
			} catch (Exception e) {
				// TODO Auto-generated catch block
			System.out.println("unable to locate Element:" +e.getMessage());
			}
		}
		
	//method to compare Two Text
	public boolean compareText(By by,String expectedText)
	{
		try {
			waitForElementToBeClickable(by);
			String actualText=driver.findElement(by).getText();
			if(expectedText.equals(actualText)) 
			{
				System.out.println("texts are matching"  );
				return true;
			}else {
				System.out.println("texts are not matching"  );
			}
			return false ;
		} catch (Exception e) {
			System.out.println("unable to compare tesxts:" +e.getMessage());
		}
		return false ;
	}	
	//wait for the element to be clickable
	private void waitForElementToBeClickable(By by) { 
	try{
		wait.until(ExpectedConditions.elementToBeClickable(by));
	}catch(Exception e) {
System.out.println("element not clickable;" +e.getMessage());
		
	}}
	
	
	
	//wait for element to be visible
	private void waitForElementToBeVisible(By by) 
	{
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			System.out.println("element not seen:" +e.getMessage());
			
		}
	}

	public void set(ActionDriver actionDriver) {
		
	}
}
