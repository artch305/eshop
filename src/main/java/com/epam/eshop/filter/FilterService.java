package com.epam.eshop.filter;

import com.epam.eshop.dao.AllValuesForFilters;
import com.epam.eshop.entity.User;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by artch on 30.09.2020.
 */
public interface FilterService {

    AbstractFilters getFiltersForUser(User user);

    void updateFilters(AbstractFilters filters, HttpServletRequest request);

    void resetFilters(AbstractFilters filters);

    void applyOrdering(AbstractFilters filters, String orderingBy);

    AllValuesForFilters getAllValuesForFilters(AbstractFilters filters);


}
