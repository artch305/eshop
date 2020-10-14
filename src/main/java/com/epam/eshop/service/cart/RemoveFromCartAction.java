package com.epam.eshop.service.cart;

import com.epam.eshop.entity.Cart;
import com.epam.eshop.entity.Product;
import com.epam.eshop.entity.User;
import com.epam.eshop.service.CartService;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by artch on 05.10.2020.
 */
class RemoveFromCartAction implements CartActionHandler {

    @Override
    public boolean execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("currentUser");
        Cart currentUserCart = (Cart) request.getSession().getAttribute("currentUserCart");
        int productId = Integer.parseInt(request.getParameter("productId"));

        CartService cartService = new CartService();
        boolean success;

        Product product = currentUserCart.getProductsInCart().keySet()
                .stream()
                .filter(product1 -> product1.getId() == productId)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        success = cartService.removeProduct(user, product);
        currentUserCart.getProductsInCart().remove(product);

        return success;
    }
}
