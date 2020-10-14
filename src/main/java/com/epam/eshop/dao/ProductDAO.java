package com.epam.eshop.dao;

import com.epam.eshop.entity.Product;
import com.epam.eshop.filter.AbstractFilters;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by artch on 30.09.2020.
 */
public interface ProductDAO {

    int getAmountAllProducts(Connection connection, AbstractFilters filters) throws SQLException;

    List<Product> getProducts(Connection connection, AbstractFilters filters, int page) throws SQLException;

    Product getProductById(Connection connection, String id) throws SQLException;

    void updateProduct(Connection connection, Map<String, String> values) throws SQLException;

    int addNewProduct(Connection connection, Map<String, String> values) throws SQLException;

    void setProductsOnOnePage(int productsOnOnePage);

    int getProductsOnOnePage();
}
