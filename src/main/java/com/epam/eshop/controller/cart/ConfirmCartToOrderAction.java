package com.epam.eshop.controller.cart;

import com.epam.eshop.controller.Util;
import com.epam.eshop.controller.constants.AttributesNames;
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
        User user = Util.getUserFromSession(request.getSession());
        Cart currentUserCart = Util.getCartFromSession(request.getSession());
        OrderService orderService = new OrderService();

        boolean success = orderService.setOrder(user, currentUserCart);
        request.getSession().setAttribute("success", success);

        return success;
    }
}
