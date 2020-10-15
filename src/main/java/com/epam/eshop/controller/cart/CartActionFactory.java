package com.epam.eshop.controller.cart;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Created by artch on 05.10.2020.
 */
public class CartActionFactory {

    private static final String ACTION_ADD = "addProduct";
    private static final String ACTION_REMOVE = "removeProduct";
    private static final String ACTION_CHANGE_AMOUNT = "changeAmountProduct";
    private static final String ACTION_CONFIRM = "confirmOrder";

    private static final Map<String, Supplier<CartActionHandler>> cartActions = new HashMap<String, Supplier<CartActionHandler>>() {{
        put(ACTION_ADD, AddToCartAction::new);
        put(ACTION_REMOVE, RemoveFromCartAction::new);
        put(ACTION_CHANGE_AMOUNT, ChangeAmountProductInCartAction::new);
        put(ACTION_CONFIRM, ConfirmCartToOrderAction::new);
    }};

    public static CartActionHandler getAction(String action) {
        return cartActions.get(action).get();
    }

}
