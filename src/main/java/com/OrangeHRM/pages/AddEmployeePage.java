package com.OrangeHRM.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.OrangeHRM.actiondriver.ActionDriver;

public class AddEmployeePage {

	WebDriver driver;
	ActionDriver actionDriver;
	
	
	public AddEmployeePage(WebDriver driver) {
		this.driver=driver;
		this.actionDriver=new ActionDriver(driver);
	}
//locators
	private By PIMTab = By.cssSelector("a[href='/web/index.php/pim/viewPimModule']");
	private By addButton = By.xpath("//button[normalize-space()='Add']");	
	private By firstNameField=By.cssSelector("input[name='firstName']");
	private By middleNameField=By.cssSelector("input[name='middleName']");
	private By lastNameField=By.cssSelector("input[name='lastName']");
	private By loginDetailsSwitch=By.cssSelector(".oxd-switch-input");
	private By usernameField = By.xpath("(//input[@class='oxd-input oxd-input--active'])[3]");	
   // private By usernameField=By.xpath("//label[normalize-space()='Username']/ancestor::div[contains(@class,'oxd-input-group')]/following-sibling::div//input");
	private By anabledRadioButton=By.xpath("//label[normalize-space()='Enabled']//span[@class='oxd-radio-input oxd-radio-input--active --label-right oxd-radio-input']");
	//private By passwordField = By.xpath("(//input[@type='password'])[1]");
	private By passwordField = By.xpath("(//input[@type='password'])[1]");
	private By confirmPasswordField = By.xpath("(//input[@type='password'])[2]");
	//private By confirmPasswordField = By.xpath("(//input[@type='password'])[2]");
	private By EmployeeIdField=By.xpath("//label[normalize-space()='Employee Id']/../following-sibling::div/input");
	private By SaveButton=By.cssSelector("button[class='oxd-button oxd-button--medium oxd-button--secondary orangehrm-left-space']");
	private By successToast = By.cssSelector(".oxd-toast--success");	
	private By requiredFieldError=By.cssSelector("span[class='oxd-text oxd-text--span oxd-input-field-error-message oxd-input-group__message']");
	private By usernameAlreadyExistsError=By.xpath("//span[normalize-space()='Username already exists']");	
	//private By savedEmployeeId = By.xpath("//label[normalize-space()='Employee Id']/../following-sibling::div/input");
	//methods 
	//method to add employee
	public void naviagateToAddEmployee() {
	WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(20));
	//using Javascript to bypass interactability checks
	 WebElement pimLink = wait.until(
		        ExpectedConditions.presenceOfElementLocated(
		            By.cssSelector("a[href='/web/index.php/pim/viewPimModule']")
		        )
		    );
		    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", pimLink);
		    //wait for PIM Employee List to load 
		    wait.until(ExpectedConditions.urlContains("/pim/viewEmployeeList"));
		    // Now click Add button
		    WebElement addBtn = wait.until(
		        ExpectedConditions.elementToBeClickable(
		            By.xpath("//button[normalize-space()='Add']")
		        )
		    );
		    addBtn.click();
		    wait.until(ExpectedConditions.urlContains("/pim/addEmployee"));
		}	 
	
	
	//employee details
	public void enterFirstName(String firstName) {
		actionDriver.enterText(firstNameField, firstName);
	}
	
	public void enterMiddleName(String middleName) {
		actionDriver.enterText(middleNameField, middleName);	
	}
	public void enterLastName(String lastName) {
		actionDriver.enterText(lastNameField, lastName);	
		
	}
	/*public void enterEmployeeId(String employeeId) {
		actionDriver.enterText(EmployeeIdField, employeeId);
	}*/
	
/*	public String getSavedEmployeeId() {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        wait.until(ExpectedConditions.urlContains("/pim/addEmployee"));
	        String id = driver.findElement(savedEmployeeId).getAttribute("value");
	        System.out.println("Saved Employee ID: " + id);
	        return id;
	    } catch (Exception e) {
	        System.out.println("Could not read Employee ID: " + e.getMessage());
	        return "";
	    }
	}*/
	public void enableLoginDetails() {
	    // Wait for loading overlay to disappear first
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    wait.until(ExpectedConditions.invisibilityOfElementLocated(
	        By.cssSelector(".oxd-form-loader")
	    ));
	    
	    // Now click the switch
	    actionDriver.click(loginDetailsSwitch);
	    
	    // Wait for username field to appear
	    wait.until(ExpectedConditions.visibilityOfElementLocated(
	        By.xpath("(//input[@class='oxd-input oxd-input--active'])[3]")
	    ));
	    System.out.println("Login details section is now visible");
	}
	public void enterUserName(String username) {
		actionDriver.enterText(usernameField,username);
		}
	public void selectEnabledRadioButton() {
		actionDriver.click(anabledRadioButton);
		}
	public void enterPassword(String pasword) {
		actionDriver.enterText(passwordField, pasword);
		}
	
	public void enterConfirmPassword(String confirmPassword) {
		actionDriver.enterText(confirmPasswordField, confirmPassword);
	}
	public void clickSave() {
	actionDriver.click(SaveButton);	
	}
	
	public boolean isEmployeeAddedSuccessfully() 
	{
		try {
			WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOfElementLocated(successToast));
			return true;
		} catch (Exception e) {
			System.out.println("Employee addition failed;"+e.getMessage());	
			return false;
		}
	}
	public boolean isRequiredFieldErrorDisplayed() 
	{
		try {
			WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOfElementLocated(requiredFieldError));
			 return true;
		} catch (Exception e) {
		System.out.println("Required field error message is not displayed."+e.getMessage());
		return false;
		}
		
	
	}
	

	public void addEmployeeWithLoginDetails(String firstName,String middleName,String lastName,String employeeId, String userName,String password, String confirmPassword)
	{
		naviagateToAddEmployee();
		enterFirstName(firstName);
		enterMiddleName(middleName);
		enterLastName(lastName);
		enableLoginDetails();
		enterUserName(userName);
		selectEnabledRadioButton();
		enterPassword(password);
		enterConfirmPassword(confirmPassword);
		clickSave();
	
	}
	


public boolean UserNameAlreadyExistsErrorDisplayed() 
	{
		try {
			WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOfElementLocated(usernameAlreadyExistsError));
			 return true;
		} catch (Exception e) {
		System.out.println("UseName already exists error message is not displayed."+e.getMessage());
		return false;
		}
		
	
	}
	
	

	
	public void addEmployeeWithoutLoginDetails(String firstName,String middleName,String lastName,String employeeId)
	{
		naviagateToAddEmployee();
		enterFirstName(firstName);
		enterMiddleName(middleName);
		enterLastName(lastName);
		//enterEmployeeId(employeeId);
		clickSave();
		}
	
	
public void dismissPasswordPopupIfPresent() {
    try {
        WebDriverWait wait = new WebDriverWait((WebDriver) driver, Duration.ofSeconds(5));
        WebElement okButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space()='OK']")
            )
        );
        okButton.click();
        System.out.println("Password popup dismissed.");
    } catch (Exception e) {
        System.out.println("No password popup present, continuing.");
    }
}

//method to delete employee
//navigate to pim
//select employee list and wait for it to load
//enter username and click search
//click on the delete icon that appears infront of the employee name



}	
		
	
	
