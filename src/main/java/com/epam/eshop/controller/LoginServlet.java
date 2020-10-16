package com.epam.eshop.controller;

import com.epam.eshop.controller.constants.AttributesNames;
import com.epam.eshop.controller.constants.ParameterNames;
import com.epam.eshop.controller.constants.URLConstants;
import com.epam.eshop.entity.Cart;
import com.epam.eshop.entity.User;
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

    private UserService userService;
    private CartService cartService;

    public LoginServlet() {
        userService = new UserService();
        cartService = new CartService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String currentPage = Util.getReturnPath(request);

        String login = request.getParameter(ParameterNames.LOGIN);
        String password = request.getParameter(ParameterNames.PASSWORD);

        User user = userService.getUser(login, password);

        HttpSession session = request.getSession();

        if (user == null) {
            session.setAttribute(AttributesNames.ERROR_LOGIN, true);
            response.sendRedirect(currentPage);
            return;
        }

        if (UserStatus.BANNED.equals(user.getUserStatus().getStatus())) {
            response.sendRedirect(request.getContextPath() + URLConstants.ERROR_BANNED_JSP);
            return;
        }

        session.removeAttribute(AttributesNames.ERROR_LOGIN);
        session.setAttribute(AttributesNames.CURRENT_USER, user);

        session.setAttribute(AttributesNames.LANG, user.getLang());

        Cart currentUserCart = new Cart();
        cartService.fillCurrentUserCart(user, currentUserCart);
        session.setAttribute(AttributesNames.CURRENT_USER_CART, currentUserCart);

        response.sendRedirect(currentPage);
    }
}
