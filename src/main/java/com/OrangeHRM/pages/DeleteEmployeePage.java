package com.OrangeHRM.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.OrangeHRM.actiondriver.ActionDriver;

public class DeleteEmployeePage {

    WebDriver driver;
    ActionDriver actionDriver;

    public DeleteEmployeePage(WebDriver driver) {
        this.driver = driver;
        this.actionDriver = new ActionDriver(driver);
    }

    // ── Confirmed locators from SelectorHub ──────────────────────────────────

    // Employee List tab in PIM top nav
    private By employeeListTab = By.xpath(
        "//a[normalize-space()='Employee List']"
    );

    // Employee Name search field — index [1] because there are 2 "Type for hints" inputs
    private By employeeNameSearchField = By.xpath(
        "(//input[@placeholder='Type for hints...'])[1]"
    );

    // Search button
    private By searchButton = By.xpath(
        "//button[normalize-space()='Search']"
    );

    // Delete (trash) icon — the parent button of the bi-trash icon
    private By deleteIcon = By.xpath(
        "//i[@class='oxd-icon bi-trash']/parent::button"
    );

    // Confirmation dialog — "Yes, Delete" button
    private By confirmDeleteButton = By.xpath(
        "//button[normalize-space()='Yes, Delete']"
    );

    // "No Records Found" — used to check if search returned nothing
    private By noRecordsFound = By.xpath(
        "//span[text()='No Records Found']"
    );

    // Success toast after deletion
    private By deletionSuccessToast = By.cssSelector(".oxd-toast--success");

    // ── Navigation ────────────────────────────────────────────────────────────

    public void navigateToEmployeeList() {
        // Navigate directly via URL — more reliable than clicking through nav
        driver.get(
            "https://opensource-demo.orangehrmlive.com" +
            "/web/index.php/pim/viewEmployeeList"
        );
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlContains("/pim/viewEmployeeList"));
        System.out.println("Navigated to Employee List");
    }

    // ── Search ────────────────────────────────────────────────────────────────

    public void searchEmployeeByName(String firstName, String lastName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for search field to be ready
        WebElement searchField = wait.until(
            ExpectedConditions.elementToBeClickable(employeeNameSearchField)
        );

        // Clear any existing value and type the name
        searchField.clear();
        searchField.sendKeys(firstName + " " + lastName);

        // Click Search button
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();

        System.out.println("Searching for employee: " + firstName + " " + lastName);

        // Wait for results to load
        wait.until(ExpectedConditions.or(
            ExpectedConditions.visibilityOfElementLocated(deleteIcon),
            ExpectedConditions.visibilityOfElementLocated(noRecordsFound)
        ));
    }

    // ── Check if employee exists in results ───────────────────────────────────

    public boolean isEmployeeFoundInResults() {
        try {
            // If no records found message appears, employee doesn't exist
            if (!driver.findElements(noRecordsFound).isEmpty()) {
                System.out.println("No records found in search results");
                return false;
            }
            // Otherwise at least one result row with a delete icon exists
            return !driver.findElements(deleteIcon).isEmpty();
        } catch (Exception e) {
            System.out.println("Could not determine search results: "
                + e.getMessage());
            return false;
        }
    }

    // ── Delete ────────────────────────────────────────────────────────────────

    public void clickDeleteIcon() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // There may be multiple rows — click the FIRST delete icon
        WebElement firstDeleteIcon = wait.until(
            ExpectedConditions.elementToBeClickable(deleteIcon)
        );

        // Use JavaScript click to avoid interception issues
        ((JavascriptExecutor) driver)
            .executeScript("arguments[0].click();", firstDeleteIcon);

        System.out.println("Clicked delete icon");
    }

    public void confirmDeletion() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for the "Are you Sure?" dialog to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            confirmDeleteButton
        ));

        // Click "Yes, Delete"
        wait.until(ExpectedConditions.elementToBeClickable(
            confirmDeleteButton
        )).click();

        System.out.println("Confirmed deletion — clicked Yes, Delete");

        // Wait for dialog to close
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
            confirmDeleteButton
        ));
    }

    public boolean isDeletionSuccessful() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                deletionSuccessToast
            ));
            System.out.println("Deletion confirmed via success toast");
            return true;
        } catch (Exception e) {
            System.out.println("No success toast after deletion: "
                + e.getMessage());
            return false;
        }
    }

    // ── Full delete flow ──────────────────────────────────────────────────────

    public void deleteEmployeeByName(String firstName, String lastName) {
        navigateToEmployeeList();
        searchEmployeeByName(firstName, lastName);

        if (isEmployeeFoundInResults()) {
            clickDeleteIcon();
            confirmDeletion();
            isDeletionSuccessful(); // logs whether toast appeared
            System.out.println("=== Employee deleted: "
                + firstName + " " + lastName + " ===");
        } else {
            System.out.println("=== Employee not found, nothing to delete: "
                + firstName + " " + lastName + " ===");
        }
    }
}
