package com.capstone.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

/** The hamburger (sidebar) menu that is available on every page after login. */
public class SideMenu extends BasePage {

    private final By menuButton = By.id("react-burger-menu-btn");
    private final By closeButton = By.id("react-burger-cross-btn");
    private final By menuPanel = By.cssSelector(".bm-menu-wrap");
    private final By menuItems = By.cssSelector(".bm-item-list a");
    private final By allItemsLink = By.id("inventory_sidebar_link");
    private final By aboutLink = By.id("about_sidebar_link");
    private final By logoutLink = By.id("logout_sidebar_link");
    private final By resetLink = By.id("reset_sidebar_link");

    public SideMenu(WebDriver driver) {
        super(driver);
    }

    public void open() {
        click(menuButton);
        waits.waitForAttributeToBe(menuPanel, "aria-hidden", "false");
    }

    public void close() {
        click(closeButton);
        waits.waitForAttributeToBe(menuPanel, "aria-hidden", "true");
    }

    public boolean isOpen() {
        return waits.waitForAttributeToBe(menuPanel, "aria-hidden", "false");
    }

    public boolean isClosed() {
        return waits.waitForAttributeToBe(menuPanel, "aria-hidden", "true");
    }

    public List<String> getMenuItemTexts() {
        return getTexts(menuItems);
    }

    public ProductsPage clickAllItems() {
        click(allItemsLink);
        return new ProductsPage(driver);
    }

    /** Opens the external Sauce Labs website. */
    public void clickAbout() {
        click(aboutLink);
    }

    public LoginPage clickLogout() {
        click(logoutLink);
        return new LoginPage(driver);
    }

    public void clickResetAppState() {
        click(resetLink);
    }
}
