package com.epam.eshop.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet does log out for current user by way invalidate {@link javax.servlet.http.HttpSession}
 *
 * Created by artch on 15.10.2020.
 */
@WebServlet("/logout")
public class LogOutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentPage = Util.getReturnPath(request);

        request.getSession().invalidate();
        response.sendRedirect(currentPage);
    }
}
