package com.epam.eshop.filter;

import com.epam.eshop.entity.Category;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Created by artch on 30.09.2020.
 */
public class FiltersMapper {

    private static final Map<Category, Supplier<AbstractFilters>> allFilters = new HashMap<Category, Supplier<AbstractFilters>>() {{
        put(Category.MONITORS, FiltersMonitorProducts::new);
        put(Category.KEYBOARDS, FiltersKeyboardProducts::new);
    }};

    public static AbstractFilters getFilters(String category) {
        Supplier<AbstractFilters> supplier = allFilters.get(Category.getCategoryByName(category));
        return supplier.get();
    }
}
