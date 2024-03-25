package com.api.store.utils.errors;

public class DiscountCalculator {
    public static float calculate(float price, int quantity) {
        if (quantity < 10) return price;
        int discountPercent = (quantity / 10) * 5;
        return price - (price * discountPercent / 100);
    }
}
