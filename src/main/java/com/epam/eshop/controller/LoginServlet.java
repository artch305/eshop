package com.epam.eshop.controller;

import com.epam.eshop.entity.Cart;
import com.epam.eshop.entity.User;
import com.epam.eshop.entity.UserSettings;
import com.epam.eshop.entity.UserStatus;
import com.epam.eshop.service.CartService;
import com.epam.eshop.service.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by artch on 22.09.2020.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        String currentPage = request.getParameter("currentPageForReturn");

        if (currentPage == null || currentPage.trim().isEmpty()) {
            currentPage = "main";
        }

        UserService userService = new UserService();
        User user = userService.getUser(login, password);

        if (user == null) {
            session.setAttribute("errorLogin", "incorrect login or password");
            response.sendRedirect(currentPage);
            return;
        }

        if (UserStatus.BANNED.equals(user.getUserStatus().getStatus())) {
            response.sendRedirect(request.getContextPath() + "/jsp/errorBanned.jsp");
            return;
        }

        CartService cartService = new CartService();
        UserSettings userSettings = userService.getUserSettings(user);
        Cart currentUserCart = new Cart();
        cartService.fillCurrentUserCart(user, currentUserCart);

        session.removeAttribute("errorLogin");
        session.setAttribute("currentUserCart", currentUserCart);
        session.setAttribute("lang", userSettings.getLanguage());
        session.setAttribute("currentUser", user);
        response.sendRedirect(currentPage);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String currentPage = request.getParameter("currentPageForReturn");

        if (currentPage == null || currentPage.trim().isEmpty()) { // TODO: 10.10.2020 use trim() before isEmpty()
            currentPage = request.getContextPath() + "/main";
        }
        request.getSession().invalidate();
        response.sendRedirect(currentPage);
    }
}
