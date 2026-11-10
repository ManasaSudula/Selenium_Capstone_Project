package com.capstone.tests;

import com.capstone.base.BaseTest;
import com.capstone.pages.ProductsPage;
import com.capstone.pages.ProductsPage.SortOption;
import com.capstone.utils.TestData;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductListingTest extends BaseTest {

    @Test(groups = "smoke", description = "Products page shows all 6 products")
    public void productsPageShouldListAllProducts() {
        ProductsPage productsPage = loginAsStandardUser();

        Assert.assertEquals(productsPage.getProductCount(), TestData.TOTAL_PRODUCTS, "Number of products");
    }

    @Test(groups = "regression", description = "Default sort order is Name (A to Z)")
    public void defaultSortShouldBeNameAToZ() {
        ProductsPage productsPage = loginAsStandardUser();

        List<String> actual = productsPage.getProductNames();
        List<String> expected = new ArrayList<>(actual);
        expected.sort(String.CASE_INSENSITIVE_ORDER);

        Assert.assertEquals(actual, expected, "Default order should be A to Z");
    }

    @Test(groups = "regression", description = "Sort by name A to Z")
    public void sortByNameAToZShouldOrderNamesAlphabetically() {
        ProductsPage productsPage = loginAsStandardUser();
        productsPage.sortBy(SortOption.NAME_Z_TO_A); // change away from default first
        productsPage.sortBy(SortOption.NAME_A_TO_Z);

        List<String> actual = productsPage.getProductNames();
        List<String> expected = new ArrayList<>(actual);
        expected.sort(String.CASE_INSENSITIVE_ORDER);

        Assert.assertEquals(actual, expected, "Names should be A to Z");
    }

    @Test(groups = "regression", description = "Sort by name Z to A")
    public void sortByNameZToAShouldOrderNamesReverseAlphabetically() {
        ProductsPage productsPage = loginAsStandardUser();
        productsPage.sortBy(SortOption.NAME_Z_TO_A);

        List<String> actual = productsPage.getProductNames();
        List<String> expected = new ArrayList<>(actual);
        expected.sort(String.CASE_INSENSITIVE_ORDER.reversed());

        Assert.assertEquals(actual, expected, "Names should be Z to A");
    }

    @Test(groups = "regression", description = "Sort by price low to high")
    public void sortByPriceLowToHighShouldOrderPricesAscending() {
        ProductsPage productsPage = loginAsStandardUser();
        productsPage.sortBy(SortOption.PRICE_LOW_TO_HIGH);

        List<Double> actual = productsPage.getProductPrices();
        List<Double> expected = new ArrayList<>(actual);
        Collections.sort(expected);

        Assert.assertEquals(actual, expected, "Prices should go low to high");
    }

    @Test(groups = "regression", description = "Sort by price high to low")
    public void sortByPriceHighToLowShouldOrderPricesDescending() {
        ProductsPage productsPage = loginAsStandardUser();
        productsPage.sortBy(SortOption.PRICE_HIGH_TO_LOW);

        List<Double> actual = productsPage.getProductPrices();
        List<Double> expected = new ArrayList<>(actual);
        expected.sort(Collections.reverseOrder());

        Assert.assertEquals(actual, expected, "Prices should go high to low");
    }
}
