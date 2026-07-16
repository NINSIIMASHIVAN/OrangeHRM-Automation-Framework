package com.OrangeHRM.test;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.OrangeHRM.base.BaseClass;
import com.OrangeHRM.pages.ClaimsAssignmentPage;
import com.OrangeHRM.pages.LoginPage;

public class ClaimsAssignmentTest extends BaseClass {

    private LoginPage loginPage;
    private ClaimsAssignmentPage claimsAssignmentPage;

    @BeforeMethod
    public void setupPages() {
        loginPage = new LoginPage(getDriver());
        claimsAssignmentPage = new ClaimsAssignmentPage(getDriver());

        // Login as ADMIN to assign/process employee claims
        loginPage.login(
            prop.getProperty("username"),   // Admin
            prop.getProperty("password")    // admin123
        );

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        wait.until(ExpectedConditions.urlContains("/dashboard/index"));
        System.out.println("Logged in as Admin for claim assignment");
    }

    //  Admin assigns Eliana's claim 

    @Test(priority = 1)
    public void verifyAdminCanAssignEmployeeClaim() {
        claimsAssignmentPage.assignClaimForEmployee(
            "Eliana", "Nakyeyune", "Accommodation", "2026-07-07", "500",
            "Accommodation expense for business trip", "Supporting document attached");

        Assert.assertTrue(
            claimsAssignmentPage.isClaimAssignedSuccessfully(),
            "Expected success toast after Admin assigns the claim");

        System.out.println("Admin successfully assigned Eliana's claim");
    }

    
    @Test(priority = 2)
    public void verifyNoMatchForNonExistentEmployee() {
        claimsAssignmentPage.navigateToEmployeeClaims();

        boolean isInvalid = claimsAssignmentPage.searchForNonExistentEmployeeAndCheck("NonExistentUser999");

        Assert.assertTrue(isInvalid, "Expected 'Invalid' validation for a non-existent employee");
        System.out.println("Correctly rejected non-existent employee search");
    }}