package com.epam.eshop.dao;

import com.epam.eshop.entity.Order;
import com.epam.eshop.entity.Product;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by artch on 14.10.2020.
 */

public class OrderDAOTest extends AbstractTest {

    @Test
    public void testSetOrder() throws Exception {
        // PREDICATE
        OrderDAO orderDAO = new OrderDAO();
        int userId = 2;
        int expectedOrderId = 3;
        // FUNCTIONALITY
        int newOrderId = orderDAO.setOrder(connection, userId);
        // TESTS
        assertEquals(expectedOrderId, newOrderId);
    }

    @Test
    public void testSetProductsToOrder() throws Exception {
        // PREDICATE
        OrderDAO orderDAO = new OrderDAO();
        ProductDAO productDAO = new BaseProductDAO();
        String productId6 = "6";
        int productId6Amount = 2;
        String productId7 = "7";
        int productId7Amount = 3;
        String productId8 = "8";
        int productId8Amount = 1;
        Map<Product, Integer> products = new HashMap<Product, Integer>() {{
            put(productDAO.getProductById(connection, productId6), productId6Amount);
            put(productDAO.getProductById(connection, productId7), productId7Amount);
            put(productDAO.getProductById(connection, productId8), productId8Amount);
        }};
        // FUNCTIONALITY
        orderDAO.setProductsToOrder(connection, 1, products);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select product_id, amount from orders_has_products where order_id = 1");
        Map<String, Integer> productsInOrder = new HashMap<>();

        while (resultSet.next()) {
            productsInOrder.put(resultSet.getString(1), resultSet.getInt(2));
        }

        // TESTS
        assertTrue(productsInOrder.containsKey(productId6));
        assertTrue(productsInOrder.containsKey(productId7));
        assertTrue(productsInOrder.containsKey(productId8));
        assertEquals(productId6Amount, (int) productsInOrder.get(productId6));
        assertEquals(productId7Amount, (int) productsInOrder.get(productId7));
        assertEquals(productId8Amount, (int) productsInOrder.get(productId8));
    }

    @Test
    public void testGetAllOrders() throws Exception {
        // PREDICATE
        OrderDAO orderDAO = new OrderDAO();
        Order order1 = new Order();
        order1.setId(1);
        Order order2 = new Order();
        order2.setId(2);
        // FUNCTIONALITY
        List<Order> orders = orderDAO.getAllOrders(connection);
        // TESTS
        assertTrue(orders.contains(order1));
        assertTrue(orders.contains(order2));
    }

    @Test
    public void testGetAllOrdersByUserId() throws Exception {
        // PREDICATE
        OrderDAO orderDAO = new OrderDAO();
        // FUNCTIONALITY
        List<Order> orders = orderDAO.getAllOrdersByUserId(connection, 3);
        // TESTS
        assertEquals(1, orders.size());
    }

    @Test
    public void testGetOrderById() throws Exception {
        // PREDICATE
        OrderDAO orderDAO = new OrderDAO();
        String orderId = "1";
        // FUNCTIONALITY
        Order order = orderDAO.getOrderById(connection, orderId);
        // TESTS
        assertNotNull(order);
        assertEquals(orderId, String.valueOf(order.getId()));
    }

    @Test
    public void testGetProductsByOrderId() throws Exception {
        // PREDICATE
        OrderDAO orderDAO = new OrderDAO();
        Product productWithId1 = new Product();
        productWithId1.setId(1);
        int amountProduct1 = 3;
        Product productWithId2 = new Product();
        productWithId2.setId(2);
        int amountProduct2 = 5;
        Product productWithId4 = new Product();
        productWithId4.setId(4);
        int amountProduct4 = 1;
        int orderId = 2;
        // FUNCTIONALITY
        Map<Product, Integer> products = orderDAO.getProductsByOrderId(connection, orderId);
        // TESTS
        assertEquals(amountProduct1, (int) products.get(productWithId1));
        assertEquals(amountProduct2, (int) products.get(productWithId2));
        assertEquals(amountProduct4, (int) products.get(productWithId4));
    }

    @Test
    public void testChangeProductAmountInOrder() throws Exception {
        // PREDICATE
        OrderDAO orderDAO = new OrderDAO();
        int orderId = 1;
        int productId = 3;
        int newProductAmount = 3;
        // FUNCTIONALITY
        orderDAO.changeProductAmountInOrder(connection, orderId, productId, newProductAmount);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select amount from orders_has_products where order_id = 1 and product_id = 3");
        int amountAfterSet = 0;

        if (resultSet.next()) {
            amountAfterSet = resultSet.getInt(1);
        }

        // TESTS
        assertEquals(newProductAmount, amountAfterSet);
    }

    @Test
    public void testRemoveProductInOrder() throws Exception {
        // PREDICATE
        OrderDAO orderDAO = new OrderDAO();
        ProductDAO productDAO = new BaseProductDAO();
        int orderId = 1;
        int productId2 = 2;
        int productId6Amount = 7;
        Product product2 = productDAO.getProductById(connection, String.valueOf(productId2));
        Map<Product, Integer> products = new HashMap<Product, Integer>() {{
            put(product2, productId6Amount);
        }};
        orderDAO.setProductsToOrder(connection, orderId, products);
        // FUNCTIONALITY
        orderDAO.removeProductInOrder(connection, orderId, productId2);
        Map<Product, Integer> productsInOrder = orderDAO.getProductsByOrderId(connection, orderId);
        // TESTS
        assertFalse(productsInOrder.containsKey(product2));
    }

    @Test
    public void testChangeOrderStatus() throws Exception {
        // PREDICATE
        OrderDAO orderDAO = new OrderDAO();
        int orderId = 2;
        int newOrderStatusId = 2;
        // FUNCTIONALITY
        orderDAO.changeOrderStatus(connection, orderId, newOrderStatusId);
        Order changedOrder = orderDAO.getOrderById(connection, String.valueOf(orderId));
        // TESTS
        assertEquals(newOrderStatusId, changedOrder.getOrderStatus().getId());
    }
}
