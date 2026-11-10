package com.capstone.utils;

/** Turns text such as "$29.99" or "Item total: $39.98" into a number. */
public final class PriceUtils {

    private PriceUtils() {
    }

    public static double parse(String text) {
        return Double.parseDouble(text.replaceAll("[^0-9.]", ""));
    }
}
