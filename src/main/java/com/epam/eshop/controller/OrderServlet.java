package com.epam.eshop.controller;

import com.epam.eshop.entity.Order;
import com.epam.eshop.entity.Product;
import com.epam.eshop.entity.User;
import com.epam.eshop.entity.UserRole;
import com.epam.eshop.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by artch on 06.10.2020.
 */
@WebServlet("/orders/*")
public class OrderServlet extends HttpServlet {

    private static final String ACTION_ORDER_CHANGE_AMOUNT = "changeAmountProduct";
    private static final String ACTION_ORDER_REMOVE_PRODUCT = "removeProduct";
    private static final String ACTION_ORDER_CHANGE_STATUS = "changeStatus";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("actionOrder");
        String orderId = request.getParameter("orderId");
        String productId = request.getParameter("productId");
        OrderService orderService = new OrderService();

        boolean success = false;

        switch (action) {
            case ACTION_ORDER_CHANGE_AMOUNT: {
                String amount = request.getParameter("amount");
                success = orderService.changeProductAmountInOrder(orderId, productId, amount);
                break;
            }
            case ACTION_ORDER_REMOVE_PRODUCT: {
                success = orderService.removeProductFromOrder(orderId, productId);
                break;
            }
            case ACTION_ORDER_CHANGE_STATUS: {
                String newStatusId = request.getParameter("newStatusId");
                success = orderService.changeOrderStatus(orderId, newStatusId);
                break;
            }
            default: {
                throw new UnsupportedOperationException("Not implemented in OrderServlet");
            }
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/orders/" + orderId);
        } else {
            response.sendError(500);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("currentUser");
        String orderId = request.getPathInfo();
        orderId = orderId.replace("/", "");
        OrderService orderService = new OrderService();
        Order order = orderService.getOrderById(orderId);

        if ((order == null) || (order.getUserId() != user.getId() && !user.getUserRole().getRole().equals(UserRole.ADMINISTRATOR))) {
            response.sendRedirect(request.getContextPath() + "/main");
            return;
        }

        order.setUserEmail(user.getEmail());
        Map<Product, Integer> productsInOrder = orderService.getProductsInOrder(order);

        request.setAttribute("productsInOrder", productsInOrder);
        request.setAttribute("currentOrder", order);
        request.getRequestDispatcher("/jsp/viewOrder.jsp").forward(request, response);
    }
}
