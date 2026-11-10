package com.capstone.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends SecuredPage {

    private final By itemNames = By.cssSelector(".cart_item .inventory_item_name");
    private final By continueShoppingButton = By.id("continue-shopping");
    private final By checkoutButton = By.id("checkout");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    /** Names of items in the cart. Returns an empty list for an empty cart. */
    public List<String> getItemNames() {
        getPageTitle(); // wait until the cart page has really loaded
        return driver.findElements(itemNames).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public String getItemPrice(String productName) {
        return getText(cartItem(productName, "//div[@class='inventory_item_price']"));
    }

    public String getItemQuantity(String productName) {
        return getText(cartItem(productName, "//div[@class='cart_quantity']"));
    }

    public void removeItem(String productName) {
        click(cartItem(productName, "//button[normalize-space()='Remove']"));
        waits.waitForInvisible(cartItem(productName, ""));
    }

    public ProductsPage clickContinueShopping() {
        click(continueShoppingButton);
        return new ProductsPage(driver);
    }

    public CheckoutInformationPage clickCheckout() {
        click(checkoutButton);
        return new CheckoutInformationPage(driver);
    }

    private By cartItem(String productName, String pathInsideItem) {
        return By.xpath("//div[contains(@class,'inventory_item_name') and normalize-space()='" + productName
                + "']/ancestor::div[@class='cart_item']" + pathInsideItem);
    }
}
