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
 * Created by artch on 30.09.2020.
 */
public class AllFilterValuesForKeyboards {

    private static Map<String, List<String>> allValuesForFilters;

    public static final String SQL_GET_ALL_LIGHT_COLORS = "select distinct light_color from keyboard_products";
    public static final String SQL_GET_ALL_PRODUCERS = "select distinct producer from products where category = '" + Category.KEYBOARDS.getDatabaseValue() + "'";
    public static final String SQL_GET_ALL_CONNECTION_TYPES = "select distinct connection_type from keyboard_products";

    private static final Logger LOGGER = LoggerFactory.getLogger(AllFilterValuesForKeyboards.class);

    private static List<String> getAllLightColors() { // TODO: 29.09.2020 synchronization
        List<String> colors = new ArrayList<>();

        try (Connection connection = ConnectionManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_LIGHT_COLORS)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                colors.add(resultSet.getString(Columns.KEYBOARD_PRODUCTS_LIGHT_COLOR));
            }

            LOGGER.info("All values for colors has been read: |{}|", colors);
        } catch (SQLException e) {
            LOGGER.error("Can't obtain values for colors", e);
        }

        return colors;
    }

    private static List<String> getAllProducers() { // TODO: 29.09.2020 synchronization
        List<String> producers = new ArrayList<>();

        try (Connection connection = ConnectionManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_PRODUCERS)) {
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

    private static List<String> getAllConnectionTypes() { // TODO: 29.09.2020 synchronization
        List<String> connectionsTypes = new ArrayList<>();

        try (Connection connection = ConnectionManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_CONNECTION_TYPES)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                connectionsTypes.add(resultSet.getString(Columns.KEYBOARD_PRODUCTS_CONNECTION_TYPE));
            }

            LOGGER.info("All values for colors has been read: |{}|", connectionsTypes);
        } catch (SQLException e) {
            LOGGER.error("Can't obtain values for colors", e);
        }

        return connectionsTypes;
    }

    public static Map<String, List<String>> getAllValuesForFilters() {
        if (allValuesForFilters == null) {
            allValuesForFilters = new HashMap<>();
            allValuesForFilters.put("lightColors", getAllLightColors());
            allValuesForFilters.put("producers", getAllProducers());
            allValuesForFilters.put("connectionTypes", getAllConnectionTypes());
        }

        return allValuesForFilters;
    }
}
