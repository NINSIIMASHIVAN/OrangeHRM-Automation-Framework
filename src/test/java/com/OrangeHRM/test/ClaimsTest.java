package com.OrangeHRM.test;

import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.OrangeHRM.base.BaseClass;
import com.OrangeHRM.pages.ClaimsPage;
import com.OrangeHRM.pages.LoginPage;
import com.OrangeHRM.utilities.DataProviders;

public class ClaimsTest extends BaseClass {

    private LoginPage loginPage;
    private ClaimsPage claimsPage;

    @BeforeMethod
    public void setupPages() {
        loginPage = new LoginPage(getDriver());
        claimsPage = new ClaimsPage(getDriver());

        // Login into the employees account before each test
        loginPage.login( prop.getProperty("employeeUsername"), prop.getProperty("employeePassword"));

        // Wait for dashboard to fully load
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        wait.until(ExpectedConditions.urlContains("/dashboard/index"));
        System.out.println("Logged in as employee: " + prop.getProperty("employeeUsername"));
    }

   //Tests

    //verify that claims can be submitted successfully with valid data using a data provider for multiple scenarios.
    @Test(dataProvider ="claimData", dataProviderClass = DataProviders.class,
          priority = 1)
    public void verifyClaimSubmittedSuccessfully(String eventName,
            String currency, String remarks) {

        claimsPage.submitClaim(eventName, currency, remarks);

        Assert.assertTrue(
            claimsPage.isClaimSubmittedSuccessfully(),
            "Expected success toast after claim submission but none appeared"
        );

        System.out.println("Claim submitted successfully: event=" + eventName
            + " currency=" + currency);
    }

  //Verify that tests fail when you don't include the event

    @Test(priority = 2)
    public void verifyClaimFailsWithoutSelectingEvent() {
        claimsPage.navigateToClaims();
        claimsPage.clickSubmitClaimButton();

        // Skip event selection intentionally — only select currency
        claimsPage.selectCurrency("United States Dollar"); 
        claimsPage.enterRemarks("Test remark without event");
        claimsPage.clickCreate();

        Assert.assertTrue(
            claimsPage.isRequiredFieldErrorDisplayed(),
            "Expected required field error when Event is not selected"
        );
        System.out.println("Validation works — Event is required");
    }

    //Verify that tests fail when you don't include the currency
    @Test(priority = 3)
    public void verifyClaimFailsWithoutSelectingCurrency() {
        claimsPage.navigateToClaims();
        claimsPage.clickSubmitClaimButton();

        // Select event but skip currency intentionally
        claimsPage.selectEvent("Accommodation"); 
        claimsPage.enterRemarks("Test remark without currency");
        claimsPage.clickCreate();

        Assert.assertTrue(
            claimsPage.isRequiredFieldErrorDisplayed(),
            "Expected required field error when Currency is not selected"
        );
        System.out.println("Validation works — Currency is required");
    }

    //verify that test fails when you don't include any of the required fields.
    @Test(priority = 4)
    public void verifyClaimFailsWithAllFieldsEmpty() {
        claimsPage.navigateToClaims();
        claimsPage.clickSubmitClaimButton();

        // Click Create without filling anything
        claimsPage.clickCreate();

        Assert.assertTrue(
            claimsPage.isRequiredFieldErrorDisplayed(),
            "Expected required field errors when all fields are empty"
        );

        System.out.println("Validation works — all required fields enforced");
    }
}
