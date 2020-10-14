package com.epam.eshop.controller;

import com.epam.eshop.entity.User;
import com.epam.eshop.entity.UserRole;
import com.epam.eshop.service.cart.CartActionFactory;
import com.epam.eshop.service.cart.CartActionHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by artch on 05.10.2020.
 */
@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actionName = request.getParameter("actionCart");

        CartActionHandler cartAction = CartActionFactory.getAction(actionName);
        boolean success = cartAction.execute(request);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/cart");
        } else {
            response.sendError(500);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User currentUser = (User) request.getSession().getAttribute("currentUser");
        String currentUserRole = currentUser.getUserRole().getRole();

        if (!UserRole.CUSTOMER.equals(currentUserRole)) {
            response.sendRedirect(request.getContextPath() + "/main");
            return;
        }

        request.getRequestDispatcher("/jsp/cart.jsp").forward(request, response);
    }
}
