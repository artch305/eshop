package com.epam.eshop.dao;

import com.epam.eshop.entity.Cart;
import com.epam.eshop.entity.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.*;

/**
 * Created by artch on 15.10.2020.
 */
public class CartDAOTest extends AbstractTest {

    private static CartDAO cartDAO;

    @BeforeClass
    public static void initCartDAOTest() throws Exception {
        cartDAO = new CartDAO();
    }

    @Test
    public void testAddProduct() throws Exception {
        // PREDICATE
        int userId = 2;
        int productId = 3;
        // FUNCTIONALITY
        cartDAO.addProduct(connection, userId, productId);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select product_id from cart where user_id = " + userId);
        int actualProductId = -1;

        if (resultSet.next()) {
            actualProductId = resultSet.getInt(1);
        }
        // TESTS
        assertEquals(productId, actualProductId);

    }

    @Test
    public void testFillUserCart() throws Exception {
        // PREDICATE
        UserDAO userDAO = new UserDAO();
        int productId1 = 1;
        int productId2 = 2;
        User user = userDAO.getUserById(connection, 3);
        Cart userCart = new Cart();
        cartDAO.addProduct(connection, user.getId(), productId1);
        cartDAO.addProduct(connection, user.getId(), productId2);
        // FUNCTIONALITY
        cartDAO.fillUserCart(connection, user, userCart);
        // TESTS
        assertFalse(userCart.getProductsInCart().isEmpty());
        assertEquals(2, userCart.getProductsInCart().size());
    }

    @Test
    public void testSetAmount() throws Exception {
        // PREDICATE
        int userId = 2;
        int productId = 4;
        int amount = 3;
        cartDAO.addProduct(connection, userId, productId);
        // FUNCTIONALITY
        cartDAO.setAmount(connection, userId, productId, amount);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select amount from cart where user_id = " + userId +
                " and product_id = " + productId);
        int actualProductAmount = -1;

        if (resultSet.next()) {
            actualProductAmount = resultSet.getInt(1);
        }

        // TESTS
        assertEquals(amount, actualProductAmount);
    }


    @Test
    public void testRemoveProduct() throws Exception {
        // PREDICATE
        int userId = 2;
        int productId = 5;
        cartDAO.addProduct(connection, userId, productId);
        int expectedAmount = 0;
        // FUNCTIONALITY
        cartDAO.removeProduct(connection, userId, productId);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select amount from cart where user_id = " + userId +
                " and product_id = " + productId);
        int actualProductAmount = 0;

        if (resultSet.next()) {
            actualProductAmount = resultSet.getInt(1);
        }
        // TESTS
        assertEquals(expectedAmount, actualProductAmount);
    }

    @Test
    public void testRemoveAllProductsByUser() throws Exception {
        // PREDICATE
        int userId = 2;
        int productId1 = 6;
        int productId2 = 7;
        int expectedCount = 0;
        cartDAO.addProduct(connection, userId, productId1);
        cartDAO.addProduct(connection, userId, productId2);
        // FUNCTIONALITY
        cartDAO.removeAllProductsByUser(connection, userId);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select count(product_id) from cart where user_id = " + userId
                + " group by product_id");
        int productsCount = 0;

        if (resultSet.next()) {
            productsCount = resultSet.getInt(1);
        }
        // TESTS
        assertEquals(expectedCount, productsCount);
    }
}