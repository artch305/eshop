package com.epam.eshop.filter;

import com.epam.eshop.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * Created by artch on 30.09.2020.
 */
public interface FilterService {

    AbstractFilters getFiltersForUser(User user);

    String getFiltersCategory(); // TODO: 14.10.2020 remove or use

    void updateFilters(AbstractFilters filters, HttpServletRequest request);

    void resetFilters(AbstractFilters filters);

    void applyOrdering(AbstractFilters filters, String orderingBy);

    Map<String, List<String>> getAllValuesForFilters(AbstractFilters filters); // TODO: 14.10.2020 try to use object in return value instead of map of lists


}
