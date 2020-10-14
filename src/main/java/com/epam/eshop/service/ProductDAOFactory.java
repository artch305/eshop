package com.epam.eshop.service;

import com.epam.eshop.dao.KeyboardProductDAO;
import com.epam.eshop.dao.MonitorProductDAO;
import com.epam.eshop.dao.ProductDAO;
import com.epam.eshop.entity.Category;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Created by artch on 30.09.2020.
 */
class ProductDAOFactory {
    private static final Map<Category, Supplier<ProductDAO>> DAOs = new HashMap<Category, Supplier<ProductDAO>>() {{
        put(Category.MONITORS, MonitorProductDAO::new);
        put(Category.KEYBOARDS, KeyboardProductDAO::new);
    }};

    static ProductDAO getProductDAO(String category) {
        Supplier<ProductDAO> supplier = DAOs.get(Category.getCategoryByName(category));
        return supplier.get();
    }
}
