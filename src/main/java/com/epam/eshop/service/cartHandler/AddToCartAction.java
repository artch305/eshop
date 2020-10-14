package com.epam.eshop.service.cartHandler;


import com.epam.eshop.dao.BaseProductDAO;
import com.epam.eshop.dao.ConnectionManager;
import com.epam.eshop.entity.Cart;
import com.epam.eshop.entity.Product;
import com.epam.eshop.entity.User;
import com.epam.eshop.service.CartService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by artch on 05.10.2020.
 */
class AddToCartAction implements CartActionHandler {

    @Override
    public boolean execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("currentUser");
        Cart currentUserCart = (Cart) request.getSession().getAttribute("currentUserCart");
        int productId = Integer.parseInt(request.getParameter("productId"));
        Map<Product, Integer> productsInCart = currentUserCart.getProductsInCart();
        boolean success;

        BaseProductDAO productDAO = new BaseProductDAO();
        Product product = null;


        try (Connection connection = ConnectionManager.getInstance().getConnection()) {
            product = productDAO.getProductById(connection, String.valueOf(productId));
        } catch (SQLException e) {
            e.printStackTrace();
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
