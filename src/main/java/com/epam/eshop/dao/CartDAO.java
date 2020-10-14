package com.epam.eshop.dao;

import com.epam.eshop.entity.Cart;
import com.epam.eshop.entity.Product;
import com.epam.eshop.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by artch on 05.10.2020.
 */
public class CartDAO {

    private static final String SQL_ADD_PRODUCT_TO_CART = "insert into cart (user_id, product_id, amount) VALUES (?,?,1)";
    private static final String SQL_GET_PRODUCTS_FOR_CART = "select * from cart join products on cart.product_id = products.id " +
            "where active = 1 and user_id = ?";
    private static final String SQL_SET_AMOUNT = "update cart set amount = ? where user_id = ? and product_id = ?";
    private static final String SQL_REMOVE = "delete from cart where user_id = ? and product_id = ?";
    private static final String SQL_REMOVE_PRODUCTS_BY_USER_ID = "delete from cart where user_id = ?";

    private static final Logger LOGGER = LoggerFactory.getLogger(CartDAO.class);

    public void addProduct(Connection connection, int userId, int productId) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_PRODUCT_TO_CART)) {
            preparedStatement.setString(1, String.valueOf(userId));
            preparedStatement.setString(2, String.valueOf(productId));
            preparedStatement.execute();
        }
        LOGGER.info("Product |{}| in user |{}| cart has been added", productId, userId);
    }


    public void fillUserCart(Connection connection, User user, Cart currentUserCart) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_PRODUCTS_FOR_CART)) {
            preparedStatement.setString(1, String.valueOf(user.getId()));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Product product = new Product();
                BaseProductDAO productDAO = new BaseProductDAO();
                productDAO.fillBaseProduct(resultSet, product);
                currentUserCart.getProductsInCart().put(product, resultSet.getInt(Columns.CART_AMOUNT));
            }
        }
        LOGGER.info("User |{}| cart has been filled", user.getLogin());
    }

    public void setAmount(Connection connection, int userID, int productId, int amount) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SET_AMOUNT)) {
            preparedStatement.setString(1, String.valueOf(amount));
            preparedStatement.setString(2, String.valueOf(userID));
            preparedStatement.setString(3, String.valueOf(productId));
            preparedStatement.execute();
        }
        LOGGER.info("Amount |{}| in user |{}| cart for product |{}| has been set", amount, userID, productId);
    }

    public void removeProduct(Connection connection, int userId, int productId) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_REMOVE)) {
            preparedStatement.setString(1, String.valueOf(userId));
            preparedStatement.setString(2, String.valueOf(productId));
            preparedStatement.execute();
        }
        LOGGER.info("Product |{}| in user |{}| cart has been deleted", productId, userId);
    }

    public void removeAllProductsByUser(Connection connection, int userId) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_REMOVE_PRODUCTS_BY_USER_ID)) {
            preparedStatement.setString(1, String.valueOf(userId));
            preparedStatement.execute();
        }
        LOGGER.info("All products in user |{}| cart has been deleted", userId);
    }
}
