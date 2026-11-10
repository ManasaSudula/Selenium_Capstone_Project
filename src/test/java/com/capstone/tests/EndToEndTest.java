package com.capstone.tests;

import com.capstone.base.BaseTest;
import com.capstone.pages.CartPage;
import com.capstone.pages.CheckoutCompletePage;
import com.capstone.pages.CheckoutInformationPage;
import com.capstone.pages.CheckoutOverviewPage;
import com.capstone.pages.ProductsPage;
import com.capstone.utils.TestData;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class EndToEndTest extends BaseTest {

    @Test(groups = "smoke", description = "Login, add items, checkout and verify the order confirmation")
    public void userShouldBeAbleToBuyItemsEndToEnd() {
        // 1. Login
        ProductsPage productsPage = loginAsStandardUser();
        Assert.assertEquals(productsPage.getPageTitle(), TestData.PRODUCTS_TITLE, "Logged in");

        // 2. Add items
        productsPage.addToCart(TestData.BACKPACK);
        productsPage.addToCart(TestData.BIKE_LIGHT);
        Assert.assertEquals(productsPage.getCartItemCount(), 2, "Cart badge after adding items");

        // 3. Cart
        CartPage cartPage = productsPage.openCart();
        Assert.assertEqualsNoOrder(cartPage.getItemNames().toArray(),
                new String[]{TestData.BACKPACK, TestData.BIKE_LIGHT}, "Items in cart");

        // 4. Checkout
        CheckoutInformationPage infoPage = cartPage.clickCheckout();
        infoPage.fillInformation(TestData.CHECKOUT_FIRST_NAME, TestData.CHECKOUT_LAST_NAME, TestData.CHECKOUT_POSTAL_CODE);
        CheckoutOverviewPage overviewPage = infoPage.clickContinue();
        List<String> itemsInSummary = overviewPage.getItemNames();
        Assert.assertEquals(itemsInSummary.size(), 2, "Items in order summary");

        // 5. Confirmation
        CheckoutCompletePage completePage = overviewPage.clickFinish();
        Assert.assertEquals(completePage.getConfirmationMessage(), TestData.ORDER_CONFIRMATION_MESSAGE,
                "Order confirmation message");
    }
}
