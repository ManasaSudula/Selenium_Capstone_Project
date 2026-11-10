package com.capstone.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Reusable explicit-wait helpers. The timeout comes from config.properties.
 * An explicit wait keeps checking until the condition is true (or time runs out),
 * so tests are fast when the page is fast and still stable when it is slow.
 */
public class WaitUtils {

    private final WebDriverWait wait;

    public WaitUtils(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("explicit.wait.seconds")));
    }

    public WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public List<WebElement> waitForAllVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public void waitForUrlContains(String urlPart) {
        wait.until(ExpectedConditions.urlContains(urlPart));
    }

    /** Returns true if the element is gone (or hidden) before the timeout, false otherwise. */
    public boolean waitForInvisible(By locator) {
        try {
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            return false;
        }
    }

    /** Returns true if the attribute gets the expected value before the timeout, false otherwise. */
    public boolean waitForAttributeToBe(By locator, String attribute, String expectedValue) {
        try {
            return wait.until(ExpectedConditions.attributeToBe(locator, attribute, expectedValue));
        } catch (TimeoutException e) {
            return false;
        }
    }
}
