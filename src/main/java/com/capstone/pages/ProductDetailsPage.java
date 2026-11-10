package com.capstone.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductDetailsPage extends SecuredPage {

    private final By productName = By.cssSelector(".inventory_details_name");
    private final By productDescription = By.cssSelector(".inventory_details_desc");
    private final By productPrice = By.cssSelector(".inventory_details_price");
    private final By addToCartButton = By.xpath("//button[normalize-space()='Add to cart']");
    private final By removeButton = By.xpath("//button[normalize-space()='Remove']");
    private final By backToProductsButton = By.id("back-to-products");

    public ProductDetailsPage(WebDriver driver) {
        super(driver);
    }

    public String getProductName() {
        return getText(productName);
    }

    public String getProductDescription() {
        return getText(productDescription);
    }

    public String getProductPrice() {
        return getText(productPrice);
    }

    public void addToCart() {
        click(addToCartButton);
        waits.waitForVisible(removeButton);
    }

    public boolean isRemoveButtonDisplayed() {
        return isDisplayed(removeButton);
    }

    public ProductsPage backToProducts() {
        click(backToProductsButton);
        return new ProductsPage(driver);
    }
}
