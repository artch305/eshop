package com.epam.eshop.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by artch on 05.10.2020.
 */
public class Cart {

    private int userId;
    private Map<Product, Integer> productsInCart = new HashMap<>();

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Map<Product, Integer> getProductsInCart() {
        return productsInCart;
    }

    public double getTotalPrice() {
        double totalPrice = 0.0;
        for (Map.Entry<Product, Integer> productEntry : productsInCart.entrySet()) {
            totalPrice += productEntry.getKey().getPrice() * productEntry.getValue();
        }

        return totalPrice;
    }

}
