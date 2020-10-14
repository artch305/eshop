package com.epam.eshop.service.cart;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by artch on 05.10.2020.
 */
public interface CartActionHandler {

    boolean execute(HttpServletRequest request);

}
