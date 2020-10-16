package com.epam.eshop.controller.cart;

import com.epam.eshop.controller.Util;
import com.epam.eshop.entity.Cart;
import com.epam.eshop.entity.Order;
import com.epam.eshop.entity.Product;
import com.epam.eshop.entity.User;
import com.epam.eshop.exception.DBException;
import com.epam.eshop.service.OrderService;

import javax.servlet.http.HttpServletRequest;

/**
 * Implements interface {@link CartActionHandler} for creating {@link Order} and adding
 * {@link Product} from {@link Cart}
 * Created by artch on 06.10.2020.
 */
public class ConfirmCartToOrderAction implements CartActionHandler {

    /**
     * creat {@link Order} and adding
     * {@link Product} from {@link Cart}. Then clean {@link Cart}
     *
     * @param request {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet. Should contains params: "currentUser",
     *                "currentUserCart";
     * @return true when execution has been success
     * @throws DBException throw this unchecked exception if something happened
     *                     wrong in DAO level
     */
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
