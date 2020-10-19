package com.epam.eshop.filter.service;


import com.epam.eshop.dao.AllValuesForFilters;
import com.epam.eshop.dao.Columns;
import com.epam.eshop.entity.User;
import com.epam.eshop.filter.AbstractFilters;
import com.epam.eshop.filter.FilterService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by artch on 10.10.2020.
 */
public abstract class BaseFilterService implements FilterService {

    static final String DELIMITER_BETWEEN_CONDITIONS = " and ";
    static final String DELIMITER_BETWEEN_WORDS = "', '";
    static final String ORDERING_FOR_DEFAULT = "producerA-Z";
    protected StringBuilder newConditions = new StringBuilder();

    private static final Map<String, String> ORDERING_CONDITION_MAPPER = new HashMap<String, String>() {{
        put("minPrice", Columns.PRODUCTS_PRICE);
        put("maxPrice", Columns.PRODUCTS_PRICE + " desc");
        put("producerA-Z", Columns.PRODUCTS_PRODUCER);
        put("producerZ-A", Columns.PRODUCTS_PRODUCER + " desc");
    }};

    public void updateFilters(AbstractFilters filters, HttpServletRequest request) {
        String newMinPriceStr = request.getParameter("minPrice");
        double newMinPrice = newMinPriceStr.isEmpty() ? 0 : Double.parseDouble(newMinPriceStr);

        String newMaxPriceStr = request.getParameter("maxPrice");
        double newMaxPrice = newMaxPriceStr.isEmpty() ? 0 : Double.parseDouble(newMaxPriceStr);

        String[] newProducersArray = request.getParameterValues("producers");
        List<String> newProducers = newProducersArray == null ? null : Arrays.asList(newProducersArray);

        String[] newActiveArray = request.getParameterValues("active");

        List<Boolean> newActiveList = newActiveArray == null ? null : Arrays.stream(newActiveArray)
                .map(Boolean::parseBoolean)
                .collect(Collectors.toList());

        filters.setMinPrice(newMinPrice);
        filters.setMaxPrice(newMaxPrice);
        filters.setProducers(newProducers);
        filters.setActive(newActiveList);

        updateConditions(filters);
    }

    public void updateConditions(AbstractFilters filters) {
        if (filters.getMinPrice() != 0) {
            newConditions.append(DELIMITER_BETWEEN_CONDITIONS)
                    .append(Columns.PRODUCTS_PRICE + " > ")
                    .append(filters.getMinPrice());

        }

        if (filters.getMaxPrice() != 0) {
            newConditions.append(DELIMITER_BETWEEN_CONDITIONS)
                    .append(Columns.PRODUCTS_PRICE + " < ")
                    .append(filters.getMaxPrice());
        }

        if (filters.getProducers() != null) {
            String allProducersForCondition = String.join(DELIMITER_BETWEEN_WORDS, filters.getProducers());
            newConditions.append(DELIMITER_BETWEEN_CONDITIONS)
                    .append(Columns.PRODUCTS_PRODUCER + " in ('")
                    .append(allProducersForCondition)
                    .append("')");
        }

        if (filters.getActive() != null && !(filters.getActive().contains(true) && filters.getActive().contains(false))) {
            newConditions.append(DELIMITER_BETWEEN_CONDITIONS)
                    .append(Columns.PRODUCTS_ACTIVE + " = ")
                    .append(filters.getActive().contains(true) ? 1 : 0);
        }

        filters.setConditions(newConditions.toString());
        newConditions = new StringBuilder();
    }

    public void applyOrdering(AbstractFilters filters, String orderingBy) {
        filters.setConditionForOrdering(" order by " + ORDERING_CONDITION_MAPPER.get(orderingBy));
        filters.setOrderingName(orderingBy);
    }

    public void resetFilters(AbstractFilters filters) {
        filters.setMinPrice(0);
        filters.setMaxPrice(0);
        filters.setProducers(null);
        filters.setActive(null);

        updateConditions(filters);
    }

    public AbstractFilters getFiltersForUser(User user) {
        throw new IllegalArgumentException("not implemented in parent");
    }

    @Override
    public AllValuesForFilters getAllValuesForFilters() {
        return new AllValuesForFilters();
    }
}
