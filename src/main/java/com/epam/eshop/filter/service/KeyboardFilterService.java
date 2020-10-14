package com.epam.eshop.filter.service;

import com.epam.eshop.dao.AllFilterValuesForKeyboards;
import com.epam.eshop.dao.Columns;
import com.epam.eshop.entity.Category;
import com.epam.eshop.entity.User;
import com.epam.eshop.entity.UserRole;
import com.epam.eshop.filter.AbstractFilters;
import com.epam.eshop.filter.FiltersKeyboardProducts;
import com.epam.eshop.filter.FiltersMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by artch on 12.10.2020.
 */
public class KeyboardFilterService extends BaseFilterService {
    @Override
    public void updateFilters(AbstractFilters filters, HttpServletRequest request) {
        FiltersKeyboardProducts filtersKeyboard = (FiltersKeyboardProducts) filters;

        String[] newConnectionTypesArray = request.getParameterValues("connectionTypes");
        List<String> newConnectionTypes = newConnectionTypesArray == null ? null : Arrays.asList(newConnectionTypesArray);

        String[] newMechanicalArray = request.getParameterValues("mechanical");

        List<Boolean> newMechanicalList = newMechanicalArray == null ? null : Arrays.stream(newMechanicalArray)
                .map(Boolean::parseBoolean)
                .collect(Collectors.toList());

        String[] newLightColorsArray = request.getParameterValues("lightColors");
        List<String> newLightColors = newLightColorsArray == null ? null : Arrays.asList(newLightColorsArray);

        filtersKeyboard.setConnectionTypes(newConnectionTypes);
        filtersKeyboard.setMechanical(newMechanicalList);
        filtersKeyboard.setLightColors(newLightColors);

        super.updateFilters(filters, request);
    }

    @Override
    public void updateConditions(AbstractFilters filters) {
        FiltersKeyboardProducts filtersKeyboards = (FiltersKeyboardProducts) filters;

        if (filtersKeyboards.getMechanical() != null && !(filtersKeyboards.getMechanical().contains(true) && filtersKeyboards.getMechanical().contains(false))) {
            newConditions.append(DELIMITER_BETWEEN_CONDITIONS)
                    .append(Columns.KEYBOARD_PRODUCTS_MECHANICAL + " = ")
                    .append(filtersKeyboards.getMechanical().contains(true) ? 1 : 0);
        }

        if (filtersKeyboards.getConnectionTypes() != null) {
            String allConnectionTypesForCondition = String.join(DELIMITER_BETWEEN_WORDS, filtersKeyboards.getConnectionTypes());
            newConditions.append(DELIMITER_BETWEEN_CONDITIONS)
                    .append(Columns.KEYBOARD_PRODUCTS_CONNECTION_TYPE + " in ('")
                    .append(allConnectionTypesForCondition)
                    .append("')");
        }

        if (filtersKeyboards.getLightColors() != null) {
            String allLightColorsForCondition = String.join(DELIMITER_BETWEEN_WORDS, filtersKeyboards.getLightColors());
            newConditions.append(DELIMITER_BETWEEN_CONDITIONS)
                    .append(Columns.KEYBOARD_PRODUCTS_LIGHT_COLOR + " in ('")
                    .append(allLightColorsForCondition)
                    .append("')");
        }

        super.updateConditions(filters);
    }

    @Override
    public Map<String, List<String>> getAllValuesForFilters(AbstractFilters filters) {
        return AllFilterValuesForKeyboards.getAllValuesForFilters();
    }

    @Override
    public void resetFilters(AbstractFilters filters) {
        FiltersKeyboardProducts filtersKeyboard = (FiltersKeyboardProducts) filters;
        filtersKeyboard.setConnectionTypes(null);
        filtersKeyboard.setMechanical(null);
        filtersKeyboard.setLightColors(null);
        super.resetFilters(filters);
    }

    @Override
    public AbstractFilters getFiltersForUser(User user) {
        AbstractFilters filters = FiltersMapper.getFilters(Category.KEYBOARDS.getDatabaseValue());

        if (user == null || UserRole.CUSTOMER.equals(user.getUserRole().getRole())) {
            List<Boolean> active = new ArrayList<>();
            active.add(true);
            filters.setActive(active);
            updateConditions(filters);
        }

        applyOrdering(filters, ORDERING_FOR_DEFAULT);

        return filters;
    }

    @Override
    public String getFiltersCategory() {
        return Category.KEYBOARDS.getDatabaseValue();
    }
}
