package com.epam.eshop.controller.cart;

import com.epam.eshop.controller.Util;
import com.epam.eshop.controller.constants.ParameterNames;
import com.epam.eshop.entity.Cart;
import com.epam.eshop.entity.Product;
import com.epam.eshop.entity.User;
import com.epam.eshop.exception.DBException;
import com.epam.eshop.service.CartService;

import javax.servlet.http.HttpServletRequest;


/**
 * Implements interface {@link CartActionHandler} for removing {@link Product} from {@link Cart}
 * Created by artch on 05.10.2020.
 */
class RemoveFromCartAction implements CartActionHandler {

    /**
     * remove {@link Product} from {@link Cart}
     *
     * @param request {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet. Should contains params: "currentUser",
     *                "productId", "currentUserCart";
     * @return true when execution has been success
     * @throws DBException throw this unchecked exception if something happened
     *                     wrong in DAO level
     */
    @Override
    public boolean execute(HttpServletRequest request) {
        User user = Util.getUserFromSession(request.getSession());
        Cart currentUserCart = Util.getCartFromSession(request.getSession());
        int productId = Integer.parseInt(request.getParameter(ParameterNames.PRODUCT_ID));

        CartService cartService = new CartService();
        boolean success;

        Product product = currentUserCart.getProductsInCart().keySet()
                .stream()
                .filter(product1 -> product1.getId() == productId)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        success = cartService.removeProduct(user, product);
        currentUserCart.getProductsInCart().remove(product);

        return success;
    }
}
