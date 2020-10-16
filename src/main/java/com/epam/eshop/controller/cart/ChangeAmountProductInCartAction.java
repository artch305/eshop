package com.epam.eshop.controller.cart;

import com.epam.eshop.controller.Util;
import com.epam.eshop.controller.constants.ParameterNames;
import com.epam.eshop.entity.Cart;
import com.epam.eshop.entity.Product;
import com.epam.eshop.entity.User;
import com.epam.eshop.exception.DBException;
import com.epam.eshop.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Implements interface {@link CartActionHandler} for changing amount {@link Product} in {@link Cart}
 * Created by artch on 05.10.2020.
 */
class ChangeAmountProductInCartAction implements CartActionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeAmountProductInCartAction.class);

    /**
     * main method for changing amount {@link Product} in {@link Cart}
     *
     * @param request {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet. Should contains params: "currentUser",
     *                "productId", "currentUserCart", "amount"
     * @return true when execution has been success
     * @throws DBException throw this unchecked exception if something happened
     *                     wrong in DAO level
     */
    @Override
    public boolean execute(HttpServletRequest request) {
        User user = Util.getUserFromSession(request.getSession());
        Cart currentUserCart = Util.getCartFromSession(request.getSession());
        int productId = Integer.parseInt(request.getParameter(ParameterNames.PRODUCT_ID));
        int amount = Integer.parseInt(request.getParameter(ParameterNames.PRODUCT_AMOUNT));

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
        LOGGER.info("Amount of product has been changed");
        return success;
    }
}
