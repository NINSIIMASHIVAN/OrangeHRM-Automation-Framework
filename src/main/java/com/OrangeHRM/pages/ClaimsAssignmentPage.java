package com.OrangeHRM.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.OrangeHRM.actiondriver.ActionDriver;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class ClaimsAssignmentPage {

    WebDriver driver;
    ActionDriver actionDriver;

    public ClaimsAssignmentPage(WebDriver driver) {
        this.driver = driver;
        this.actionDriver = new ActionDriver(driver);
    }

    // locators 

    private By employeeNameField = By.xpath("(//input[@placeholder='Type for hints...'])[1]");

    private By searchButton = By.cssSelector(
        "button[class='oxd-button oxd-button--medium oxd-button--secondary orangehrm-left-space']");

    private By viewDetailsButton = By.xpath("(//button[normalize-space()='View Details'])[1]");

    private By addExpenseButton = By.xpath("(//button[@class='oxd-button oxd-button--medium oxd-button--text'])[1]");

  
    private By expenseTypeDropdown = By.cssSelector("div[class='oxd-select-text oxd-select-text--active']");

    private By expenseDateField = By.xpath("//div[@class='oxd-dialog-container-default']"+"//div[contains(@class,'oxd-input-group')][.//*[contains(text(),'Date')]]//input");

    private By expenseAmountField = By.xpath( "//div[@class='oxd-dialog-container-default']" +"//div[contains(@class,'oxd-input-group')][.//*[contains(text(),'Amount')]]//input");
    	
    private By expenseNoteField = By.xpath("//div[@class='oxd-dialog-container-default']" + "//div[contains(@class,'oxd-input-group')][.//*[contains(text(),'Note')]]//textarea");
  
    private By expenseSaveButton = By.cssSelector("div.oxd-dialog-container-default button[class='oxd-button oxd-button--medium oxd-button--secondary orangehrm-left-space']");

    private By addAttachmentButton = By.xpath("(//button[@class='oxd-button oxd-button--medium oxd-button--text'])[2]");
    
    private By attachmentFileInput = By.cssSelector("input[type='file']");
    
    private By attachmentCommentField = By.cssSelector("textarea[placeholder='Type comment here']");

    private By attachmentSaveButton = By.xpath("//div[@class='oxd-dialog-container-default']//button[@type='submit']");

    private By submitButton = By.cssSelector("button.oxd-button.oxd-button--medium.oxd-button--secondary.orangehrm-sm-button");

    private By successToast = By.cssSelector(".oxd-toast--success");

    private By employeeNameInvalidMessage = By.cssSelector("span[class='oxd-text oxd-text--span oxd-input-field-error-message oxd-input-group__message']");

    // Navigation 

    public void navigateToEmployeeClaims() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/claim/viewAssignClaim");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlContains("/claim/viewAssignClaim"));
        System.out.println("Navigated to Employee Claims page");
    }

    public void searchClaimByEmployeeName(String firstName, String lastName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement nameField = wait.until(ExpectedConditions.elementToBeClickable(employeeNameField));
        nameField.clear();
        nameField.sendKeys(firstName);

        // Increased wait time for dropdown suggestion to appear (headless mode issue)
        By suggestion = By.xpath("//div[@role='listbox']//span[contains(text(),'" + lastName + "')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(suggestion)).click();

        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewDetailsButton));
        System.out.println("Found claim for: " + firstName + " " + lastName);
    }

    public boolean searchForNonExistentEmployeeAndCheck(String fakeName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement nameField = wait.until(ExpectedConditions.elementToBeClickable(employeeNameField));
        nameField.clear();
        nameField.sendKeys(fakeName);

        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();

        return isEmployeeNameInvalid();
    }

    public boolean isEmployeeNameInvalid() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement invalidMsg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(employeeNameInvalidMessage));
            return invalidMsg.getText().trim().equalsIgnoreCase("Invalid");
        } catch (Exception e) {
            System.out.println("No 'Invalid' validation message appeared: " + e.getMessage());
            return false;
        }
    }

    public void clickViewDetails() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(viewDetailsButton)).click();
        wait.until(ExpectedConditions.urlContains("/claim/"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//label[normalize-space()='Remarks']")));
        System.out.println("Opened claim detail: " + driver.getCurrentUrl());
    }

    // Add Expense

    public void clickAddExpense() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 500);");

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(addExpenseButton));
        } catch (org.openqa.selenium.TimeoutException | org.openqa.selenium.NoSuchElementException e) {
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 500);");
        }

        WebElement addBtn = wait.until(ExpectedConditions.presenceOfElementLocated(addExpenseButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", addBtn);

        wait.until(ExpectedConditions.elementToBeClickable(addExpenseButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(expenseTypeDropdown));
        System.out.println("Add Expense popup opened");
    }

    public void selectExpenseType(String expenseType) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(expenseTypeDropdown)).click();
        By option = By.xpath("//div[@role='listbox']//span[text()='" + expenseType + "']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(option)).click();
        System.out.println("Selected expense type: " + expenseType);
    }

    public void enterExpenseDate(String date) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement field = wait.until(ExpectedConditions.elementToBeClickable(expenseDateField));
        field.clear();
        field.sendKeys(date);
        System.out.println("Entered expense date: " + date);
    }

    public void enterExpenseAmount(String amount) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement field = wait.until(ExpectedConditions.elementToBeClickable(expenseAmountField));
        field.clear();
        field.sendKeys(amount);
        System.out.println("Entered expense amount: " + amount);
    }

    public void enterExpenseNote(String note) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement field = wait.until(ExpectedConditions.elementToBeClickable(expenseNoteField));
        field.clear();
        field.sendKeys(note);
        System.out.println("Entered expense note: " + note);
    }
    public void saveExpense() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // was 10
        wait.until(ExpectedConditions.elementToBeClickable(expenseSaveButton)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
            By.xpath("//div[@class='oxd-dialog-container-default']")));
        System.out.println("Expense saved successfully");
    }

    //  Add Attachment 
    public void clickAddAttachment() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement addBtn = wait.until(ExpectedConditions.presenceOfElementLocated(addAttachmentButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", addBtn);
        wait.until(ExpectedConditions.elementToBeClickable(addAttachmentButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(attachmentCommentField));
        System.out.println("Add Attachment popup opened");
    }

    public void enterAttachmentComment(String comment) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement field = wait.until(ExpectedConditions.elementToBeClickable(attachmentCommentField));
        field.clear();
        field.sendKeys(comment);
        System.out.println("Entered attachment comment: " + comment);
    }

    public void saveAttachment() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // was 10
        wait.until(ExpectedConditions.elementToBeClickable(attachmentSaveButton)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
            By.xpath("//div[@class='oxd-dialog-container-default']"))); // dialog container, not just the button
        System.out.println("Attachment saved successfully");
    }

    public void submitClaim() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
        System.out.println("Clicked Submit button");
    }

    public boolean isClaimAssignedSuccessfully() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
            wait.until(ExpectedConditions.visibilityOfElementLocated(successToast));
            System.out.println("Claim assignment confirmed — success toast visible");
            return true;
        } catch (Exception e) {
            System.out.println("No success toast after submission: " + e.getMessage());
            return false;
        }
    }
    

    public String getAttachmentFilePath() {
        File folder = new File("C:\\Users\\HP\\OneDrive\\Pictures\\Screenshots");
        File[] files = folder.listFiles((dir, name) ->
            name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg"));

        if (files == null || files.length == 0) {
            throw new RuntimeException("No image files found in Screenshots folder for attachment upload");
        }

        long maxSizeBytes = 1024 * 1024; // 1MB — matches OrangeHRM's stated limit

        File chosen = Arrays.stream(files)
            .filter(f -> f.length() < maxSizeBytes)
            .sorted(Comparator.comparing(File::getName))
            .findFirst()
            .orElseThrow(() -> new RuntimeException(
                "No image file under 1MB found in Screenshots folder for attachment upload"));

        System.out.println("Using attachment file: " + chosen.getAbsolutePath()
            + " (" + (chosen.length() / 1024) + " KB)");
        return chosen.getAbsolutePath();
    }
    
    public void uploadAttachmentFile(String filePath) {
        WebElement fileInput = driver.findElement(attachmentFileInput);
        fileInput.sendKeys(filePath);
        System.out.println("Uploaded attachment file: " + filePath);
    }

    public void assignClaimForEmployee(String firstName, String lastName,
            String expenseType, String date, String amount,
            String note, String attachmentComment) {

        navigateToEmployeeClaims();
        searchClaimByEmployeeName(firstName, lastName);
        clickViewDetails();

        clickAddExpense();
        selectExpenseType(expenseType);
        enterExpenseDate(date);
        enterExpenseAmount(amount);
        enterExpenseNote(note);
        saveExpense();

        clickAddAttachment();
        uploadAttachmentFile(getAttachmentFilePath());
        enterAttachmentComment(attachmentComment);
        saveAttachment();
        submitClaim();
    }
}