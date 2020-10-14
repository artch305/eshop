package com.epam.eshop.controller;

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("currentUser");

        OrderService orderService = new OrderService();
        HttpSession session = request.getSession();
        List<Order> orders = null;

        if (UserRole.ADMINISTRATOR.equals(user.getUserRole().getRole())) {
            orders = orderService.getAllOrders();
        } else if (UserRole.CUSTOMER.equals(user.getUserRole().getRole())) {
            orders = orderService.getOrdersByUser(user);
        }

        session.setAttribute("orders", orders);
        request.getRequestDispatcher("/jsp/orders.jsp").forward(request, response);

    }
}
