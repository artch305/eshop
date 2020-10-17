package com.epam.eshop.service;


import com.epam.eshop.controller.Util;
import com.epam.eshop.controller.constants.AttributesNames;
import com.epam.eshop.controller.constants.ParameterNames;
import com.epam.eshop.dao.AllValuesForFilters;
import com.epam.eshop.dao.Columns;
import com.epam.eshop.dao.ConnectionManager;
import com.epam.eshop.dao.ProductDAO;
import com.epam.eshop.entity.Category;
import com.epam.eshop.entity.Product;
import com.epam.eshop.exception.DBException;
import com.epam.eshop.filter.AbstractFilters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by artch on 30.09.2020.
 */
public class ProductService {

    private final ProductDAO productDAO;
    private final ConnectionManager connectionManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    public ProductService(String category) {
        this(ProductDAOFactory.getProductDAO(category), ConnectionManager.getInstance());
    }

    public ProductService(ProductDAO productDAO, ConnectionManager connectionManager) {
        this.productDAO = productDAO;
        this.connectionManager = connectionManager;
    }

    public List<Product> getProducts(AbstractFilters filters, String page, String ProductsOnPage) {
        if (ProductsOnPage != null && !ProductsOnPage.trim().isEmpty()) {
            productDAO.setProductsOnOnePage(Integer.parseInt(ProductsOnPage));
        }

        int startItem = getNumberOfStartItem(page);
        List<Product> products;

        try (Connection connection = connectionManager.getConnection()) {
            products = productDAO.getProducts(connection, filters, startItem);
        } catch (SQLException e) {
            LOGGER.error("Can't obtain products", e);
            throw new DBException("Can't update product", e);
        }

        return products;
    }

    private int getNumberOfStartItem(String page) {
        int startItem = 0;
        if (page != null && !page.trim().isEmpty()) {
            try {
                int pageInt = Integer.parseInt(page);
                if (pageInt > 0) {
                    startItem = (pageInt - 1) * productDAO.getProductsOnOnePage();
                }
            } catch (NumberFormatException e) {
                LOGGER.error("Can't parse number page to int |{}|", page, e);
                throw new DBException("Incorrect number format for number page", e);
            }
        }
        return startItem;
    }

    public Product getProduct(String productId) {
        Product product;
        try (Connection connection = connectionManager.getConnection()) {
            product = productDAO.getProductById(connection, productId);
        } catch (SQLException e) {
            LOGGER.error("Can't get product |{}|", productId, e);
            throw new DBException("Can't get product", e);
        }
        return product;
    }

    public boolean updateProductData(HttpServletRequest request) {
        Map<String, String> values = getValuesForProducts(request);

        String imgURL = Util.uploadImage(request);

        if (imgURL.isEmpty()) {
            imgURL = request.getParameter(ParameterNames.IMG_URL);
        }

        values.put(Columns.PRODUCTS_IMG_URL, imgURL);

        Connection connection = connectionManager.getConnection();
        try {
            connection.setAutoCommit(false);
            productDAO.updateProduct(connection, values);
            connection.commit();
            request.getSession().setAttribute(AttributesNames.SUCCESS, "success");
            AllValuesForFilters.resetAllValuesForCategory(request.getParameter(ParameterNames.CATEGORY));
            return true;
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Can't update product |{}|", values.get(Columns.PRODUCTS_PRODUCER) + values.get(Columns.PRODUCTS_NAME), e);
            throw new DBException("Can't update product", e);
        }finally {
            applyAutoCommitAndClose(connection);
        }
    }

    public int addNewProduct(HttpServletRequest request) {
        Map<String, String> values = getValuesForProducts(request);
        int newProductId;

        String imgURL = Util.uploadImage(request);
        values.put(Columns.PRODUCTS_IMG_URL, imgURL);

        Connection connection = null;
        try {
            connection = connectionManager.getConnection();
            connection.setAutoCommit(false);
            newProductId = productDAO.addNewProduct(connection, values);
            connection.commit();
            AllValuesForFilters.resetAllValuesForCategory(request.getParameter(ParameterNames.CATEGORY));
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Can't add new product |{}|", values.get(Columns.PRODUCTS_PRODUCER) + values.get(Columns.PRODUCTS_NAME), e);
            throw new DBException("Can't add new product", e);
        } finally {
            if (connection != null){
                applyAutoCommitAndClose(connection);
            }
        }

        return newProductId;
    }

    private Map<String, String> getValuesForProducts(HttpServletRequest request) {
        Map<String, String> values = new HashMap<>();

        String category = request.getParameter(ParameterNames.CATEGORY);
        values.put(Columns.PRODUCTS_ID, request.getParameter(ParameterNames.PRODUCT_ID));
        values.put(Columns.PRODUCTS_CATEGORY, request.getParameter(ParameterNames.CATEGORY));
        values.put(Columns.PRODUCTS_PRODUCER, request.getParameter(ParameterNames.PRODUCER).trim());
        values.put(Columns.PRODUCTS_NAME, request.getParameter(ParameterNames.NAME).trim());
        values.put(Columns.PRODUCTS_PRICE, request.getParameter(ParameterNames.PRICE));
        values.put(Columns.PRODUCTS_DESCRIPTION, request.getParameter(ParameterNames.DESCRIPTION));
        values.put(Columns.PRODUCTS_ACTIVE, request.getParameter(ParameterNames.ACTIVE));

        if (Category.MONITORS.getDatabaseValue().equals(category)) {
            values.put(Columns.MONITOR_PRODUCTS_DIAGONAL, request.getParameter(ParameterNames.DIAGONAL));
            values.put(Columns.MONITOR_PRODUCTS_PANEL_TYPE, request.getParameter(ParameterNames.PANEL_TYPE).trim());
            values.put(Columns.MONITOR_PRODUCTS_BRIGHTNESS, request.getParameter(ParameterNames.BRIGHTNESS));
        } else if (Category.KEYBOARDS.getDatabaseValue().equals(category)) {
            values.put(Columns.KEYBOARD_PRODUCTS_CONNECTION_TYPE, request.getParameter(ParameterNames.CONNECTION_TYPE).trim());
            values.put(Columns.KEYBOARD_PRODUCTS_LIGHT_COLOR, request.getParameter(ParameterNames.LIGHT_COLOR).trim());
            values.put(Columns.KEYBOARD_PRODUCTS_MECHANICAL, request.getParameter(ParameterNames.MECHANICAL));
        }

        return values;
    }

    public int getMaxItems(AbstractFilters currentUserFilters) {
        int maxItems = 0;

        try (Connection connection = connectionManager.getConnection()) {
            maxItems = productDAO.getAmountAllProducts(connection, currentUserFilters);
        } catch (SQLException e) {
            LOGGER.error("Can't get maxItems", e);
            throw new DBException("Can't get max amount of products", e);
        }

        return maxItems;
    }



    private void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.error("can't rollback transaction ");
            throw new DBException("Can't rollback connection", e);
        }
    }

    private void applyAutoCommitAndClose(Connection connection) {
        try {
            connection.setAutoCommit(true);
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("problem in setAutoCommit and closing connection", e);
            throw new DBException("Can't set autocommit and close product", e);
        }
    }
}
