package com.epam.eshop.controller;

import com.epam.eshop.controller.constants.AttributesNames;
import com.epam.eshop.controller.constants.ParameterNames;
import com.epam.eshop.controller.constants.URLConstants;
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

    private UserService userService;

    public UsersServlet() {
        userService = new UserService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter(ParameterNames.USER_ID));
        String newLogin = request.getParameter(ParameterNames.LOGIN).trim();
        String newEmail = request.getParameter(ParameterNames.EMAIL).trim();
        String newPassword = request.getParameter(ParameterNames.PASSWORD).trim();
        int newUserStatusId = Integer.parseInt(request.getParameter(ParameterNames.USER_STATUS));
        String newUserRole = request.getParameter(ParameterNames.USER_ROLE); // TODO: 14.10.2020 wrap all these parameters into DTO (data-transfer object) to hold and transfer it and reduce amount of parameters in method signature

        String errorMessage = userService.changeUserData(userId, newLogin, newEmail, newPassword, newUserStatusId, newUserRole);

        if (errorMessage == null) {
            request.getSession().setAttribute(AttributesNames.SUCCESS, "success");
            response.sendRedirect(request.getContextPath() + URLConstants.USERS);
        } else {
            request.setAttribute(AttributesNames.ERROR_MESSAGE, errorMessage);
            request.setAttribute(AttributesNames.USERS, userService.getAllUsers());
            request.setAttribute(AttributesNames.USER_ID_WITH_ERROR, userId);
            
            request.getRequestDispatcher(URLConstants.USERS_JSP).forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = Util.getUserFromSession(request.getSession());

        if (!Util.checkUserRole(user,UserRole.ADMINISTRATOR)) {
            response.sendRedirect(request.getContextPath() + URLConstants.MAIN);
            return;
        }

        List<User> users = userService.getAllUsers();
        request.setAttribute(AttributesNames.USERS, users);

        Util.replaceAttributeFromSessionIntoRequest(request, AttributesNames.SUCCESS);
        
        request.getRequestDispatcher(URLConstants.USERS_JSP).forward(request, response);
    }
}
