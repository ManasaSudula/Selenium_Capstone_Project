package com.capstone.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutCompletePage extends SecuredPage {

    private final By confirmationHeader = By.cssSelector(".complete-header");
    private final By backHomeButton = By.id("back-to-products");

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    public String getConfirmationMessage() {
        return getText(confirmationHeader);
    }

    public ProductsPage clickBackHome() {
        click(backHomeButton);
        return new ProductsPage(driver);
    }
}
