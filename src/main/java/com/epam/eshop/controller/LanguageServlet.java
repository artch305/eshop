package com.epam.eshop.controller;

import com.epam.eshop.controller.constants.AttributesNames;
import com.epam.eshop.controller.constants.URLConstants;
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
 * Created by artch on 25.09.2020.
 */
@WebServlet("/lang")
public class LanguageServlet extends HttpServlet {

    private UserService userService;

    public LanguageServlet() {
        userService = new UserService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lang = request.getParameter(AttributesNames.LANG);

        HttpSession session = request.getSession();
        session.setAttribute(AttributesNames.LANG, lang);

        User user = Util.getUserFromSession(session);

        if (user != null) {
            userService.setUserLang(user, lang);
        }

        response.sendRedirect(request.getContextPath() + URLConstants.MAIN);
    }
}
