package com.OrangeHRM.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.OrangeHRM.actiondriver.ActionDriver;

public class ClaimsPage {

    WebDriver driver;
    ActionDriver actionDriver;

    public ClaimsPage(WebDriver driver) {
        this.driver = driver;
        this.actionDriver = new ActionDriver(driver);
    }

 
    private By claimTab = By.cssSelector( "a[href='/web/index.php/claim/viewClaimModule']" );

    private By submitClaimButton = By.xpath("//button[normalize-space()='Submit Claim']");

    private By eventDropdown = By.xpath( "(//div[@class='oxd-select-wrapper'])[1]");

    private By currencyDropdown = By.xpath("(//div[@class='oxd-select-wrapper'])[2]");

     private By remarksField = By.cssSelector("textarea.oxd-textarea.oxd-textarea--active.oxd-textarea--resize-vertical");

    private By createButton = By.cssSelector("button[type='submit']");

    private By successToast = By.cssSelector(".oxd-toast--success");

    private By submitClaimTab = By.xpath("//a[normalize-space()='Submit Claim']");

    // ── Navigation

    public void navigateToClaims() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Clicking Claim in left sidebar using JavaScript to avoid interception
        WebElement claim = wait.until(
            ExpectedConditions.presenceOfElementLocated(claimTab)
        );
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].click();", claim
        );

        // Wait for Claim page to load
        wait.until(ExpectedConditions.urlContains("/claim/viewClaim"));
        System.out.println("Navigated to Claims page");
    }

    public void clickSubmitClaimButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Wait for Submit Claim button to be clickable
        wait.until(ExpectedConditions.elementToBeClickable(
            submitClaimButton
        )).click();

        // Wait for Submit Claim form to load
        wait.until(ExpectedConditions.urlContains("/claim/submitClaim"));
        System.out.println("Clicked Submit Claim button");
    }

    // ── Form interaction ──────────────────────────────────────────────────────

    public void selectEvent(String eventName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Click the Event dropdown to open it
        wait.until(ExpectedConditions.elementToBeClickable(
            eventDropdown
        )).click();

        // Select option by visible text
        By eventOption = By.xpath(
            "//div[@role='option']//span[text()='" + eventName + "']"
        );
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            eventOption
        )).click();

        System.out.println("Selected event: " + eventName);
    }

    public void selectCurrency(String currencyName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Click the Currency dropdown to open it
        wait.until(ExpectedConditions.elementToBeClickable(
            currencyDropdown
        )).click();

        // Select currency option by visible text
        By currencyOption = By.xpath(
            "//div[@role='option']//span[text()='" + currencyName + "']"
        );
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            currencyOption
        )).click();

        System.out.println("Selected currency: " + currencyName);
    }

    public void enterRemarks(String remarks) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement textarea = wait.until(
            ExpectedConditions.elementToBeClickable(remarksField)
        );
        textarea.clear();
        textarea.sendKeys(remarks);
        System.out.println("Entered remarks: " + remarks);
    }

    public void clickCreate() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(
            createButton
        )).click();
        System.out.println("Clicked Create button");
    }

    // ── Verification ──────────────────────────────────────────────────────────

    public boolean isClaimSubmittedSuccessfully() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                successToast
            ));
            System.out.println("Claim submitted successfully — success toast visible");
            return true;
        } catch (Exception e) {
            System.out.println("Claim submission failed — no success toast: "
                + e.getMessage());
            return false;
        }
    }

    public boolean isRequiredFieldErrorDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".oxd-input-field-error-message")
            ));
            return true;
        } catch (Exception e) {
            System.out.println("Required field error not shown: "
                + e.getMessage());
            return false;
        }
    }

    //method to submit a claim with all required fields
    public void submitClaim(String eventName, String currency, String remarks) {
        navigateToClaims();
        clickSubmitClaimButton();
        selectEvent(eventName);
        selectCurrency(currency);
        enterRemarks(remarks);
        clickCreate();
    }
}
