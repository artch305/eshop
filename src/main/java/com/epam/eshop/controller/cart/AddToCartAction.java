package com.epam.eshop.controller.cart;


import com.epam.eshop.controller.Util;
import com.epam.eshop.controller.constants.ParameterNames;
import com.epam.eshop.dao.BaseProductDAO;
import com.epam.eshop.dao.ConnectionManager;
import com.epam.eshop.entity.Cart;
import com.epam.eshop.entity.Product;
import com.epam.eshop.entity.User;
import com.epam.eshop.exception.DBException;
import com.epam.eshop.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * Implements interface {@link CartActionHandler} for adding {@link Product} to {@link Cart}
 * Created by artch on 05.10.2020.
 */
class AddToCartAction implements CartActionHandler {

    private BaseProductDAO productDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(AddToCartAction.class);

    public AddToCartAction() {
        productDAO = new BaseProductDAO();
    }

    /**
     * main method for adding {@link Product} to {@link Cart}
     *
     * @param request {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet.Should contains params: "currentUser",
     *                "productId", "currentUserCart";
     * @return true when execution has been success
     * @throws DBException throw this unchecked exception if something happened
     *                     wrong in DAO level
     */
    @Override
    public boolean execute(HttpServletRequest request) {
        User user = Util.getUserFromSession(request.getSession());
        Cart currentUserCart = Util.getCartFromSession(request.getSession());
        int productId = Integer.parseInt(request.getParameter(ParameterNames.PRODUCT_ID));
        Map<Product, Integer> productsInCart = currentUserCart.getProductsInCart();
        boolean success;

        Product product;

        try (Connection connection = ConnectionManager.getInstance().getConnection()) {
            product = productDAO.getProductById(connection, String.valueOf(productId));
        } catch (SQLException e) {
            LOGGER.error("Can't add product |{}| to cart", productId, e);
            throw new DBException("Can't add product to cart", e);
        }

        CartService cartService = new CartService();

        if (productsInCart.containsKey(product)) {
            Integer amount = productsInCart.get(product);
            success = cartService.setAmount(user, product, ++amount);
            productsInCart.put(product, amount);
        } else {
            productsInCart.put(product, 1);
            success = cartService.addProduct(user, product);
        }

        return success;
    }
}
