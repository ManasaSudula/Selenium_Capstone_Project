package com.capstone.pages;

import com.capstone.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

/** Parent of every page class: holds the driver and small helpers that always wait first. */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WaitUtils waits;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.waits = new WaitUtils(driver);
    }

    protected void click(By locator) {
        waits.waitForClickable(locator).click();
    }

    protected void type(By locator, String text) {
        WebElement field = waits.waitForVisible(locator);
        field.clear();
        field.sendKeys(text);
    }

    protected String getText(By locator) {
        return waits.waitForVisible(locator).getText();
    }

    protected List<String> getTexts(By locator) {
        return waits.waitForAllVisible(locator).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /** True if the element shows up within the wait time; false if it never does. */
    protected boolean isDisplayed(By locator) {
        try {
            return waits.waitForVisible(locator).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void waitForUrlToContain(String urlPart) {
        waits.waitForUrlContains(urlPart);
    }

    public void navigateBack() {
        driver.navigate().back();
    }
}
