package com.epam.eshop.controller;

import com.epam.eshop.entity.Cart;
import com.epam.eshop.entity.User;
import com.epam.eshop.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by artch on 24.09.2020.
 */
@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserService userService = new UserService();

        String errorMessage = userService.getErrorMessageInRegistrationData(login, email);

        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/jsp/registration.jsp").forward(request, response);
            return;
        }

        String currentLocal;

        if (session.getAttribute("lang") == null) {
            currentLocal = request.getLocale().getLanguage();
            session.setAttribute("lang", currentLocal);
        } else {
            currentLocal = (String) session.getAttribute("lang");
        }

        User newUser = userService.setUser(login, email, password);

        Cart currentUserCart = new Cart();
        currentUserCart.setUserId(newUser.getId());

        userService.setUserLang(newUser, currentLocal);
        session.setAttribute("currentUserCart", currentUserCart);
        session.setAttribute("currentUser", newUser);
        response.sendRedirect("main");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/registration.jsp").forward(request, response);
    }

}
