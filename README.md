# SauceDemo Test Automation Framework

Selenium WebDriver + Java + Maven + TestNG framework that tests <https://www.saucedemo.com>,
built with the Page Object Model (POM), explicit waits, Extent Reports and screenshots on failure.

## Tech stack
| Tool | Purpose |
|------|---------|
| Java 11+ | Language |
| Maven | Builds the project, downloads libraries |
| Selenium 4 | Controls the browser (driver is downloaded automatically) |
| TestNG | Runs tests, groups, assertions |
| Extent Reports | HTML report with pass / fail / skip counts |

## Prerequisites
1. JDK 11 or later, with `JAVA_HOME` set (`java -version` should work)
2. Maven (`mvn -version`) - or use IntelliJ's built-in Maven
3. Google Chrome installed (Firefox / Edge also supported)
4. Internet access (the app is online and Selenium downloads the browser driver)

## Setup
```bash
git clone <your-repo-url>
cd saucedemo-automation
```
Open the folder in IntelliJ -> right-click `pom.xml` -> **Maven -> Reload project**.

## How to run
```bash
mvn clean test                              # whole suite (testng.xml): smoke + regression
mvn clean test -Dbrowser=firefox            # another browser
mvn clean test -Dheadless=true              # no browser window
mvn clean test -DsuiteXmlFile=testng.xml    # choose a suite file
```
In IntelliJ: right-click **`testng.xml` -> Run**. This is the way to get the Extent report and failure screenshots.
(Running a single test class directly skips the listener, so no report/screenshot is produced.)

## Where are the results?
* HTML report: `reports/ExtentReport_<timestamp>.html` - open it in a browser
* Failure screenshots: `screenshots/<testName>_<timestamp>.png`

## Project structure
```
saucedemo-automation
├── pom.xml                      Dependencies + Surefire (runs testng.xml)
├── testng.xml                   Suite: "Smoke Tests" and "Regression Tests" groups
├── docs/BEGINNER_GUIDE.md       Plain-English explanation of every part
└── src
    ├── main
    │   ├── java/com/capstone
    │   │   ├── pages            One class per page (all locators live here)
    │   │   │   ├── BasePage, SecuredPage, LoginPage, ProductsPage, ProductDetailsPage,
    │   │   │   │   CartPage, CheckoutInformationPage, CheckoutOverviewPage,
    │   │   │   │   CheckoutCompletePage, SideMenu
    │   │   └── utils            ConfigReader, WaitUtils, ScreenshotUtil, ExtentManager, PriceUtils, TestData
    │   └── resources/config.properties    Browser, URL, wait time, credentials
    └── test/java/com/capstone
        ├── base/BaseTest        Opens/closes the browser for every test
        ├── listeners/TestListener   Extent report + screenshot on failure
        └── tests                LoginTest, ProductListingTest, ProductDetailsTest,
                                 CartTest, CheckoutTest, NavigationTest, EndToEndTest
```

## Test coverage
| Module | Positive | Negative / edge |
|--------|----------|-----------------|
| Login | valid login | wrong password, empty fields, empty password, locked-out user, logout -> Back |
| Product Listing | product count, sort A-Z / Z-A / price low-high / high-low | default order |
| Product Details | name/price/description match listing, add to cart, back | viewing does not add to cart |
| Cart | add one, add many, remove, item details, continue shopping | empty cart |
| Checkout | full flow, order summary, price calculation | empty form, missing last name, missing postal code |
| Navigation | open/close menu, All Items, About, Logout, Reset App State | - |
| End-to-End | login -> add -> cart -> checkout -> confirmation | - |

## Design decisions
* **POM**: tests never contain locators; they call page methods.
* **Config**: browser, URL, wait time and credentials come from `config.properties` (can be overridden with `-D`).
* **Waits**: only explicit waits (`WaitUtils`); no `Thread.sleep()`.
* **Groups**: `smoke` (key flows) and `regression` (detailed checks), defined in `testng.xml`.

## Troubleshooting
| Problem | Fix |
|---------|-----|
| No green Run arrow in IntelliJ | Maven -> Reload project; check `src/test/java` is a Test Sources Root; check Project SDK |
| `config.properties not found` | Make sure it is in `src/main/resources`, then `mvn clean test` |
| Browser does not start | Update Chrome; check internet (Selenium Manager downloads the driver) |
| Test fails on a locator | Inspect the element in DevTools and fix the locator in the page class |
