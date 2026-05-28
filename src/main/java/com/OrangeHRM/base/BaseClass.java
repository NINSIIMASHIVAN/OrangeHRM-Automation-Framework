package com.OrangeHRM.base;


import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.OrangeHRM.actiondriver.ActionDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
  
	protected static Properties prop;
	//protected static WebDriver driver;
	//private static ActionDriver actionDriver;
	
	  // ThreadLocal means each test thread gets its own WebDriver instance
	private static ThreadLocal<WebDriver>driver=new ThreadLocal<>();
	private static ThreadLocal <ActionDriver> actionDriver=new ThreadLocal<>();

	
@BeforeSuite
	public void loadConfig() throws IOException 
	{ //load the configuration file
		 prop=new Properties();
		 FileInputStream fis=new FileInputStream("src/main/resources/config.properties");
		prop.load(fis);
	}
	
	@BeforeMethod
public void setup()
{
	System.out.println("Setting up WebDriver for:"+this.getClass().getSimpleName());
	launchBrowser();
	configureBrowser();
	staticWait(2);

	//initialize the actionDriver  per thread,if not already set 
if(actionDriver.get()==null) {
actionDriver.set(new ActionDriver(getDriver()));
System.out.println("ActionDriver instance is created");
	
}}
		
		/*initialise the webdriver based on browser, defined in config.properties file and per Thread*/
		
		/*private void launchBrowser()
		{
		String browser=prop.getProperty("browser");
	if(browser.equalsIgnoreCase("chrome"))
	{
		driver.set(new ChromeDriver());
	}
	else if(browser.equalsIgnoreCase("firefox"))
	{                                                                                                                                                                                                                                         {
		driver.set(new FirefoxDriver());
	}} 
	else if (browser.equalsIgnoreCase("edge")) 
	{
		driver.set(new EdgeDriver());
	}
	else 
	{
		throw new IllegalArgumentException("Browser not supported:+ browser");
	}
		}*/
	private void launchBrowser() {

		String browser = prop.getProperty("browser");

		if(browser.equalsIgnoreCase("chrome")) {

		    WebDriverManager.chromedriver().setup();
		    driver.set(new ChromeDriver());

		}

		else if(browser.equalsIgnoreCase("firefox")) {

		    WebDriverManager.firefoxdriver().setup();
		    driver.set(new FirefoxDriver());

		}
		else if(browser.equalsIgnoreCase("edge")) {

		    System.setProperty("webdriver.edge.driver",
		            prop.getProperty("edgeDriverPath"));

		    driver.set(new EdgeDriver());
		}
	        else 
	    	{
	    		throw new IllegalArgumentException("Browser not supported:+ browser");
	    	}

	    
	}
	    // Configure browser settings & open the URL; uses getDriver() for per-thread WebDriver
	
	private void configureBrowser()
	{
	//implement implicit wait
	int implicitWait=Integer.parseInt(prop.getProperty("implicitWait"));
	getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
	
	//maximise the browser
	getDriver().manage().window().maximize();
	
	
	//Navigate to URL
	try {
		getDriver().get(prop.getProperty("url"));
	} catch (Exception e) {
		System.out.println("Failed to Navigate to the URL:"+e.getMessage());
	}
	}
	@AfterMethod

	//terminating the browser
	public void tearDown() 
	{
		if(driver.get()!=null) 
		{
			try {
				driver.get().quit();
			} catch (Exception e) {
				System.out.println("Failed to quit browser:"+e.getMessage());
				
				
			}
		}
		System.out.println("WebDriver instance is closed");
		driver.remove();
		actionDriver.remove();
	}
	
	/*//Driver getter method
	public WebDriver getDriver()
	{
	return driver;
	}


	//driver setter method
	public void setDriver(WebDriver driver)
	{
		this.driver=driver;
	}
	*/
	//Getter for per-thread ActionDriver
	public static ActionDriver getActionDriver() 
	{
		if(actionDriver.get()==null) 
		{
			System.out.println("ActionDriver is not initialized");
			throw new IllegalStateException("ActionDriver is not initialized");
		}
		return  actionDriver.get();
		
	}
	//Getter for per-thread WebDriver
	public static WebDriver getDriver() 
	{
		if(driver.get()==null) 
		{
			System.out.println("WebDriver is not initialized");
			throw new IllegalStateException("WebDriver is not initialized");
		}
		return driver.get();
		
	}
	//Static wait for pause
 public void staticWait(int seconds)
 {
	 LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
 }

	 //getter method for properties file values,if needed globally
public static Properties getProp() {
	
	return prop;
}


	}
	
	
	

