package com.epam.eshop.service;

import com.epam.eshop.exception.DBException;
import com.epam.eshop.dao.CartDAO;
import com.epam.eshop.dao.ConnectionManager;
import com.epam.eshop.dao.OrderDAO;
import com.epam.eshop.entity.Cart;
import com.epam.eshop.entity.Order;
import com.epam.eshop.entity.Product;
import com.epam.eshop.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by artch on 06.10.2020.
 */
public class OrderService {

    private OrderDAO orderDAO;
    private CartDAO cartDAO;
    private ConnectionManager connectionManager;

    public OrderService() {
        this(new OrderDAO(), new CartDAO(), ConnectionManager.getInstance());
    }

    public OrderService(OrderDAO orderDAO, CartDAO cartDAO, ConnectionManager connectionManager) {
        this.orderDAO = orderDAO;
        this.cartDAO = cartDAO;
        this.connectionManager = connectionManager;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    public boolean setOrder(User user, Cart currentUserCart) {
        Connection connection = null;

        try {
            connection = connectionManager.getConnection();
            connection.setAutoCommit(false);

            int orderId = orderDAO.setOrder(connection, user.getId());
            orderDAO.setProductsToOrder(connection, orderId, currentUserCart.getProductsInCart());
            cartDAO.removeAllProductsByUser(connection, user.getId());

            currentUserCart.getProductsInCart().clear();

            connection.commit();
            LOGGER.info("Order for user |{}| has been created", user.getLogin());
            return true;
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Can't set order for user |{}|", user.getLogin(), e);
            throw new DBException("Can't set new order", e);
        } finally {
            if (connection != null) {
                applyAutoCommitAndClose(connection);
            }
        }

    }

    private void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.error("can't rollback transaction ");
            throw new DBException("Can't rollback connection in OrderService", e);
        }
    }

    private void applyAutoCommitAndClose(Connection connection) {
        try {
            connection.setAutoCommit(true);
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("problem in setAutoCommit and closing connection", e);
            throw new DBException("Can't apply autocommit and close connection in OrderService", e);
        }
    }

    public List<Order> getAllOrders() {
        List<Order> orders;

        try (Connection connection = connectionManager.getConnection()) {
            orders = orderDAO.getAllOrders(connection);
        } catch (SQLException e) {
            LOGGER.error("Can't obtain all orders", e);
            throw new DBException("Can't get all orders", e);
        }

        return orders;
    }

    public List<Order> getOrdersByUser(User user) {
        List<Order> orders;

        try (Connection connection = connectionManager.getConnection()) {
            orders = orderDAO.getAllOrdersByUserId(connection, user.getId());

        } catch (SQLException e) {
            LOGGER.error("Can't obtain order by user |{}|", user.getLogin(), e);
            throw new DBException("Can't get all orders by user", e);
        }
        return orders;
    }

    public Order getOrderById(String orderId) {
        Order order;

        try (Connection connection = connectionManager.getConnection()) {
            order = orderDAO.getOrderById(connection, orderId);
        } catch (SQLException e) {
            LOGGER.error("Can't obtain order by id |{}|", orderId, e);
            throw new DBException("Can't get order by id", e);
        }

        return order;
    }

    public Map<Product, Integer> getProductsInOrder(Order order) {
        Map<Product, Integer> productsInOrder;

        try (Connection connection = connectionManager.getConnection()) {
            productsInOrder = orderDAO.getProductsByOrderId(connection, order.getId());

        } catch (SQLException e) {
            LOGGER.error("Can't obtain products in order |{}|", order.getId(), e);
            throw new DBException("Can't get products in order", e);
        }

        return productsInOrder;
    }

    public boolean changeProductAmountInOrder(String orderIdStr, String productIdStr, String amountStr) {
        int orderId = Integer.parseInt(orderIdStr);
        int productId = Integer.parseInt(productIdStr);
        int amount = Integer.parseInt(amountStr);

        if (amount < 1) {
            return false;
        }

        try (Connection connection = connectionManager.getConnection()) {
            orderDAO.changeProductAmountInOrder(connection, orderId, productId, amount);
            return true;
        } catch (SQLException e) {
            LOGGER.error("Can't change product |{}| amount |{}| in order |{}|", productId, amount, orderId, e);
            throw new DBException("Can't set change products amount in order", e);
        }
    }

    public boolean removeProductFromOrder(String orderIdStr, String productIdStr) {
        int orderId = Integer.parseInt(orderIdStr);
        int productId = Integer.parseInt(productIdStr);

        try (Connection connection = connectionManager.getConnection()) {
            orderDAO.removeProductInOrder(connection, orderId, productId);
            return true;
        } catch (SQLException e) {
            LOGGER.error("Can't remove product |{}| from order |{}|", productId, orderId, e);
            throw new DBException("Can't remove product from order", e);
        }

    }

    public boolean changeOrderStatus(String orderIdStr, String newStatusIdStr) {
        int orderId = Integer.parseInt(orderIdStr);
        int newStatusId = Integer.parseInt(newStatusIdStr);

        OrderDAO orderDAO = new OrderDAO();
        try (Connection connection = connectionManager.getConnection()) {
            orderDAO.changeOrderStatus(connection, orderId, newStatusId);
            return true;
        } catch (SQLException e) {
            LOGGER.error("Can't change order |{}| status", orderId, e);
            throw new DBException("Can't change order status", e);
        }
    }
}

