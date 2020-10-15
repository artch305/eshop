package com.epam.eshop.controller;

import com.epam.eshop.controller.constants.AttributesNames;
import com.epam.eshop.controller.constants.URLConstants;
import com.epam.eshop.entity.Order;
import com.epam.eshop.entity.User;
import com.epam.eshop.entity.UserRole;
import com.epam.eshop.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by artch on 06.10.2020.
 */
@WebServlet("/orders")
public class OrdersPageServlet extends HttpServlet {

    private OrderService orderService;

    public OrdersPageServlet() {
        orderService = new OrderService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(AttributesNames.CURRENT_USER);

        List<Order> orders = null;

        if (Util.checkUserRole(user,UserRole.ADMINISTRATOR)) {
            orders = orderService.getAllOrders();
        } else if (Util.checkUserRole(user, UserRole.CUSTOMER)) {
            orders = orderService.getOrdersByUser(user);
        }

        session.setAttribute(AttributesNames.ORDERS, orders);
        
        request.getRequestDispatcher(URLConstants.ORDERS_JSP).forward(request, response);
    }
}
