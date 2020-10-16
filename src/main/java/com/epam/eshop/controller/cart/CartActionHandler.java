package com.epam.eshop.controller.cart;

import com.epam.eshop.entity.Cart;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface for using pattern Command
 * Created by artch on 05.10.2020.
 */
public interface CartActionHandler {

    /**
     * Do any action with {@link Cart} using {@link HttpServletRequest}
     * @param request contains any Params for action
     * @return true is action has been success
     */
    boolean execute(HttpServletRequest request);

}
