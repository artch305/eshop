package com.epam.eshop.service;

import com.epam.eshop.exception.DBException;
import com.epam.eshop.dao.CartDAO;
import com.epam.eshop.dao.ConnectionManager;
import com.epam.eshop.entity.Cart;
import com.epam.eshop.entity.Product;
import com.epam.eshop.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by artch on 05.10.2020.
 */
public class CartService {

    private CartDAO cartDAO;

    public CartService() {
        cartDAO = new CartDAO();
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class);

    public boolean addProduct(User user, Product product) {
        try (Connection connection = ConnectionManager.getInstance().getConnection()) {
            cartDAO.addProduct(connection, user.getId(), product.getId());
            return true;
        } catch (SQLException e) {
            LOGGER.error("Can't add product into user |{}| car", user.getLogin(), e);
            throw new DBException("Can't add product to user cart", e);
        }
    }

    public void fillCurrentUserCart(User user, Cart currentUserCart) {
        currentUserCart.setUserId(user.getId());

        try (Connection connection = ConnectionManager.getInstance().getConnection()) {
            cartDAO.fillUserCart(connection, user, currentUserCart);
        } catch (SQLException e) {
            LOGGER.error("Can't fill current user |{}| cart", user.getLogin(), e);
            throw new DBException("Can't fill user cart", e);
        }
    }

    public boolean setAmount(User user, Product product, int amount) {
        try (Connection connection = ConnectionManager.getInstance().getConnection()) {
            cartDAO.setAmount(connection, user.getId(), product.getId(), amount);
            return true;
        } catch (SQLException e) {
            LOGGER.error("Can't set product |{}| amount |{}| into user |{}| cart",
                    product.getProducer() + product.getName(), amount, user.getLogin(), e);
            throw new DBException("Can't set amount product in user cart", e);
        }
    }

    public boolean removeProduct(User user, Product product) {
        try (Connection connection = ConnectionManager.getInstance().getConnection()) {
            cartDAO.removeProduct(connection, user.getId(), product.getId());
            return true;
        } catch (SQLException e) {
            LOGGER.error("Can't remove product |{}| from user |{}| cart", product.getProducer() +
                    product.getName(), user.getLogin(), e);
            throw new DBException("Can't remove product from user cart", e);
        }
    }

}
