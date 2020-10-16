package com.epam.eshop.dao;

import com.epam.eshop.entity.Order;
import com.epam.eshop.entity.OrderStatus;
import com.epam.eshop.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by artch on 22.09.2020.
 */
public class OrderDAO {

    private static final int ORDER_STATUS_DEFAULT_REGISTERED = 1;

    private static final String SQL_SET_ORDER = "insert into orders (user_id, order_status_id) values (?,?)";
    private static final String SQL_SET_PRODUCT_TO_ORDER = "insert into orders_has_products " +
            "(order_id, product_id, amount) values (?,?,?)";
    private static final String SQL_GET_ALL_ORDERS = "select * from orders " +
            "join order_statuses on orders.order_status_id = order_statuses.id join users on orders.user_id = users.id";
    private static final String SQL_GET_ALL_ORDERS_BY_USER_ID = "select * from orders " +
            "join order_statuses on orders.order_status_id = order_statuses.id where user_id = ?";
    private static final String SQL_GET_ORDER_BY_ID = "select * from orders " +
            "join order_statuses on orders.order_status_id = order_statuses.id where orders.id = ?";
    private static final String SQL_GET_PRODUCTS_BY_ORDER_ID = "select * from orders_has_products " +
            "join products on orders_has_products.product_id = products.id where order_id = ?";
    private static final String SQL_SET_PRODUCT_AMOUNT_IN_ORDER = "update orders_has_products set amount = ? " +
            "where order_id = ? and product_id = ?";
    private static final String SQL_REMOVE_PRODUCT_IN_ORDER = "delete from orders_has_products where order_id = ? and product_id = ?";
    private static final String SQL_CHANGE_ORDER_STATUS = "update orders set order_status_id = ? where id = ?";

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDAO.class);

    public int setOrder(Connection connection, int userId) throws SQLException {
        int orderId = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SET_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, String.valueOf(userId));
            preparedStatement.setString(2, String.valueOf(ORDER_STATUS_DEFAULT_REGISTERED));
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                orderId = resultSet.getInt(1);
            }
        }

        LOGGER.info("Order for user |{}| has been created", userId);
        return orderId;
    }

    public void setProductsToOrder(Connection connection, int orderId, Map<Product, Integer> productsInCart) throws SQLException {
        for (Map.Entry<Product, Integer> productEntry : productsInCart.entrySet()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SET_PRODUCT_TO_ORDER)) {
                preparedStatement.setString(1, String.valueOf(orderId));
                preparedStatement.setString(2, String.valueOf(productEntry.getKey().getId()));
                preparedStatement.setString(3, String.valueOf(productEntry.getValue()));
                preparedStatement.execute();
            }
        }
        LOGGER.info("All products |{}| has been set in order |{}|", productsInCart, orderId);
    }

    public List<Order> getAllOrders(Connection connection) throws SQLException {
        List<Order> orders = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_ORDERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Order order = new Order();
                fillOrder(resultSet, order);
                order.setUserEmail(resultSet.getString(Columns.USERS_EMAIL));
                orders.add(order);
            }
        }
        LOGGER.info("All orders has been get");
        return orders;
    }

    public List<Order> getAllOrdersByUserId(Connection connection, int userId) throws SQLException {
        List<Order> orders = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_ORDERS_BY_USER_ID)) {
            preparedStatement.setString(1, String.valueOf(userId));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Order order = new Order();
                fillOrder(resultSet, order);
                orders.add(order);
            }
        }
        LOGGER.info("All orders for user |{}| has been obtain", userId);
        return orders;
    }

    public Order getOrderById(Connection connection, String orderId) throws SQLException {
        Order order = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ORDER_BY_ID)) {
            preparedStatement.setString(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                order = new Order();
                fillOrder(resultSet, order);
            }

        }
        LOGGER.info("Order |{}| has been obtain", orderId);
        return order;
    }

    public Map<Product, Integer> getProductsByOrderId(Connection connection, int orderId) throws SQLException {
        Map<Product, Integer> productsInOrder = new HashMap<>();
        BaseProductDAO productDAO = new BaseProductDAO();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_PRODUCTS_BY_ORDER_ID)) {
            preparedStatement.setString(1, String.valueOf(orderId));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Product product = new Product();
                productDAO.fillBaseProduct(resultSet, product);
                product.setPrice(resultSet.getDouble(Columns.ORDERS_HAS_PRODUCTS_PRODUCT_PRICE));
                productsInOrder.put(product, resultSet.getInt(Columns.ORDERS_HAS_PRODUCTS_AMOUNT));
            }

        }
        LOGGER.info("All products in order |{}| has been obtain", orderId);
        return productsInOrder;
    }

    public void changeProductAmountInOrder(Connection connection, int orderId, int productId, int amount) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SET_PRODUCT_AMOUNT_IN_ORDER)) {
            preparedStatement.setString(1, String.valueOf(amount));
            preparedStatement.setString(2, String.valueOf(orderId));
            preparedStatement.setString(3, String.valueOf(productId));
            preparedStatement.execute();
        }
        LOGGER.info("Product |{}| amount in order |{}| has been changed", productId, orderId);
    }

    public void removeProductInOrder(Connection connection, int orderId, int productId) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_REMOVE_PRODUCT_IN_ORDER)) {
            preparedStatement.setString(1, String.valueOf(orderId));
            preparedStatement.setString(2, String.valueOf(productId));
            preparedStatement.execute();
        }
        LOGGER.info("Product |{}| in order |{}| has been removed", productId, orderId);
    }

    public void changeOrderStatus(Connection connection, int orderId, int newStatusId) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHANGE_ORDER_STATUS)) {
            preparedStatement.setString(1, String.valueOf(newStatusId));
            preparedStatement.setString(2, String.valueOf(orderId));
            preparedStatement.execute();
        }
        LOGGER.info("Status |{}| for order |{}| has been set", newStatusId, orderId);
    }

    private void fillOrder(ResultSet resultSet, Order order) throws SQLException {
        order.setId(resultSet.getInt(Columns.ORDERS_ID));
        order.setUserId(resultSet.getInt(Columns.ORDERS_USER_ID));
        order.setTotalPrice(resultSet.getDouble(Columns.ORDERS_TOTAL_PRICE));
        order.setCreateDate(resultSet.getString(Columns.ORDERS_CREATE_DATE));
        order.setLastUpdateDate(resultSet.getString(Columns.ORDERS_LAST_UPDATE_DATE));

        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setId(resultSet.getInt(Columns.ORDERS_ORDER_STATUS_ID));
        orderStatus.setStatus(resultSet.getString(Columns.ORDER_STATUSES_ORDER_STATUS));
        order.setOrderStatus(orderStatus);
        LOGGER.info("Order |{}| has been filled", order.getId());
    }
}
