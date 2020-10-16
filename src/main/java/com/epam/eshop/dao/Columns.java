package com.epam.eshop.dao;

/**
 * Created by artch on 06.10.2020.
 */
public class Columns {
    public static final String USERS_ID = "id"; // TODO: 14.10.2020 add comments for which table these columns belong
    public static final String USERS_LOGIN = "login";
    public static final String USERS_EMAIL = "email";
    public static final String USERS_PASSWORD = "password";
    public static final String USERS_USER_STATUS_ID = "user_status_id";
    public static final String USERS_USER_ROLE_ID = "user_role_id";
    public static final String USERS_REGISTRATION_DATE = "registration_date";
    public static final String USERS_LANG = "lang";
    public static final String USERS_ROLE = "role";
    public static final String USERS_STATUS = "status";

    public static final String PRODUCTS_ID = "id";
    public static final String PRODUCTS_CATEGORY = "category";
    public static final String PRODUCTS_PRODUCER = "producer";
    public static final String PRODUCTS_NAME = "name";
    public static final String PRODUCTS_PRICE = "price";
    public static final String PRODUCTS_DESCRIPTION = "description";
    public static final String PRODUCTS_ACTIVE = "active";
    public static final String PRODUCTS_ADDED_DATE = "added_date";
    public static final String PRODUCTS_IMG_URL = "img_url";

    public static final String MONITOR_PRODUCTS_ID = "id";
    public static final String MONITOR_PRODUCTS_PRODUCT_ID = "product_id";
    public static final String MONITOR_PRODUCTS_DIAGONAL = "diagonal";
    public static final String MONITOR_PRODUCTS_PANEL_TYPE = "panel_type";
    public static final String MONITOR_PRODUCTS_BRIGHTNESS = "brightness";

    public static final String KEYBOARD_PRODUCTS_ID = "id";
    public static final String KEYBOARD_PRODUCTS_PRODUCT_ID = "product_id";
    public static final String KEYBOARD_PRODUCTS_CONNECTION_TYPE = "connection_type";
    public static final String KEYBOARD_PRODUCTS_MECHANICAL = "mechanical";
    public static final String KEYBOARD_PRODUCTS_LIGHT_COLOR = "light_color";

    public static final String ORDERS_ID = "id";
    public static final String ORDERS_USER_ID = "user_id";
    public static final String ORDERS_TOTAL_PRICE = "total_price";
    public static final String ORDERS_CREATE_DATE = "create_date";
    public static final String ORDERS_LAST_UPDATE_DATE = "last_update_date";
    public static final String ORDERS_ORDER_STATUS_ID = "order_status_id";

    public static final String ORDER_STATUSES_ID = "id";
    public static final String ORDER_STATUSES_ORDER_STATUS = "order_status";

    public static final String ORDERS_HAS_PRODUCTS_ORDER_ID = "order_id";
    public static final String ORDERS_HAS_PRODUCTS_PRODUCT_ID = "product_id";
    public static final String ORDERS_HAS_PRODUCTS_PRODUCT_PRICE = "product_price";
    public static final String ORDERS_HAS_PRODUCTS_AMOUNT = "amount";
    public static final String ORDERS_HAS_PRODUCTS_TOTAL_PRICE = "total_price";

    public static final String CART_USER_ID = "user_id";
    public static final String CART_PRODUCT_ID = "product_id";
    public static final String CART_AMOUNT = "amount";

}
