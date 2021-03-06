package com.epam.eshop.dao;

import com.epam.eshop.entity.KeyboardProduct;
import com.epam.eshop.entity.Product;
import com.epam.eshop.filter.AbstractFilters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by artch on 22.09.2020.
 */
public class KeyboardProductDAO extends BaseProductDAO {

    private static final String SQL_GET_ALL_KEYBOARDS = "SELECT * FROM products " +
            "join keyboard_products on products.id = keyboard_products.product_id ";
    private static final String SQL_GET_AMOUNT_ALL_KEYBOARDS = "SELECT count(products.id) FROM products " +
            "join keyboard_products on products.id = keyboard_products.product_id ";
    private static final String SQL_GET_KEYBOARD_BY_ID = "SELECT * FROM products " +
            "join keyboard_products on products.id = keyboard_products.product_id " +
            "where products.id = ?";
    private static final String SQL_UPDATE_MONITOR_DATA = "update keyboard_products set " +
            "connection_type = ?, mechanical = ?, light_color = ? where product_id = ?";
    private static final String SQL_ADD_NEW_KEYBOARD = "insert into keyboard_products (product_id, connection_type, mechanical, light_color) " +
            "values (?,?,?,?)";

    private static final Logger LOGGER = LoggerFactory.getLogger(KeyboardProduct.class);

    @Override
    public int getAmountAllProducts(Connection connection, AbstractFilters filters) throws SQLException {
        String query = SQL_GET_AMOUNT_ALL_KEYBOARDS + filters.getConditions();
        int amount = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                amount = resultSet.getInt(1);
            }
        }
        return amount;
    }

    @Override
    public List<Product> getProducts(Connection connection, AbstractFilters filters, int startItem) throws SQLException {
        String query = SQL_GET_ALL_KEYBOARDS + filters.getConditions() + filters.getConditionForOrdering() + " limit ?," + getProductsOnOnePage();
        List<Product> keyboardProducts = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, startItem);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                KeyboardProduct newMonitor = fillMonitorProduct(resultSet);
                keyboardProducts.add(newMonitor);
            }

        }

        return keyboardProducts;
    }

    @Override
    public Product getProductById(Connection connection, String id) throws SQLException {
        KeyboardProduct keyboard = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_KEYBOARD_BY_ID)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                keyboard = fillMonitorProduct(resultSet);
            }

        }
        return keyboard;
    }

    @Override
    public void updateProduct(Connection connection, Map<String, String> values) throws SQLException {
        super.updateProduct(connection,values);
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_MONITOR_DATA)) {
            int numberColumn = 1;
            preparedStatement.setString(numberColumn++, values.get(Columns.KEYBOARD_PRODUCTS_CONNECTION_TYPE));
            preparedStatement.setString(numberColumn++, values.get(Columns.KEYBOARD_PRODUCTS_MECHANICAL));
            preparedStatement.setString(numberColumn++, values.get(Columns.KEYBOARD_PRODUCTS_LIGHT_COLOR));
            preparedStatement.setString(numberColumn, values.get(Columns.PRODUCTS_ID));
            preparedStatement.execute();
        }
    }

    @Override
    public int addNewProduct(Connection connection, Map<String, String> values) throws SQLException {
        int newProductId = super.addNewProduct(connection, values);

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_NEW_KEYBOARD)) {
            int numberColumn = 1;
            preparedStatement.setInt(numberColumn++, newProductId);
            preparedStatement.setString(numberColumn++, values.get(Columns.KEYBOARD_PRODUCTS_CONNECTION_TYPE));
            preparedStatement.setString(numberColumn++, values.get(Columns.KEYBOARD_PRODUCTS_MECHANICAL));
            preparedStatement.setString(numberColumn, values.get(Columns.KEYBOARD_PRODUCTS_LIGHT_COLOR));
            preparedStatement.execute();
        }
        return newProductId;
    }

    private KeyboardProduct fillMonitorProduct(ResultSet resultSet) throws SQLException {
        KeyboardProduct newKeyboard = new KeyboardProduct();
        super.fillBaseProduct(resultSet, newKeyboard);
        newKeyboard.setConnectionType(resultSet.getString(Columns.KEYBOARD_PRODUCTS_CONNECTION_TYPE));
        newKeyboard.setMechanical(resultSet.getBoolean(Columns.KEYBOARD_PRODUCTS_MECHANICAL));
        newKeyboard.setLightColor(resultSet.getString(Columns.KEYBOARD_PRODUCTS_LIGHT_COLOR));


        LOGGER.info("Monitor - |{}|  fields has been filled", newKeyboard.getName());

        return newKeyboard;
    }

}
