# OrangeHRM Automation Framework

## Project Overview

This project is a scalable Selenium Automation Framework developed using Java, TestNG, Maven, and Page Object Model (POM) architecture for testing the OrangeHRM application.

The framework supports:

* Cross-browser testing
* Parallel execution
* Thread-safe WebDriver management
* Extent Reports integration
* Reusable utilities and components
* Configurable execution using properties files

---

## Technologies Used

* Java
* Selenium WebDriver
* TestNG
* Maven
* Extent Reports
* WebDriverManager
* Page Object Model (POM)
* ThreadLocal
* Git & GitHub

---

## Framework Features

### Cross-Browser Testing

Supports execution on:

* Chrome
* Firefox
* Microsoft Edge

---

### Parallel Execution

Implemented parallel execution using:

* TestNG
* ThreadLocal WebDriver management

This ensures thread-safe browser execution during concurrent test runs.

---

### Reporting

Integrated Extent Reports for:

* Test execution tracking
* Pass/fail reporting
* Screenshot capture on failure
* Detailed execution logs

---

### Reusable Framework Components

The framework includes:

* BaseClass for browser setup and teardown
* ActionDriver utility methods
* Centralized configuration management
* Wait utilities
* Listener implementation
* Screenshot utilities

---

## Project Structure

```text
src/test/java
src/main/java
src/main/resources
test-output
pom.xml
testng.xml
```

---

## How to Run the Tests

### Clone the Repository

```bash
git clone <repository-url>
```

---

### Run Using Maven

```bash
mvn test
```

---

### Run Using TestNG XML

Execute:

```text
testng.xml
```

from Eclipse or IntelliJ.

---

## Sample Implementations

* Login functionality automation
* Home page validation
* Parallel browser execution
* Extent Report generation
* Screenshot capture on failures

---

## Future Improvements

* Jenkins CI/CD integration
* Selenium Grid execution
* Docker containerization
* REST Assured API integration
* Database validation
* Cloud execution using BrowserStack/Sauce Labs

---

## Author

Shivan Ninsiima

QA Automation Engineer | Selenium | Java | TestNG | API Testing
