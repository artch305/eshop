package com.epam.eshop.controller;

import com.epam.eshop.entity.User;
import com.epam.eshop.entity.UserRole;
import com.epam.eshop.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by artch on 07.10.2020.
 */
@WebServlet("/users")
public class UsersServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserService();
        int userId = Integer.parseInt(request.getParameter("userId"));
        String newLogin = request.getParameter("login").trim();
        String newEmail = request.getParameter("email").trim();
        String newPassword = request.getParameter("password").trim();
        int newUserStatusId = Integer.parseInt(request.getParameter("status"));
        int newUserRoleId = Integer.parseInt(request.getParameter("role"));

        String errorMessage = userService.changeUserData(userId, newLogin, newEmail, newPassword, newUserStatusId, newUserRoleId);

        if (errorMessage == null) {
            response.sendRedirect("users");
        } else {
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("users", userService.getAllUsers());
            request.setAttribute("userIdWithError", userId);
            request.getRequestDispatcher("/jsp/users.jsp").forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("currentUser");

        if (!UserRole.ADMINISTRATOR.equals(user.getUserRole().getRole())) {
            response.sendRedirect(request.getContextPath() + "/main");
            return;
        }

        UserService userService = new UserService();
        List<User> users = userService.getAllUsers();

        request.setAttribute("users", users);
        request.getRequestDispatcher("/jsp/users.jsp").forward(request, response);
    }
}
