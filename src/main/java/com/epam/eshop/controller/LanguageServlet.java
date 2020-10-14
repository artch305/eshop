package com.epam.eshop.controller;

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String language = request.getParameter("lang");

        HttpSession session = request.getSession();
        session.setAttribute("lang", language);

        User user = (User) session.getAttribute("currentUser");

        if (user != null) {
            UserService userService = new UserService();
            userService.updateUserLang(user, language);
        }

        response.sendRedirect(request.getContextPath() + "/main"); // TODO: 28.09.2020 remove? or leave if used only from JS
    }
}
