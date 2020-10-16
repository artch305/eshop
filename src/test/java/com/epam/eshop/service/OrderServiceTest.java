package com.epam.eshop.service;

import com.epam.eshop.dao.AbstractTest;
import com.epam.eshop.dao.CartDAO;
import com.epam.eshop.dao.ConnectionManager;
import com.epam.eshop.dao.OrderDAO;
import com.epam.eshop.entity.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by artch on 16.10.2020.
 */
public class OrderServiceTest extends AbstractTest {

    private OrderService orderService;

    private OrderDAO orderDAO;
    private ConnectionManager connectionManager;
    private CartDAO cartDAO;


    @Before
    public void initOrderServiceTest() throws Exception {
        orderDAO = mock(OrderDAO.class);
        connectionManager = mock(ConnectionManager.class);
        cartDAO = mock(CartDAO.class);
        orderService = new OrderService(orderDAO, cartDAO, connectionManager);
    }

    @Test
    public void testSetOrder() throws Exception {
        // PREDICATE
        Cart userCart = new Cart();
        Product product1 = new MonitorProduct();
        Product product3 = new KeyboardProduct();
        Product product4 = new MonitorProduct();
        userCart.getProductsInCart().put(product1, 2);
        userCart.getProductsInCart().put(product3, 5);
        userCart.getProductsInCart().put(product4, 1);
        User user = new User();
        user.setId(2);
        when(connectionManager.getConnection()).thenReturn(connection);
        // FUNCTIONALITY
        orderService.setOrder(user, userCart);
        // TESTS
        verify(connectionManager).getConnection();
        verify(orderDAO).setOrder(connection,user.getId());
        verify(cartDAO).removeAllProductsByUser(connection, user.getId());
        assertEquals(0, userCart.getProductsInCart().size());
    }

    @Test
    public void testGetAllOrders() throws Exception {
        // PREDICATE
        List<Order> orders = new ArrayList<>();
        when(orderDAO.getAllOrders(connection)).thenReturn(orders);
        when(connectionManager.getConnection()).thenReturn(connection);
        // FUNCTIONALITY
        List<Order> orders2 = orderService.getAllOrders();
        // TESTS
        assertEquals(orders, orders2);
        verify(connectionManager).getConnection();
        verify(orderDAO).getAllOrders(connection);
    }

    @Test
    public void testGetOrdersByUser() throws Exception {
        // PREDICATE
        List<Order> orders = new ArrayList<>();
        User user = new User();
        user.setId(2);
        when(orderDAO.getAllOrdersByUserId(connection, user.getId())).thenReturn(orders);
        when(connectionManager.getConnection()).thenReturn(connection);
        // FUNCTIONALITY
        List<Order> orders2 = orderService.getOrdersByUser(user);
        // TESTS
        assertEquals(orders, orders2);
        verify(connectionManager).getConnection();
        verify(orderDAO).getAllOrdersByUserId(connection, user.getId());
    }

    @Test
    public void testGetOrderById() throws Exception {
        // PREDICATE
        Order order = new Order();
        int orderId = 1;
        order.setId(orderId);
        User user = new User();
        user.setId(2);
        when(orderDAO.getOrderById(connection,String.valueOf(orderId))).thenReturn(order);
        when(connectionManager.getConnection()).thenReturn(connection);
        // FUNCTIONALITY
        Order order2 = orderService.getOrderById(String.valueOf(orderId));
        // TESTS
        assertEquals(order, order2);
        verify(connectionManager).getConnection();
        verify(orderDAO).getOrderById(connection,String.valueOf(orderId));
    }

    @Test
    public void testGetProductsInOrder() throws Exception {
        // PREDICATE
        Map<Product, Integer> productsInCart = new HashMap<>();
        Order order = new Order();
        int orderId = 1;
        order.setId(orderId);
        when(connectionManager.getConnection()).thenReturn(connection);
        when(orderDAO.getProductsByOrderId(connection,orderId)).thenReturn(productsInCart);
        // FUNCTIONALITY
        Map<Product, Integer> productsInCart2 = orderService.getProductsInOrder(order);
        // TESTS
        assertEquals(productsInCart, productsInCart2);
        verify(connectionManager).getConnection();
        verify(orderDAO).getProductsByOrderId(connection,orderId);
    }

    @Test
    public void testChangeProductAmountInOrder() throws Exception {
        // PREDICATE
        String orderId = "1";
        String productId = "2";
        String rightAmount = "3";
        String wongAmount = "-1";
        when(connectionManager.getConnection()).thenReturn(connection);
        // FUNCTIONALITY
        boolean test1 = orderService.changeProductAmountInOrder(orderId, productId, rightAmount);
        boolean test2 = orderService.changeProductAmountInOrder(orderId, productId, wongAmount);
        // TESTS
        verify(connectionManager).getConnection();
        verify(orderDAO).changeProductAmountInOrder(connection, 1,2,3);
        assertTrue(test1);
        assertFalse(test2);
    }

    @Test
    public void testRemoveProductFromOrder() throws Exception {
        // PREDICATE
        int productId = 3;
        int orderId = 2;
        when(connectionManager.getConnection()).thenReturn(connection);
        // FUNCTIONALITY
        boolean success = orderService.removeProductFromOrder(String.valueOf(orderId),String.valueOf(productId));
        // TESTS
        verify(connectionManager).getConnection();
        verify(orderDAO).removeProductInOrder(connection,orderId, productId);
        assertTrue(success);
    }

    @Test
    public void testChangeOrderStatus() throws Exception {
        // PREDICATE
        int statusId = 2;
        int orderId = 2;
        when(connectionManager.getConnection()).thenReturn(connection);
        // FUNCTIONALITY
        boolean success = orderService.changeOrderStatus(String.valueOf(orderId),String.valueOf(statusId));
        // TESTS
        verify(connectionManager).getConnection();
        verify(orderDAO).changeOrderStatus(connection,orderId,statusId);
        assertTrue(success);
    }
}