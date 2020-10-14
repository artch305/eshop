package com.epam.eshop.dao;

import com.epam.eshop.entity.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by artch on 29.09.2020.
 */
public class AllFilterValuesForMonitors {

    private static Map<String, List<String>> allValuesForFilters;

    private static final String SQL_GET_ALL_PANEL_TYPES = "select distinct panel_type from monitor_products";
    private static final String SQL_GET_ALL_DIAGONALS = "select distinct diagonal from monitor_products";
    private static final String SQL_SELECT_ALL_PRODUCERS = "select distinct producer from products where category = '" + Category.MONITORS.getDatabaseValue() + "'";

    private static final Logger LOGGER = LoggerFactory.getLogger(AllFilterValuesForMonitors.class);

    private static List<String> getAllPanelTypes() { // TODO: 29.09.2020 synchronization
        List<String> panelTypes = new ArrayList<>();

        try (Connection connection = ConnectionManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_PANEL_TYPES)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                panelTypes.add(resultSet.getString(Columns.MONITOR_PRODUCTS_PANEL_TYPE));
            }
            LOGGER.info("All values for panelTypes has been read: {}", panelTypes);
        } catch (SQLException e) {
            LOGGER.error("Can't obtain values for panelTypes", e);
        }

        return panelTypes;
    }

    private static List<String> getAllDiagonals() {
        List<String> diagonals = new ArrayList<>();

        try (Connection connection = ConnectionManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_DIAGONALS)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                diagonals.add(resultSet.getString(Columns.MONITOR_PRODUCTS_DIAGONAL));
            }

            diagonals.sort(String::compareTo);
            LOGGER.info("All values for diagonals has been read: {}", diagonals);
        } catch (SQLException e) {
            LOGGER.error("Can't obtain values for diagonals", e);
        }


        return diagonals;
    }

    private static List<String> getAllProducers() { // TODO: 29.09.2020 synchronization
        List<String> producers = new ArrayList<>();

        try (Connection connection = ConnectionManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_PRODUCERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                producers.add(resultSet.getString(Columns.PRODUCTS_PRODUCER));
            }

            LOGGER.info("All values for colors has been read: |{}|", producers);
        } catch (SQLException e) {
            LOGGER.error("Can't obtain values for colors", e);
        }

        return producers;
    }

    public static Map<String, List<String>> getAllValuesForFilters() {
        if (allValuesForFilters == null) {
            allValuesForFilters = new HashMap<>();
            allValuesForFilters.put("panelTypes", getAllPanelTypes());
            allValuesForFilters.put("diagonals", getAllDiagonals());
            allValuesForFilters.put("producers", getAllProducers());
        }

        return allValuesForFilters;
    }

}
