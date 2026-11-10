package com.capstone.tests;

import com.capstone.base.BaseTest;
import com.capstone.pages.ProductDetailsPage;
import com.capstone.pages.ProductsPage;
import com.capstone.utils.TestData;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductDetailsTest extends BaseTest {

    @Test(groups = "smoke", description = "Details page shows the same name, price and description as the listing")
    public void detailsPageShouldMatchListing() {
        ProductsPage productsPage = loginAsStandardUser();
        String listingPrice = productsPage.getProductPrice(TestData.BACKPACK);
        String listingDescription = productsPage.getProductDescription(TestData.BACKPACK);

        ProductDetailsPage detailsPage = productsPage.openProductDetails(TestData.BACKPACK);

        Assert.assertEquals(detailsPage.getProductName(), TestData.BACKPACK, "Product name");
        Assert.assertEquals(detailsPage.getProductPrice(), listingPrice, "Product price");
        Assert.assertEquals(detailsPage.getProductDescription(), listingDescription, "Product description");
    }

    @Test(groups = "regression", description = "Add to cart from the details page")
    public void addToCartFromDetailsPageShouldUpdateCart() {
        ProductsPage productsPage = loginAsStandardUser();
        ProductDetailsPage detailsPage = productsPage.openProductDetails(TestData.BIKE_LIGHT);

        detailsPage.addToCart();

        Assert.assertEquals(detailsPage.getCartItemCount(), 1, "Cart badge count");
        Assert.assertTrue(detailsPage.isRemoveButtonDisplayed(), "Button should change to Remove");
    }

    @Test(groups = "regression", description = "Back to products button returns to the listing")
    public void backToProductsShouldReturnToListing() {
        ProductsPage productsPage = loginAsStandardUser();
        ProductDetailsPage detailsPage = productsPage.openProductDetails(TestData.BACKPACK);

        ProductsPage returnedPage = detailsPage.backToProducts();

        Assert.assertEquals(returnedPage.getPageTitle(), TestData.PRODUCTS_TITLE, "Page title");
        Assert.assertEquals(returnedPage.getProductCount(), TestData.TOTAL_PRODUCTS, "Number of products");
    }

    @Test(groups = "regression", description = "Only viewing a product must not add it to the cart")
    public void openingDetailsShouldNotAddItemToCart() {
        ProductsPage productsPage = loginAsStandardUser();
        ProductDetailsPage detailsPage = productsPage.openProductDetails(TestData.BACKPACK);

        Assert.assertEquals(detailsPage.getProductName(), TestData.BACKPACK, "Details page loaded");
        Assert.assertEquals(detailsPage.getCartItemCount(), 0, "Cart should still be empty");
    }
}
