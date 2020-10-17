package com.epam.eshop.dao;
import com.epam.eshop.entity.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by artch on 16.10.2020.
 */
public class AllValuesForFilters {

    private static final String SQL_GET_ALL_PRODUCERS_FOR_MONITORS = "select distinct producer from products where category = 'monitors'";
    private static final String SQL_GET_ALL_PRODUCERS_FOR_KEYBOARDS = "select distinct producer from products where category = 'keyboards'";
    private static final String SQL_GET_ALL_PANEL_TYPES = "select distinct panel_type from monitor_products";
    private static final String SQL_GET_ALL_DIAGONALS = "select distinct diagonal from monitor_products";
    private static final String SQL_GET_ALL_LIGHT_COLORS = "select distinct light_color from keyboard_products";
    private static final String SQL_GET_ALL_CONNECTION_TYPES = "select distinct connection_type from keyboard_products";

    private static List<String> producersForMonitors;
    private static List<String> producersForKeyboards;
    private static List<String> panelTypes;
    private static List<String> diagonals;
    private static List<String> lightColors;
    private static List<String> connectionTypes;


    private static final Logger LOGGER = LoggerFactory.getLogger(AllValuesForFilters.class);

    public static void resetAllValuesForCategory (String categoryName){
        Category category = Category.getCategoryByName(categoryName);

        if (Category.MONITORS == category){
            producersForMonitors = null;
            panelTypes = null;
            diagonals = null;
        } else if (Category.KEYBOARDS == category){
            producersForKeyboards = null;
            lightColors = null;
            connectionTypes = null;
        }
    }

    public List<String> getProducersForMonitors() {
        if (producersForMonitors == null){
            producersForMonitors = getValues(SQL_GET_ALL_PRODUCERS_FOR_MONITORS, Columns.PRODUCTS_PRODUCER);
        }

        return producersForMonitors;
    }

    public List<String> getProducersForKeyboards() {
        if(producersForKeyboards == null){
            producersForKeyboards = getValues(SQL_GET_ALL_PRODUCERS_FOR_KEYBOARDS, Columns.PRODUCTS_PRODUCER);
        }

        return producersForKeyboards;
    }

    public List<String> getPanelTypes() {
        if (panelTypes == null){
            panelTypes = getValues(SQL_GET_ALL_PANEL_TYPES, Columns.MONITOR_PRODUCTS_PANEL_TYPE);
        }

        return panelTypes;
    }

    public List<String> getDiagonals() {
        if (diagonals == null){
            diagonals = getValues(SQL_GET_ALL_DIAGONALS, Columns.MONITOR_PRODUCTS_DIAGONAL);
        }

        return diagonals;
    }

    public List<String> getLightColors() {
        if (lightColors == null){
            lightColors = getValues(SQL_GET_ALL_LIGHT_COLORS, Columns.KEYBOARD_PRODUCTS_LIGHT_COLOR);
        }

        return lightColors;
    }

    public List<String> getConnectionTypes() {
        if (connectionTypes == null){
            connectionTypes = getValues(SQL_GET_ALL_CONNECTION_TYPES, Columns.KEYBOARD_PRODUCTS_CONNECTION_TYPE);
        }

        return connectionTypes;
    }

    private List<String> getValues (String query, String columnName){
        List<String> values = new ArrayList<>();

        try (Connection connection = ConnectionManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                values.add(resultSet.getString(columnName));
            }
            LOGGER.info("All values  has been read: {}", values);
        } catch (SQLException e) {
            LOGGER.error("Can't obtain values", e);
        }
        values.sort(String::compareTo);

        return values;
    }
}
