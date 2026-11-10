package com.capstone.tests;

import com.capstone.base.BaseTest;
import com.capstone.pages.LoginPage;
import com.capstone.pages.ProductsPage;
import com.capstone.pages.SideMenu;
import com.capstone.utils.TestData;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NavigationTest extends BaseTest {

    @Test(groups = "smoke", description = "Sidebar opens and shows its four options")
    public void sidebarShouldOpenWithAllMenuOptions() {
        loginAsStandardUser().getPageTitle();
        SideMenu menu = new SideMenu(driver);

        menu.open();

        Assert.assertTrue(menu.isOpen(), "Sidebar should be open");
        List<String> options = menu.getMenuItemTexts().stream().map(String::toLowerCase).collect(Collectors.toList());
        Assert.assertEquals(options, Arrays.asList("all items", "about", "logout", "reset app state"),
                "Sidebar options");
    }

    @Test(groups = "regression", description = "Sidebar closes with the X button")
    public void sidebarShouldCloseWhenCloseButtonIsClicked() {
        loginAsStandardUser().getPageTitle();
        SideMenu menu = new SideMenu(driver);
        menu.open();

        menu.close();

        Assert.assertTrue(menu.isClosed(), "Sidebar should be closed");
    }

    @Test(groups = "regression", description = "All Items link goes back to the Products page")
    public void allItemsLinkShouldOpenProductsPage() {
        loginAsStandardUser().openCart();
        SideMenu menu = new SideMenu(driver);
        menu.open();

        ProductsPage productsPage = menu.clickAllItems();

        Assert.assertEquals(productsPage.getPageTitle(), TestData.PRODUCTS_TITLE, "Page title");
        Assert.assertTrue(productsPage.getCurrentUrl().contains("inventory.html"), "URL should be the products page");
    }

    @Test(groups = "regression", description = "About link opens the Sauce Labs website")
    public void aboutLinkShouldOpenSauceLabsWebsite() {
        loginAsStandardUser().getPageTitle();
        SideMenu menu = new SideMenu(driver);
        menu.open();

        menu.clickAbout();

        menu.waitForUrlToContain(TestData.ABOUT_URL_PART);
        Assert.assertTrue(driver.getCurrentUrl().contains(TestData.ABOUT_URL_PART),
                "About should open the Sauce Labs site");
    }

    @Test(groups = "regression", description = "Logout returns to the login page")
    public void logoutLinkShouldReturnToLoginPage() {
        loginAsStandardUser().getPageTitle();
        SideMenu menu = new SideMenu(driver);
        menu.open();

        LoginPage loginPage = menu.clickLogout();

        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button should be visible after logout");
    }

    @Test(groups = "regression", description = "Reset App State empties the cart")
    public void resetAppStateShouldEmptyTheCart() {
        ProductsPage productsPage = loginAsStandardUser();
        productsPage.addToCart(TestData.BACKPACK);
        productsPage.addToCart(TestData.BIKE_LIGHT);
        Assert.assertEquals(productsPage.getCartItemCount(), 2, "Cart count before reset");
        SideMenu menu = new SideMenu(driver);
        menu.open();

        menu.clickResetAppState();

        Assert.assertTrue(productsPage.isCartBadgeGone(), "Cart badge should disappear after reset");
    }
}
