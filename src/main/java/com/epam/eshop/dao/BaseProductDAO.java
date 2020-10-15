package com.epam.eshop.dao;

import com.epam.eshop.entity.Product;
import com.epam.eshop.filter.AbstractFilters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;
import java.util.Map;


/**
 * Created by artch on 05.10.2020.
 */
public class BaseProductDAO implements ProductDAO {

    private int productsOnOnePage = 5;

    private static final String SQL_GET_PRODUCT_BY_ID = "select * from products where id = ?";
    private static final String SQL_ADD_BASE_PRODUCT = "insert into products (category, producer, name, price, description, active,  img_url) " +
            "values (?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE_BASE_PRODUCT = "update products set category = ?, producer = ?, name = ?, price = ?, " +
            "description = ?, active = ?, img_url =? where id = ?";

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseProductDAO.class);

    public int getProductsOnOnePage() {
        return productsOnOnePage;
    }

    public void setProductsOnOnePage(int productsOnOnePage) {
        this.productsOnOnePage = productsOnOnePage;
    }

    @Override
    public int getAmountAllProducts(Connection connection, AbstractFilters filters) throws SQLException {
        throw new IllegalArgumentException("Not implemented");
    }

    @Override
    public List<Product> getProducts(Connection connection, AbstractFilters filters, int startItem) throws SQLException {
        throw new IllegalArgumentException("Not implemented");
    }

    @Override
    public void updateProduct(Connection connection, Map<String, String> values) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_BASE_PRODUCT)) {
            fillBaseValueToPreparedStatement(values, preparedStatement);
            preparedStatement.setString(8, values.get(Columns.PRODUCTS_ID));  
            preparedStatement.execute();
        }
    }

    @Override
    public int addNewProduct(Connection connection, Map<String, String> values) throws SQLException {
        int newProductId = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_BASE_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
            fillBaseValueToPreparedStatement(values, preparedStatement);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                newProductId = resultSet.getInt(1);
            }
        }
        return newProductId;
    }

    @Override
    public Product getProductById(Connection connection, String id) throws SQLException {
        Product product = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_PRODUCT_BY_ID)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                product = new Product();
                fillBaseProduct(resultSet, product);
            }

        }
        return product;
    }

    void fillBaseProduct(ResultSet resultSet, Product product) throws SQLException {
        product.setId(resultSet.getInt(Columns.PRODUCTS_ID));
        product.setCategory(resultSet.getString(Columns.PRODUCTS_CATEGORY));
        product.setProducer(resultSet.getString(Columns.PRODUCTS_PRODUCER));
        product.setName(resultSet.getString(Columns.PRODUCTS_NAME));
        product.setPrice(resultSet.getDouble(Columns.PRODUCTS_PRICE));
        product.setDescription(resultSet.getString(Columns.PRODUCTS_DESCRIPTION));
        product.setAddedDate(resultSet.getString(Columns.PRODUCTS_ADDED_DATE));
        product.setImgURL(resultSet.getString(Columns.PRODUCTS_IMG_URL));
        product.setActive(resultSet.getBoolean(Columns.PRODUCTS_ACTIVE));
        LOGGER.info("Product |{}| has been filled", product.getProducer()+ " " + product.getName());
    }

    private void fillBaseValueToPreparedStatement(Map<String, String> values, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, values.get(Columns.PRODUCTS_CATEGORY));
        preparedStatement.setString(2, values.get(Columns.PRODUCTS_PRODUCER));
        preparedStatement.setString(3, values.get(Columns.PRODUCTS_NAME));
        preparedStatement.setString(4, values.get(Columns.PRODUCTS_PRICE));
        preparedStatement.setString(5, values.get(Columns.PRODUCTS_DESCRIPTION));
        preparedStatement.setString(6, values.get(Columns.PRODUCTS_ACTIVE));
        preparedStatement.setString(7, values.get(Columns.PRODUCTS_IMG_URL));
        LOGGER.info("prepareStatement has been filled by |{}|", values.get(Columns.PRODUCTS_PRODUCER) + " " +
                values.get(Columns.PRODUCTS_NAME));
    }
}
