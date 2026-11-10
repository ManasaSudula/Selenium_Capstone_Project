package com.capstone.base;

import com.capstone.pages.LoginPage;
import com.capstone.pages.ProductsPage;
import com.capstone.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Every test class extends this one.
 * Before each test: open a fresh browser on the login page.
 * After each test: close the browser (even if the test failed).
 */
public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = createDriver(ConfigReader.get("browser"));
        driver.manage().window().maximize();
        driver.get(ConfigReader.get("base.url"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /** Used by TestListener to take a screenshot when a test fails. */
    public WebDriver getDriver() {
        return driver;
    }

    /** Logs in with the standard user from config.properties. */
    protected ProductsPage loginAsStandardUser() {
        new LoginPage(driver).login(ConfigReader.get("standard.username"), ConfigReader.get("password"));
        return new ProductsPage(driver);
    }

    private WebDriver createDriver(String browser) {
        boolean headless = ConfigReader.getBoolean("headless");

        switch (browser.toLowerCase()) {
            case "chrome": {
                ChromeOptions options = new ChromeOptions();
                // Stops Chrome's "Change your password" pop-up from blocking the page
                Map<String, Object> preferences = new HashMap<>();
                preferences.put("credentials_enable_service", false);
                preferences.put("profile.password_manager_enabled", false);
                preferences.put("profile.password_manager_leak_detection", false);
                options.setExperimentalOption("prefs", preferences);
                if (headless) {
                    options.addArguments("--headless=new", "--window-size=1920,1080");
                }
                return new ChromeDriver(options);
            }
            case "firefox": {
                FirefoxOptions options = new FirefoxOptions();
                if (headless) {
                    options.addArguments("-headless");
                }
                return new FirefoxDriver(options);
            }
            case "edge": {
                EdgeOptions options = new EdgeOptions();
                if (headless) {
                    options.addArguments("--headless=new", "--window-size=1920,1080");
                }
                return new EdgeDriver(options);
            }
            default:
                throw new IllegalArgumentException("Unsupported browser in config.properties: " + browser);
        }
    }
}
