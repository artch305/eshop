package com.epam.eshop.dao;

import com.epam.eshop.entity.MonitorProduct;
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
public class MonitorProductDAO extends BaseProductDAO {

    private static final String SQL_GET_ALL_MONITORS = "SELECT * FROM products " +
            "join monitor_products on products.id = monitor_products.product_id ";
    private static final String SQL_GET_AMOUNT_ALL_MONITORS = "SELECT count(*) FROM products " +
            "join monitor_products on products.id = monitor_products.product_id ";
    private static final String SQL_GET_MONITOR_BY_ID = "SELECT * FROM products " +
            "join monitor_products on products.id = monitor_products.product_id " +
            "where products.id = ?";
    private static final String SQL_UPDATE_MONITOR_DATA = "update monitor_products " +
            "set diagonal = ?, panel_type = ?, brightness = ? where product_id = ?";
    private static final String SQL_ADD_NEW_MONITOR = "insert into monitor_products (product_id, diagonal, panel_type, brightness) " +
            "values (?,?,?,?)";

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorProduct.class);

    @Override
    public int getAmountAllProducts(Connection connection, AbstractFilters filters) throws SQLException {
        String query = SQL_GET_AMOUNT_ALL_MONITORS + filters.getConditions();
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
        String query = SQL_GET_ALL_MONITORS + filters.getConditions() + filters.getConditionForOrdering() + " limit ?," + getProductsOnOnePage();
        List<Product> monitorProducts = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, startItem);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                MonitorProduct newMonitor = fillMonitorProduct(resultSet);
                monitorProducts.add(newMonitor);
            }

        }
        return monitorProducts;
    }

    @Override
    public Product getProductById(Connection connection, String id) throws SQLException {
        MonitorProduct monitor = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_MONITOR_BY_ID)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                monitor = fillMonitorProduct(resultSet);
            }

        }
        return monitor;
    }

    @Override
    public void updateProduct(Connection connection, Map<String, String> values) throws SQLException {
        super.updateProduct(connection,values);
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_MONITOR_DATA)) {
            int numberColumn = 1;
            preparedStatement.setString(numberColumn++, values.get(Columns.MONITOR_PRODUCTS_DIAGONAL));
            preparedStatement.setString(numberColumn++, values.get(Columns.MONITOR_PRODUCTS_PANEL_TYPE));
            preparedStatement.setString(numberColumn++, values.get(Columns.MONITOR_PRODUCTS_BRIGHTNESS));
            preparedStatement.setString(numberColumn, values.get(Columns.PRODUCTS_ID));
            preparedStatement.execute();
        }
    }

    @Override
    public int addNewProduct(Connection connection, Map<String, String> values) throws SQLException {
        int newProductId = super.addNewProduct(connection, values);

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_NEW_MONITOR)) {
            int numberColumn = 1;
            preparedStatement.setInt(numberColumn++, newProductId);
            preparedStatement.setString(numberColumn++, values.get(Columns.MONITOR_PRODUCTS_DIAGONAL));
            preparedStatement.setString(numberColumn++, values.get(Columns.MONITOR_PRODUCTS_PANEL_TYPE));
            preparedStatement.setString(numberColumn, values.get(Columns.MONITOR_PRODUCTS_BRIGHTNESS));
            preparedStatement.execute();
        }

        return newProductId;
    }

    private MonitorProduct fillMonitorProduct(ResultSet resultSet) throws SQLException {
        MonitorProduct newMonitor = new MonitorProduct();
        super.fillBaseProduct(resultSet, newMonitor);
        newMonitor.setDiagonal(resultSet.getDouble(Columns.MONITOR_PRODUCTS_DIAGONAL));
        newMonitor.setPanelType(resultSet.getString(Columns.MONITOR_PRODUCTS_PANEL_TYPE));
        newMonitor.setBrightness(resultSet.getInt(Columns.MONITOR_PRODUCTS_BRIGHTNESS));

        LOGGER.info("Monitor - |{}|  fields has been filled", newMonitor.getName());

        return newMonitor;
    }


}

