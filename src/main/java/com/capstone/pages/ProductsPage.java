package com.capstone.pages;

import com.capstone.utils.PriceUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class ProductsPage extends SecuredPage {

    /** The four options of the sort dropdown, with the "value" attribute used by the page. */
    public enum SortOption {
        NAME_A_TO_Z("az"),
        NAME_Z_TO_A("za"),
        PRICE_LOW_TO_HIGH("lohi"),
        PRICE_HIGH_TO_LOW("hilo");

        private final String value;

        SortOption(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private final By inventoryItems = By.cssSelector(".inventory_item");
    private final By itemNames = By.cssSelector(".inventory_item_name");
    private final By itemPrices = By.cssSelector(".inventory_item_price");
    private final By sortDropdown = By.cssSelector("[data-test='product-sort-container']");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    // ---------- listing ----------

    public int getProductCount() {
        return waits.waitForAllVisible(inventoryItems).size();
    }

    public List<String> getProductNames() {
        return getTexts(itemNames);
    }

    public List<Double> getProductPrices() {
        return getTexts(itemPrices).stream()
                .map(PriceUtils::parse)
                .collect(Collectors.toList());
    }

    public void sortBy(SortOption option) {
        new Select(waits.waitForVisible(sortDropdown)).selectByValue(option.getValue());
    }

    // ---------- one product (found by its name) ----------

    public String getProductPrice(String productName) {
        return getText(itemLocator(productName, "//div[@class='inventory_item_price']"));
    }

    public String getProductDescription(String productName) {
        return getText(itemLocator(productName, "//div[contains(@class,'inventory_item_desc')]"));
    }

    public void addToCart(String productName) {
        click(itemLocator(productName, "//button[normalize-space()='Add to cart']"));
        // Wait for the button to flip to "Remove" so the cart badge is up to date
        waits.waitForVisible(itemLocator(productName, "//button[normalize-space()='Remove']"));
    }

    public void removeFromCart(String productName) {
        click(itemLocator(productName, "//button[normalize-space()='Remove']"));
        waits.waitForVisible(itemLocator(productName, "//button[normalize-space()='Add to cart']"));
    }

    public boolean isRemoveButtonDisplayed(String productName) {
        return isDisplayed(itemLocator(productName, "//button[normalize-space()='Remove']"));
    }

    public ProductDetailsPage openProductDetails(String productName) {
        click(By.xpath(productNameXpath(productName)));
        return new ProductDetailsPage(driver);
    }

    // ---------- locator helpers ----------

    private String productNameXpath(String productName) {
        return "//div[contains(@class,'inventory_item_name') and normalize-space()='" + productName + "']";
    }

    /** Finds something INSIDE the product card that has the given name. */
    private By itemLocator(String productName, String pathInsideCard) {
        return By.xpath(productNameXpath(productName) + "/ancestor::div[@class='inventory_item']" + pathInsideCard);
    }
}
