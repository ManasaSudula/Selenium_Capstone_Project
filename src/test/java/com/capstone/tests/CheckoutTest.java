package com.capstone.tests;

import com.capstone.base.BaseTest;
import com.capstone.pages.CheckoutCompletePage;
import com.capstone.pages.CheckoutInformationPage;
import com.capstone.pages.CheckoutOverviewPage;
import com.capstone.pages.ProductsPage;
import com.capstone.utils.TestData;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CheckoutTest extends BaseTest {

    @Test(groups = "smoke", description = "Complete checkout with valid details")
    public void completeCheckoutShouldShowConfirmation() {
        CheckoutInformationPage infoPage = startCheckoutWith(TestData.BACKPACK);
        infoPage.fillInformation(TestData.CHECKOUT_FIRST_NAME, TestData.CHECKOUT_LAST_NAME, TestData.CHECKOUT_POSTAL_CODE);
        CheckoutOverviewPage overviewPage = infoPage.clickContinue();

        CheckoutCompletePage completePage = overviewPage.clickFinish();

        Assert.assertEquals(completePage.getConfirmationMessage(), TestData.ORDER_CONFIRMATION_MESSAGE,
                "Order confirmation message");
    }

    @Test(groups = "regression", description = "Submitting an empty form shows 'First Name is required'")
    public void emptyFormShouldShowFirstNameRequiredError() {
        CheckoutInformationPage infoPage = startCheckoutWith(TestData.BACKPACK);
        infoPage.fillInformation("", "", "");
        infoPage.clickContinue();

        Assert.assertTrue(infoPage.getErrorMessage().contains(TestData.FIRST_NAME_REQUIRED_ERROR),
                "Error message for empty form");
        Assert.assertEquals(infoPage.getPageTitle(), TestData.CHECKOUT_INFO_TITLE, "User stays on the information page");
    }

    @Test(groups = "regression", description = "Missing last name shows 'Last Name is required'")
    public void missingLastNameShouldShowError() {
        CheckoutInformationPage infoPage = startCheckoutWith(TestData.BACKPACK);
        infoPage.fillInformation(TestData.CHECKOUT_FIRST_NAME, "", TestData.CHECKOUT_POSTAL_CODE);
        infoPage.clickContinue();

        Assert.assertTrue(infoPage.getErrorMessage().contains(TestData.LAST_NAME_REQUIRED_ERROR),
                "Error message for missing last name");
    }

    @Test(groups = "regression", description = "Missing postal code shows 'Postal Code is required'")
    public void missingPostalCodeShouldShowError() {
        CheckoutInformationPage infoPage = startCheckoutWith(TestData.BACKPACK);
        infoPage.fillInformation(TestData.CHECKOUT_FIRST_NAME, TestData.CHECKOUT_LAST_NAME, "");
        infoPage.clickContinue();

        Assert.assertTrue(infoPage.getErrorMessage().contains(TestData.POSTAL_CODE_REQUIRED_ERROR),
                "Error message for missing postal code");
    }

    @Test(groups = "regression", description = "Order summary lists the items that were added")
    public void orderSummaryShouldListSelectedItems() {
        CheckoutInformationPage infoPage = startCheckoutWith(TestData.BACKPACK, TestData.BIKE_LIGHT);
        infoPage.fillInformation(TestData.CHECKOUT_FIRST_NAME, TestData.CHECKOUT_LAST_NAME, TestData.CHECKOUT_POSTAL_CODE);

        CheckoutOverviewPage overviewPage = infoPage.clickContinue();

        Assert.assertEquals(overviewPage.getPageTitle(), TestData.CHECKOUT_OVERVIEW_TITLE, "Page title");
        Assert.assertEqualsNoOrder(overviewPage.getItemNames().toArray(),
                new String[]{TestData.BACKPACK, TestData.BIKE_LIGHT}, "Items in order summary");
    }

    @Test(groups = "regression", description = "Item total, tax and final total are calculated correctly")
    public void priceTotalShouldBeCalculatedCorrectly() {
        CheckoutInformationPage infoPage = startCheckoutWith(TestData.BACKPACK, TestData.BIKE_LIGHT, TestData.BOLT_TSHIRT);
        infoPage.fillInformation(TestData.CHECKOUT_FIRST_NAME, TestData.CHECKOUT_LAST_NAME, TestData.CHECKOUT_POSTAL_CODE);

        CheckoutOverviewPage overviewPage = infoPage.clickContinue();

        double sumOfItemPrices = overviewPage.getItemPrices().stream().mapToDouble(Double::doubleValue).sum();
        Assert.assertEquals(overviewPage.getItemTotal(), sumOfItemPrices, 0.01, "Item total = sum of item prices");
        Assert.assertEquals(overviewPage.getTotal(), overviewPage.getItemTotal() + overviewPage.getTax(), 0.01,
                "Total = item total + tax");
    }

    /** Logs in, adds the given products, opens the cart and clicks Checkout. */
    private CheckoutInformationPage startCheckoutWith(String... productNames) {
        ProductsPage productsPage = loginAsStandardUser();
        for (String productName : productNames) {
            productsPage.addToCart(productName);
        }
        return productsPage.openCart().clickCheckout();
    }
}
