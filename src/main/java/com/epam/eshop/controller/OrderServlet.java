package com.epam.eshop.controller;

import com.epam.eshop.controller.constants.AttributesNames;
import com.epam.eshop.controller.constants.ParameterNames;
import com.epam.eshop.controller.constants.URLConstants;
import com.epam.eshop.entity.Order;
import com.epam.eshop.entity.Product;
import com.epam.eshop.entity.User;
import com.epam.eshop.entity.UserRole;
import com.epam.eshop.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * This servlet processes action with {@link Order} in post requests. All available action names
 * contain in privet constants. Action name becomes with request Param "actionOrder";
 * Provides order details for order in get request.
 *
 * Created by artch on 06.10.2020.
 */
@WebServlet("/orders/*")
public class OrderServlet extends HttpServlet {

    private static final String ACTION_ORDER_CHANGE_AMOUNT = "changeAmountProduct";
    private static final String ACTION_ORDER_REMOVE_PRODUCT = "removeProduct";
    private static final String ACTION_ORDER_CHANGE_STATUS = "changeStatus";

    private OrderService orderService;

    public OrderServlet() {
        orderService = new OrderService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter(ParameterNames.ACTION_NAME_FOR_ORDER);
        String orderId = request.getParameter(ParameterNames.ORDER_ID);
        String productId = request.getParameter(ParameterNames.PRODUCT_ID);

        boolean success;

        switch (action) { // TODO: rewritten with using Command pattern with success flag return
            case ACTION_ORDER_CHANGE_AMOUNT: {
                String amount = request.getParameter(ParameterNames.PRODUCT_AMOUNT);
                success = orderService.changeProductAmountInOrder(orderId, productId, amount);
                break;
            }
            case ACTION_ORDER_REMOVE_PRODUCT: {
                success = orderService.removeProductFromOrder(orderId, productId);
                break;
            }
            case ACTION_ORDER_CHANGE_STATUS: {
                String newStatusId = request.getParameter(ParameterNames.NEW_STATUS_ID);
                success = orderService.changeOrderStatus(orderId, newStatusId);
                break;
            }
            default: {
                throw new UnsupportedOperationException("Not implemented in OrderServlet");
            }
        }

        if (success) {
            response.sendRedirect(request.getContextPath() + "/orders/" + orderId);
        } else {
            response.sendError(500);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderId = Util.getIdFromURL(request.getPathInfo());

        Order order = orderService.getOrderById(orderId);

        User user = (User) request.getSession().getAttribute(AttributesNames.CURRENT_USER);
        
        if (!allowedAccessToOrder(order, user)) {
            response.sendRedirect(request.getContextPath() + URLConstants.MAIN);
            return;
        }

        order.setUserEmail(user.getEmail());
        request.setAttribute(AttributesNames.CURRENT_ORDER, order);

        Map<Product, Integer> productsInOrder = orderService.getProductsInOrder(order);
        request.setAttribute(AttributesNames.PRODUCTS_IN_ORDER, productsInOrder);
        
        request.getRequestDispatcher(URLConstants.VIEW_ORDERS_JSP).forward(request, response);
    }

    private boolean allowedAccessToOrder(Order order, User user) {
        return (order != null) && (order.getUserId() == user.getId() || Util.checkUserRole(user,UserRole.ADMINISTRATOR));
    }
}
