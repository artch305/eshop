package com.epam.eshop.filter.service;

import com.epam.eshop.entity.Category;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Created by artch on 12.10.2020.
 */
public class FilterServiceFactory {
    private static final Map<String, Supplier<BaseFilterService>> filterServices = new HashMap<String, Supplier<BaseFilterService>>() {{
        put(Category.MONITORS.getDatabaseValue(), MonitorFilterService::new);
        put(Category.KEYBOARDS.getDatabaseValue(), KeyboardFilterService::new);
    }};

    public static BaseFilterService getFilterService(String category) {
        return filterServices.get(category).get();
    }

}