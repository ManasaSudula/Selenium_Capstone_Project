package com.capstone.utils;

/** Fixed test data and expected messages, kept in one place so tests stay readable. */
public final class TestData {

    private TestData() {
    }

    // Products
    public static final String BACKPACK = "Sauce Labs Backpack";
    public static final String BIKE_LIGHT = "Sauce Labs Bike Light";
    public static final String BOLT_TSHIRT = "Sauce Labs Bolt T-Shirt";
    public static final int TOTAL_PRODUCTS = 6;

    // Page titles
    public static final String PRODUCTS_TITLE = "Products";
    public static final String CART_TITLE = "Your Cart";
    public static final String CHECKOUT_INFO_TITLE = "Checkout: Your Information";
    public static final String CHECKOUT_OVERVIEW_TITLE = "Checkout: Overview";

    // Login messages
    public static final String LOCKED_OUT_ERROR = "Sorry, this user has been locked out.";
    public static final String INVALID_CREDENTIALS_ERROR = "Username and password do not match any user in this service";
    public static final String USERNAME_REQUIRED_ERROR = "Username is required";
    public static final String PASSWORD_REQUIRED_ERROR = "Password is required";
    public static final String NOT_LOGGED_IN_ERROR = "when you are logged in";

    // Checkout
    public static final String CHECKOUT_FIRST_NAME = "Asha";
    public static final String CHECKOUT_LAST_NAME = "Reddy";
    public static final String CHECKOUT_POSTAL_CODE = "515001";
    public static final String FIRST_NAME_REQUIRED_ERROR = "First Name is required";
    public static final String LAST_NAME_REQUIRED_ERROR = "Last Name is required";
    public static final String POSTAL_CODE_REQUIRED_ERROR = "Postal Code is required";
    public static final String ORDER_CONFIRMATION_MESSAGE = "Thank you for your order!";

    // Navigation
    public static final String ABOUT_URL_PART = "saucelabs.com";
}
