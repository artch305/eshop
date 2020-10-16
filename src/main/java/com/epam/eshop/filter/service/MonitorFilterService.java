package com.epam.eshop.filter.service;

import com.epam.eshop.dao.AllFilterValuesForMonitors;
import com.epam.eshop.dao.Columns;
import com.epam.eshop.entity.Category;
import com.epam.eshop.entity.User;
import com.epam.eshop.entity.UserRole;
import com.epam.eshop.filter.AbstractFilters;
import com.epam.eshop.filter.FiltersMapper;
import com.epam.eshop.filter.FiltersMonitorProducts;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by artch on 12.10.2020.
 */
public class MonitorFilterService extends BaseFilterService {

    @Override
    public void updateFilters(AbstractFilters filters, HttpServletRequest request) {
        FiltersMonitorProducts filtersMonitor = (FiltersMonitorProducts) filters;

        String newBrightnessArray = request.getParameter("brightness");
        int newBrightness = newBrightnessArray.isEmpty() ? 0 : Integer.parseInt(newBrightnessArray);

        String[] newDiagonalsArray = request.getParameterValues("diagonals");
        List<String> newDiagonals = newDiagonalsArray == null ? null : Arrays.asList(newDiagonalsArray);

        String[] newPanelTypesArray = request.getParameterValues("panelTypes");
        List<String> newPanelTypes = newPanelTypesArray == null ? null : Arrays.asList(newPanelTypesArray);

        filtersMonitor.setBrightness(newBrightness);
        filtersMonitor.setDiagonals(newDiagonals);
        filtersMonitor.setPanelTypes(newPanelTypes);

        super.updateFilters(filters, request);
    }

    @Override
    public void updateConditions(AbstractFilters filters) {
        FiltersMonitorProducts filtersMonitor = (FiltersMonitorProducts) filters;

        if (filtersMonitor.getBrightness() != 0) {
            newConditions.append(DELIMITER_BETWEEN_CONDITIONS)
                    .append(Columns.MONITOR_PRODUCTS_BRIGHTNESS + " = ")
                    .append(filtersMonitor.getBrightness());
        }

        if (filtersMonitor.getDiagonals() != null) {
            String allDiagonalsForCondition = String.join(", ", filtersMonitor.getDiagonals());
            newConditions.append(DELIMITER_BETWEEN_CONDITIONS)
                    .append(Columns.MONITOR_PRODUCTS_DIAGONAL + " in (")
                    .append(allDiagonalsForCondition)
                    .append(")");
        }

        if (filtersMonitor.getPanelTypes() != null) {
            String allPanelTypesForCondition = String.join(DELIMITER_BETWEEN_WORDS, filtersMonitor.getPanelTypes());
            newConditions.append(DELIMITER_BETWEEN_CONDITIONS)
                    .append(Columns.MONITOR_PRODUCTS_PANEL_TYPE + " in ('")
                    .append(allPanelTypesForCondition)
                    .append("')");
        }

        super.updateConditions(filters);
    }

    @Override
    public Map<String, List<String>> getAllValuesForFilters(AbstractFilters filters) {
        return AllFilterValuesForMonitors.getAllValuesForFilters();
    }

    @Override
    public void resetFilters(AbstractFilters filters) {
        FiltersMonitorProducts filtersMonitor = (FiltersMonitorProducts) filters;
        filtersMonitor.setDiagonals(null);
        filtersMonitor.setPanelTypes(null);
        filtersMonitor.setBrightness(0);

        super.resetFilters(filters);
    }

    @Override
    public AbstractFilters getFiltersForUser(User user) {
        AbstractFilters filters = FiltersMapper.getFilters(Category.MONITORS.getDatabaseValue());

        if (user == null || UserRole.CUSTOMER.equals(user.getUserRole())) {
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
        return Category.MONITORS.getDatabaseValue();
    }
}
