package com.epam.eshop.service.cartHandler;

import com.epam.eshop.entity.Cart;
import com.epam.eshop.entity.User;
import com.epam.eshop.service.OrderService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by artch on 06.10.2020.
 */
public class ConfirmCartToOrderAction implements CartActionHandler {

    @Override
    public boolean execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("currentUser");
        Cart currentUserCart = (Cart) request.getSession().getAttribute("currentUserCart");
        OrderService orderService = new OrderService();

        boolean success = orderService.setOrder(user, currentUserCart);
        request.setAttribute("success", success);

        return success;
    }
}
