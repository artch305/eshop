package com.epam.eshop.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by artch on 05.10.2020.
 */
public class Cart {

    private final Map<Product, Integer> productsInCart = new HashMap<>(); // TODO: 14.10.2020 manage modification of collection state within the class. e.g., create mmethods clear, add, remove and do not expose modifiable collection outside
    private int userId;

    public int getAllAmountProductsInCart() {
        int totalAmount = 0;

        for (Map.Entry<Product, Integer> productEntry : productsInCart.entrySet()) {
            totalAmount+= productEntry.getValue();
        }

        return totalAmount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Map<Product, Integer> getProductsInCart() {
        return productsInCart; // TODO: 14.10.2020 use Collections.unmodifiableMap()
    }

    public double getTotalPrice() {
        double totalPrice = 0.0;
        
        for (Map.Entry<Product, Integer> productEntry : productsInCart.entrySet()) {
            Integer amount = productEntry.getValue();
            totalPrice += productEntry.getKey().getPrice() * amount;
        }

        return totalPrice;
    }

}
