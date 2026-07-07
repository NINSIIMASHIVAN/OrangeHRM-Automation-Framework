package com.OrangeHRM.test;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.OrangeHRM.actiondriver.ActionDriver;
import com.OrangeHRM.base.BaseClass;
import com.OrangeHRM.pages.AddEmployeePage;
import com.OrangeHRM.pages.DeleteEmployeePage;
import com.OrangeHRM.pages.LoginPage;
import com.OrangeHRM.utilities.DataProviders;

public class AddEmployeeTest extends BaseClass {
private LoginPage loginPage;
private AddEmployeePage addEmployeePage;
private DeleteEmployeePage deleteEmployeePage;
private static String createdFirstName = "";
private static String createdLastName = "";
private static String savedEmployeeId = "";

@BeforeMethod
public void setupPages() 
{
	loginPage=new LoginPage(getDriver());

	addEmployeePage=new AddEmployeePage(getDriver());
	deleteEmployeePage = new DeleteEmployeePage(getDriver());

loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
// Wait for dashboard to fully load before ANY test action starts
WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
wait.until(ExpectedConditions.urlContains("/dashboard/index"));

// Dismiss the Google Password Manager popup if it appears
addEmployeePage.dismissPasswordPopupIfPresent();
}




	@Test(dataProvider="addEmployeeData", dataProviderClass=DataProviders.class,priority=1)
public void verifyAddEmployeeWithLoginDetails(String firstName,String middleName,String lastName,String userName,String password, String confirmPassword)
	{
addEmployeePage.naviagateToAddEmployee();
addEmployeePage.enterFirstName(firstName);
addEmployeePage.enterMiddleName(middleName);
createdFirstName = firstName;
createdLastName = lastName;
addEmployeePage.enterLastName(lastName);
addEmployeePage.enableLoginDetails();
addEmployeePage.enterUserName(userName);
addEmployeePage.selectEnabledRadioButton();
addEmployeePage.enterPassword(password);
addEmployeePage.enterConfirmPassword(confirmPassword);

//savedEmployeeId = addEmployeePage.getSavedEmployeeId();
//System.out.println("Will reuse Employee ID: " + savedEmployeeId);

addEmployeePage.clickSave();

Assert.assertTrue(addEmployeePage.isEmployeeAddedSuccessfully(), "Employee addition failed; " + firstName + " " + lastName);
	}
	
@Test(priority=2)
public void verifyAddEmployeeWithoutLoginDetails()
{
    addEmployeePage.naviagateToAddEmployee();
	addEmployeePage.enterFirstName("");
	addEmployeePage.enterMiddleName("");
	addEmployeePage.enterLastName("");
	addEmployeePage.enableLoginDetails();
	addEmployeePage.enterUserName("");
	addEmployeePage.selectEnabledRadioButton();
	addEmployeePage.enterPassword("");
	addEmployeePage.enterConfirmPassword("");
	addEmployeePage.clickSave();
	
	Assert.assertTrue(addEmployeePage.isRequiredFieldErrorDisplayed(), "Required field error message is not displayed."); 
}
@Test(priority=3)
public void verifyUserNameAlreadyExists() {
    addEmployeePage.naviagateToAddEmployee();
    addEmployeePage.enterFirstName("Duplicate");
    addEmployeePage.enterMiddleName("testing");
    addEmployeePage.enterLastName("UserTest");
    addEmployeePage.enableLoginDetails();
    addEmployeePage.enterUserName("ElianaBabirye2"); 
    addEmployeePage.selectEnabledRadioButton();
    
    
    
    Assert.assertTrue( addEmployeePage.UserNameAlreadyExistsErrorDisplayed(), "Expected duplicate userName error: " );
    		
 }
//@AfterClass
/*public void cleanUpCreatedEmployee() {
    System.out.println("=== CLEANUP STARTING ===");
    deleteEmployeePage = new DeleteEmployeePage(getDriver());
    deleteEmployeePage.deleteEmployeeByName(
        createdFirstName, createdLastName
    );
    System.out.println("=== CLEANUP COMPLETE ===");
}*/
}
