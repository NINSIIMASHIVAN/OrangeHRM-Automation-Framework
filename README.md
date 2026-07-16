# OrangeHRM Automation Framework

A Selenium WebDriver + Java test automation framework built for the [OrangeHRM demo application](https://opensource-demo.orangehrmlive.com/), with Page Object Model architecture, cross-browser support, parallel execution, Maven build management, and a working Jenkins CI/CD pipeline.


![Build Status](https://img.shields.io/badge/build-passing-brightgreen)
![Java](https://img.shields.io/badge/Java-21-orange)
![Selenium](https://img.shields.io/badge/Selenium-4.33.0-green)
![TestNG](https://img.shields.io/badge/TestNG-7.11.0-blue)

This project is a scalable Selenium Automation Framework developed using Java, TestNG, Maven, and Page Object Model (POM) architecture for testing the OrangeHRM application.

The framework supports:
* Page Object Model
* Cross-browser testing
* Parallel execution
* Thread-safe WebDriver management
* Extent Reports integration
* Reusable utilities and components
* Configurable execution using properties files
* Jenkins CI/CD integration


## Overview

This framework automates regression testing for OrangeHRM's login, dashboard, and logout flows,Claims management, with a structure designed to scale toward broader HR module coverage (Admin, PIM, Leave Management).

**Current test coverage:**
- Valid login
- Invalid login (data-driven, multiple credential sets)
- Dashboard load verification
- Logout flow
-Claims Management
-Creation of Employee  



## Tech Stack

| Category | Tools |
|---|---|
| Language | Java 21 |
| Automation | Selenium WebDriver 4.33.0 |
| Test Runner | TestNG 7.11.0 |
| Build Tool | Maven |
| Design Pattern | Page Object Model (POM) |
| Driver Management | WebDriverManager |
| Reporting | ExtentReports (Spark) |
| Data-Driven Testing | Apache POI (Excel) |
| Thread Safety | ThreadLocal\<WebDriver\> |
| CI/CD | Jenkins (Freestyle + Declarative Pipeline) |
| Version Control | Git, GitHub (webhook-triggered builds) |

---

## Framework Features

- **Page Object Model** — page logic separated from test logic for maintainability
- **Cross-browser support** — Chrome, Firefox, and Edge, configurable via `config.properties`
- **Parallel test execution** — TestNG `parallel="tests"` with `ThreadLocal<WebDriver>` for thread-safe browser instances
- **Headless execution** — toggle via `headless=true/false` in config, used for CI runs
- **Data-driven testing** — Excel-based test data via Apache POI, covering valid and invalid login scenarios
- **Custom reporting** — ExtentReports with a TestNG listener for pass/fail/skip tracking
- **CI/CD pipeline** — Jenkins job triggered automatically via GitHub webhook on every push, running `mvn clean install`, publishing ExtentReports as an HTML artifact, and sending email notifications on build success/failure

---

## Project Structure

```
src
├── main
│   ├── java/com/OrangeHRM
│   │   ├── actiondriver/      → reusable Selenium action wrapper (click, type, wait, etc.)
│   │   ├── base/              → BaseClass: browser setup/teardown, ThreadLocal driver management
│   │   ├── listeners/         → TestNG listener for ExtentReports integration
│   │   ├── pages/             → Page Object classes (LoginPage, HomePage)
│   │   └── utilities/         → ExcelReaderUtility, DataProviders, ExtentManager
│   └── resources/
│       └── config.properties  → browser, headless, wait, URL configuration
└── test
    ├── java/com/OrangeHRM/test/   → test classes (LoginPageTest, HomePageTest)
    └── resources/
        ├── testdata/              → Excel test data files
        └── testng.xml             → TestNG suite configuration
```

---

## Running the Tests

### Prerequisites
- Java 21
- Maven 3.9+
- Chrome, Firefox, or Edge installed locally

### Run via Maven
```bash
mvn clean install
```

### Run via TestNG XML directly
```bash
mvn test -DsuiteXmlFile=src/test/resources/testng.xml
```

### Configure browser and headless mode
Edit `src/main/resources/config.properties`:
```properties
browser=chrome
headless=true
```

---

## CI/CD Pipeline

This project runs through a **Jenkins pipeline** triggered automatically on every push to GitHub via webhook.

**Pipeline stages:**
1. **Checkout** — pulls latest code from GitHub
2. **Build** — `mvn clean install`
3. **Test** — runs the full TestNG suite headlessly
4. **Reports** — publishes the ExtentReports HTML report as a Jenkins artifact
5. **Notify** — sends an email with build status (success/failure), including the build log and a link to the report

See [`Jenkinsfile`](./Jenkinsfile) for the full pipeline definition.

### Build Evidence

| Cross-Browser Execution | Jenkins Build Success | CI/CD Email Notification |
|---|---|---|
| ![cross-browser](docs/screenshots/cross-browser-run.png) | ![jenkins-success](docs/screenshots/jenkins-success.png) | ![email-notification](docs/screenshots/ci-email.png) |

*(Screenshots show an actual local run: Chrome/Firefox/Edge execution, a passing Jenkins console output, and the automated build-status email.)*



## Known Limitations
-Shared Public Test Environment

This framework was developed using the public OrangeHRM demo application, which is accessible to users worldwide. Since the environment is shared, test data created during one execution (such as employees, usernames, and other records) may be modified or deleted by other users at any time.

As a result:

Test data cannot be assumed to persist between executions.
Some test cases require recreating prerequisite data before they can be executed successfully.
Automated tests that depend on previously created employees or users may fail due to external changes rather than defects in the framework or application.
During development, tests such as Claims Assignment depended on employees created in previous test runs. Because other users could delete these employees from the shared demo environment, I often had to verify whether the required test data still existed or recreate it before executing the tests. This experience highlighted the importance of test data management and test independence in automation frameworks.

Lessons Learned

This project reinforced the importance of designing automated tests that are independent, repeatable, and resilient to changing test data. It also highlighted the value of test data management strategies, such as generating unique data, using dedicated test environments, or automating test data setup and cleanup.


- Jenkinsfile currently uses Windows `bat` steps; will not run as-is on a Linux Jenkins agent


## Author

**Shivan Ninsiima**
QA Automation Engineer | SDET
[GitHub](https://github.com/NINSIIMASHIVAN) · [LinkedIn](https://linkedin.com/in/shivan-ninsiima)
