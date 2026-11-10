package com.capstone.pages;

import com.capstone.utils.PriceUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.stream.Collectors;

public class CheckoutOverviewPage extends SecuredPage {

    private final By itemNames = By.cssSelector(".cart_item .inventory_item_name");
    private final By itemPrices = By.cssSelector(".cart_item .inventory_item_price");
    private final By itemTotalLabel = By.cssSelector(".summary_subtotal_label");
    private final By taxLabel = By.cssSelector(".summary_tax_label");
    private final By totalLabel = By.cssSelector(".summary_total_label");
    private final By finishButton = By.id("finish");

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getItemNames() {
        return getTexts(itemNames);
    }

    public List<Double> getItemPrices() {
        return getTexts(itemPrices).stream()
                .map(PriceUtils::parse)
                .collect(Collectors.toList());
    }

    public double getItemTotal() {
        return PriceUtils.parse(getText(itemTotalLabel));
    }

    public double getTax() {
        return PriceUtils.parse(getText(taxLabel));
    }

    public double getTotal() {
        return PriceUtils.parse(getText(totalLabel));
    }

    public CheckoutCompletePage clickFinish() {
        click(finishButton);
        return new CheckoutCompletePage(driver);
    }
}
