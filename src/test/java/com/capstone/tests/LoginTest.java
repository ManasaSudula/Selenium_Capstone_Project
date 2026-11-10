package com.capstone.tests;

import com.capstone.base.BaseTest;
import com.capstone.pages.LoginPage;
import com.capstone.pages.ProductsPage;
import com.capstone.pages.SideMenu;
import com.capstone.utils.ConfigReader;
import com.capstone.utils.TestData;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(groups = "smoke", description = "Valid user can log in and lands on the Products page")
    public void validLoginShouldOpenProductsPage() {
        ProductsPage productsPage = loginAsStandardUser();

        Assert.assertEquals(productsPage.getPageTitle(), TestData.PRODUCTS_TITLE, "Page title after login");
        Assert.assertTrue(productsPage.getCurrentUrl().contains("inventory.html"), "URL should contain inventory.html");
    }

    @Test(groups = "regression", description = "Wrong password shows an error")
    public void invalidPasswordShouldShowErrorMessage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("standard.username"), "wrong_password");

        Assert.assertTrue(loginPage.getErrorMessage().contains(TestData.INVALID_CREDENTIALS_ERROR),
                "Error message for invalid password");
    }

    @Test(groups = "regression", description = "Empty username and password shows 'Username is required'")
    public void emptyFieldsShouldShowUsernameRequiredError() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("", "");

        Assert.assertTrue(loginPage.getErrorMessage().contains(TestData.USERNAME_REQUIRED_ERROR),
                "Error message for empty fields");
    }

    @Test(groups = "regression", description = "Username without password shows 'Password is required'")
    public void emptyPasswordShouldShowPasswordRequiredError() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("standard.username"), "");

        Assert.assertTrue(loginPage.getErrorMessage().contains(TestData.PASSWORD_REQUIRED_ERROR),
                "Error message for empty password");
    }

    @Test(groups = "regression", description = "Locked-out user cannot log in")
    public void lockedOutUserShouldSeeLockedOutMessage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("locked.username"), ConfigReader.get("password"));

        Assert.assertTrue(loginPage.getErrorMessage().contains(TestData.LOCKED_OUT_ERROR),
                "Error message for locked-out user");
    }

    @Test(groups = "regression", description = "After logout, the browser Back button must not reopen the app")
    public void backButtonAfterLogoutShouldNotGiveAccessToProducts() {
        loginAsStandardUser().getPageTitle(); // wait until logged in
        SideMenu menu = new SideMenu(driver);
        menu.open();
        LoginPage loginPage = menu.clickLogout();

        loginPage.navigateBack();

        Assert.assertTrue(loginPage.getErrorMessage().contains(TestData.NOT_LOGGED_IN_ERROR),
                "App should ask the user to log in again");
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button should be visible");
    }
}
