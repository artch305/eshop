package com.epam.eshop.controller;

import com.epam.eshop.controller.constants.URLConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet forwards main.jsp for any requests
 *
 * Created by artch on 23.09.2020.
 */
@WebServlet("/main")
public class MainPageServlet extends HttpServlet {

    /**
     * Routes to the main entry page.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(URLConstants.MAIN_JSP).forward(request, response);
    }
}
