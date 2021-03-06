package com.epam.eshop.controller;

import com.epam.eshop.controller.constants.AttributesNames;
import com.epam.eshop.controller.constants.ParameterNames;
import com.epam.eshop.controller.constants.URLConstants;
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
 * This servlet processes registration logic. Input params become with request param ("login", "password", "email").
 * if input data incorrect, in request put errorMessage with description.
 *
 * Created by artch on 24.09.2020.
 */
@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private UserService userService;

    public RegistrationServlet() {
        userService = new UserService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(ParameterNames.LOGIN);
        String email = request.getParameter(ParameterNames.EMAIL);
        String errorMessage = userService.getErrorMessageInRegistrationData(login, email);

        if (errorMessage != null) {
            request.setAttribute(AttributesNames.ERROR_MESSAGE, errorMessage);
            request.getRequestDispatcher(URLConstants.REGISTRATION_JSP).forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        String currentLang;

        if (session.getAttribute(AttributesNames.LANG) == null) {
            currentLang = request.getLocale().getLanguage();
            session.setAttribute(AttributesNames.LANG, currentLang);
        } else {
            currentLang = (String) session.getAttribute(AttributesNames.LANG);
        }

        String password = request.getParameter(ParameterNames.PASSWORD);
        User newUser = userService.setUser(login, email, password, currentLang);
        session.setAttribute(AttributesNames.CURRENT_USER, newUser);

        Cart currentUserCart = new Cart();
        currentUserCart.setUserId(newUser.getId());
        session.setAttribute(AttributesNames.CURRENT_USER_CART, currentUserCart);

        response.sendRedirect(request.getContextPath() + URLConstants.MAIN);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(URLConstants.REGISTRATION_JSP).forward(request, response);
    }

}
