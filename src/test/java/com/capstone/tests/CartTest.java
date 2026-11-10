package com.capstone.tests;

import com.capstone.base.BaseTest;
import com.capstone.pages.CartPage;
import com.capstone.pages.ProductsPage;
import com.capstone.utils.TestData;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CartTest extends BaseTest {

    @Test(groups = "smoke", description = "Add one item and see it in the cart")
    public void addSingleItemShouldShowItemInCart() {
        ProductsPage productsPage = loginAsStandardUser();
        productsPage.addToCart(TestData.BACKPACK);

        Assert.assertEquals(productsPage.getCartItemCount(), 1, "Cart badge count");
        CartPage cartPage = productsPage.openCart();
        Assert.assertEquals(cartPage.getItemNames(), List.of(TestData.BACKPACK), "Items in cart");
    }

    @Test(groups = "regression", description = "Add three items and see all of them in the cart")
    public void addMultipleItemsShouldShowAllItemsInCart() {
        ProductsPage productsPage = loginAsStandardUser();
        productsPage.addToCart(TestData.BACKPACK);
        productsPage.addToCart(TestData.BIKE_LIGHT);
        productsPage.addToCart(TestData.BOLT_TSHIRT);

        Assert.assertEquals(productsPage.getCartItemCount(), 3, "Cart badge count");
        List<String> cartItems = productsPage.openCart().getItemNames();
        Assert.assertEqualsNoOrder(cartItems.toArray(),
                new String[]{TestData.BACKPACK, TestData.BIKE_LIGHT, TestData.BOLT_TSHIRT}, "Items in cart");
    }

    @Test(groups = "regression", description = "Remove one of two items from the cart")
    public void removeItemShouldLeaveOnlyTheOtherItem() {
        ProductsPage productsPage = loginAsStandardUser();
        productsPage.addToCart(TestData.BACKPACK);
        productsPage.addToCart(TestData.BIKE_LIGHT);
        CartPage cartPage = productsPage.openCart();

        cartPage.removeItem(TestData.BACKPACK);

        Assert.assertEquals(cartPage.getItemNames(), List.of(TestData.BIKE_LIGHT), "Items left in cart");
        Assert.assertEquals(cartPage.getCartItemCount(), 1, "Cart badge count");
    }

    @Test(groups = "regression", description = "Cart shows the same price as the listing and quantity 1")
    public void cartItemDetailsShouldMatchListing() {
        ProductsPage productsPage = loginAsStandardUser();
        String listingPrice = productsPage.getProductPrice(TestData.BACKPACK);
        productsPage.addToCart(TestData.BACKPACK);

        CartPage cartPage = productsPage.openCart();

        Assert.assertEquals(cartPage.getItemPrice(TestData.BACKPACK), listingPrice, "Price in cart");
        Assert.assertEquals(cartPage.getItemQuantity(TestData.BACKPACK), "1", "Quantity in cart");
    }

    @Test(groups = "regression", description = "Continue Shopping returns to the Products page")
    public void continueShoppingShouldReturnToProducts() {
        CartPage cartPage = loginAsStandardUser().openCart();

        ProductsPage productsPage = cartPage.clickContinueShopping();

        Assert.assertEquals(productsPage.getPageTitle(), TestData.PRODUCTS_TITLE, "Page title");
    }

    @Test(groups = "regression", description = "A new cart is empty")
    public void emptyCartShouldContainNoItems() {
        CartPage cartPage = loginAsStandardUser().openCart();

        Assert.assertEquals(cartPage.getPageTitle(), TestData.CART_TITLE, "Page title");
        Assert.assertTrue(cartPage.getItemNames().isEmpty(), "Cart should have no items");
        Assert.assertEquals(cartPage.getCartItemCount(), 0, "Cart badge count");
    }
}
