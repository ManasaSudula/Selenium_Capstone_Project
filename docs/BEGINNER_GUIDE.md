# Beginner Guide - understand every part

## 1. The big idea
A test = "do what a user does, then check the result".
Our code is split so that each file has ONE job:

```
Test class  -->  Page class  -->  Selenium  -->  Browser  -->  SauceDemo
 (what to check)  (how to click/type)
```
Analogy: the **test** is the manager saying "log in and add a backpack".
The **page class** is the employee who knows where every button is.

## 2. Page Object Model (POM)
* One class per page: `LoginPage`, `ProductsPage`, `CartPage`...
* Locators (`By.id("login-button")`) live ONLY in page classes.
* If the website changes a button id, you fix ONE line, not 40 tests.
* `BasePage` = shared helpers (click, type, getText) that always wait first.
* `SecuredPage` = pages after login share the cart icon and title.

## 3. Locators - how we find elements
| Strategy | Example | When |
|----------|---------|------|
| id | `By.id("user-name")` | best, fastest, most stable |
| CSS | `By.cssSelector(".inventory_item")` | classes / attributes |
| XPath | `//div[... ='Sauce Labs Backpack']/ancestor::...//button` | find a button INSIDE the card of a named product |

Practice: open the site, press F12, click the arrow icon, click the element, read its id/class.

## 4. Waits (synchronisation)
Pages need time to load. `Thread.sleep(5000)` always waits 5 s (slow + flaky).
An **explicit wait** keeps checking and continues the moment the element is ready:
```java
wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
```
All waits are in `WaitUtils`; the timeout (10 s) is in `config.properties`.

## 5. TestNG basics
* `@Test` marks a test. `groups = "smoke"` puts it in a group.
* `@BeforeMethod` / `@AfterMethod` (in `BaseTest`) run before/after EVERY test: open and close the browser.
* `Assert.assertEquals(actual, expected, "message")` - the test FAILS if they differ.
* `testng.xml` decides which groups run. We have two: **smoke** and **regression**.

## 6. Reporting and failure screenshots
`TestListener` is called by TestNG automatically:
* test passes -> logs "passed" in the report
* test fails -> logs the error **and** saves `screenshots/testName_timestamp.png` and attaches it to the report
* suite ends -> `extent.flush()` writes the HTML file

## 7. Follow one test from top to bottom
`CartTest.addSingleItemShouldShowItemInCart`
1. `BaseTest.setUp()` opens Chrome on saucedemo.com
2. `loginAsStandardUser()` -> `LoginPage.login(...)` types user/password (from config) and clicks Login
3. `productsPage.addToCart("Sauce Labs Backpack")` finds that product's card and clicks its button
4. `Assert...getCartItemCount() == 1` checks the badge
5. `openCart().getItemNames()` must equal `[Sauce Labs Backpack]`
6. `BaseTest.tearDown()` closes the browser

## 8. Suggested Git commit history (not one giant commit!)
1. `chore: create Maven project with Selenium, TestNG and Extent dependencies`
2. `feat: add config.properties, ConfigReader and WaitUtils`
3. `feat: add BaseTest with browser setup and teardown`
4. `feat: add LoginPage and login tests`
5. `feat: add ProductsPage and product listing/sorting tests`
6. `feat: add ProductDetailsPage and details tests`
7. `feat: add CartPage and cart tests`
8. `feat: add checkout pages and checkout tests`
9. `feat: add SideMenu and navigation tests`
10. `feat: add end-to-end test`
11. `feat: add TestListener, Extent report and failure screenshots`
12. `feat: add testng.xml with smoke and regression groups`
13. `docs: add README`

Tip: build the project step by step and commit after each step - it also helps you learn it.

## 9. Interview questions you should be ready for
* **Why POM?** Less duplication, one place to fix locators, readable tests.
* **Why explicit waits, not Thread.sleep?** Faster and more stable.
* **Which locator do you prefer?** id first, then CSS, XPath when needed; avoid long absolute XPath.
* **How does a screenshot get taken on failure?** `ITestListener.onTestFailure` -> `ScreenshotUtil.capture`.
* **How do you run only smoke tests?** Include only the `smoke` group in `testng.xml`.
* **How do you change browser without editing code?** `-Dbrowser=firefox` or `config.properties`.
* **What does `alwaysRun = true` do?** The browser is closed even if something failed.
* **What would you add next?** Parallel runs (ThreadLocal driver), data-driven tests, CI with GitHub Actions, logging (Log4j).

## 10. Next steps for you
1. Run the suite and open the Extent report.
2. Break a locator on purpose, watch the test fail, and look at the screenshot.
3. Add your own test (e.g. `problem_user`) following the same pattern.
4. Re-type a few classes yourself - you will be asked to explain every line.
