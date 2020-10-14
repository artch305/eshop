package com.epam.eshop.service.cart;

import com.epam.eshop.entity.Cart;
import com.epam.eshop.entity.Product;
import com.epam.eshop.entity.User;
import com.epam.eshop.service.CartService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by artch on 05.10.2020.
 */
class ChangeAmountProductInCartAction implements CartActionHandler {

    @Override
    public boolean execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("currentUser");
        Cart currentUserCart = (Cart) request.getSession().getAttribute("currentUserCart");
        int productId = Integer.parseInt(request.getParameter("productId"));
        int amount = Integer.parseInt(request.getParameter("amount"));

        if (amount < 1) {
            return false;
        }

        boolean success;

        Product product = currentUserCart.getProductsInCart().keySet()
                .stream()
                .filter(product1 -> product1.getId() == productId)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);


        Map<Product, Integer> productsInCart = currentUserCart.getProductsInCart();

        CartService cartService = new CartService();
        success = cartService.setAmount(user, product, amount);

        productsInCart.put(product, amount);

        return success;
    }
}
