package com.epam.eshop.controller;

import com.epam.eshop.controller.constants.AttributesNames;
import com.epam.eshop.controller.constants.ParameterNames;
import com.epam.eshop.controller.constants.URLConstants;
import com.epam.eshop.entity.User;
import com.epam.eshop.entity.UserRole;
import com.epam.eshop.controller.cart.CartActionFactory;
import com.epam.eshop.controller.cart.CartActionHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * This servlet processes all requests for changing or getting {@link com.epam.eshop.entity.Cart}
 * Here using pattern Command for changing Cart in post requests with param "actionCart".
 *
 * Created by artch on 05.10.2020.
 */
@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!Util.checkUserRole(Util.getUserFromSession(request.getSession()), UserRole.CUSTOMER)){
            response.sendRedirect(request.getContextPath() + URLConstants.MAIN);
        }

        String actionName = request.getParameter(ParameterNames.ACTION_NAME_FOR_CART);

        CartActionHandler cartAction = CartActionFactory.getAction(actionName);
        boolean success = cartAction.execute(request);

        if (success) {
            response.sendRedirect(request.getContextPath() + URLConstants.CART);
        } else {
            response.sendError(500);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User currentUser = (User) request.getSession().getAttribute(AttributesNames.CURRENT_USER);

        if (!Util.checkUserRole(currentUser, UserRole.CUSTOMER)) {
            response.sendRedirect(request.getContextPath() + URLConstants.MAIN);
            return;
        }

        Util.replaceAttributeFromSessionIntoRequest(request, AttributesNames.SUCCESS);

        request.getRequestDispatcher(URLConstants.CART_JSP).forward(request, response);
    }
}
