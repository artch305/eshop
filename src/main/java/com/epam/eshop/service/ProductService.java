package com.epam.eshop.service;


import com.epam.eshop.controller.exception.DBException;
import com.epam.eshop.dao.Columns;
import com.epam.eshop.dao.ConnectionManager;
import com.epam.eshop.dao.ProductDAO;
import com.epam.eshop.entity.Category;
import com.epam.eshop.entity.Product;
import com.epam.eshop.filter.AbstractFilters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by artch on 30.09.2020.
 */
public class ProductService {

    private ProductDAO productDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    public ProductService(String category) {
        productDAO = ProductDAOFactory.getProductDAO(category);
    }


    public List<Product> getProducts(AbstractFilters filters, String page, String ProductsOnPage) {
        int startItem;

        if (ProductsOnPage != null && !ProductsOnPage.trim().isEmpty()) {
            productDAO.setProductsOnOnePage(Integer.parseInt(ProductsOnPage));
        }

        List<Product> products = new ArrayList<>();
        startItem = getNumberOfStartItem(page);

        try (Connection connection = ConnectionManager.getInstance().getConnection()) {
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
        Product product = null;
        try (Connection connection = ConnectionManager.getInstance().getConnection()) {
            product = productDAO.getProductById(connection, productId);
        } catch (SQLException e) {
            LOGGER.error("Can't get product |{}|", productId, e);
            throw new DBException("Can't get product", e);
        }
        return product;
    }

    public boolean updateProductData(HttpServletRequest request) {
        Map<String, String> values = getValuesForProducts(request);

        String imgURL = uploadImage(request);

        if (imgURL.isEmpty()) {
            imgURL = request.getParameter("imgURL");
        }

        values.put(Columns.PRODUCTS_IMG_URL, imgURL);

        try (Connection connection = ConnectionManager.getInstance().getConnection()) {
            productDAO.updateProduct(connection, values);
            return true;
        } catch (SQLException e) {
            LOGGER.error("Can't update product |{}|", values.get(Columns.PRODUCTS_PRODUCER) + values.get(Columns.PRODUCTS_NAME), e);
            throw new DBException("Can't update product", e);
        }
    }

    public int addNewProduct(HttpServletRequest request) {
        Map<String, String> values = getValuesForProducts(request);
        int newProductId = 0;

        String imgURL = uploadImage(request);
        values.put(Columns.PRODUCTS_IMG_URL, imgURL);

        Connection connection = ConnectionManager.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            newProductId = productDAO.addNewProduct(connection, values);
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Can't add new product |{}|", values.get(Columns.PRODUCTS_PRODUCER) + values.get(Columns.PRODUCTS_NAME), e);
            throw new DBException("Can't add new product", e);
        } finally {
            applyAutoCommitAndClose(connection);
        }

        return newProductId;
    }

    private Map<String, String> getValuesForProducts(HttpServletRequest request) {
        Map<String, String> values = new HashMap<>();

        String category = request.getParameter("category");
        values.put(Columns.PRODUCTS_ID, request.getParameter("productId"));
        values.put(Columns.PRODUCTS_CATEGORY, category);
        values.put(Columns.PRODUCTS_PRODUCER, request.getParameter("producer").trim());
        values.put(Columns.PRODUCTS_NAME, request.getParameter("name").trim());
        values.put(Columns.PRODUCTS_PRICE, request.getParameter("price"));
        values.put(Columns.PRODUCTS_DESCRIPTION, request.getParameter("description"));
        values.put(Columns.PRODUCTS_ACTIVE, request.getParameter("active"));

        if (Category.MONITORS.getDatabaseValue().equals(category)) {
            values.put(Columns.MONITOR_PRODUCTS_DIAGONAL, request.getParameter("diagonal"));
            values.put(Columns.MONITOR_PRODUCTS_PANEL_TYPE, request.getParameter("panelType").trim());
            values.put(Columns.MONITOR_PRODUCTS_BRIGHTNESS, request.getParameter("brightness"));
        } else if (Category.KEYBOARDS.getDatabaseValue().equals(category)) {
            values.put(Columns.KEYBOARD_PRODUCTS_CONNECTION_TYPE, request.getParameter("connectionType").trim());
            values.put(Columns.KEYBOARD_PRODUCTS_LIGHT_COLOR, request.getParameter("lightColor").trim());
            values.put(Columns.KEYBOARD_PRODUCTS_MECHANICAL, request.getParameter("mechanical"));
        }

        return values;
    }

    private String uploadImage(HttpServletRequest request) {
        String path = request.getParameter("destination");
        String newImgURL = "";

        try {
            Part filePart = request.getPart("img");
            String fileName = getFileName(filePart);
            Path dir = Paths.get(path);
            dir.toFile().mkdirs();
            Path createdImageFilePath = dir.resolve(fileName);
            InputStream fileInputStream = filePart.getInputStream();
            Files.copy(fileInputStream, createdImageFilePath, StandardCopyOption.REPLACE_EXISTING);
            //urlImg = path + "/" + fileName;
            newImgURL = createdImageFilePath.toAbsolutePath().toString();
        } catch (IOException | ServletException e) {
            LOGGER.error("Can't update product |{}|", request.getParameter("productId"), e);
            throw new DBException("Upload image for product", e);
        }
        return newImgURL;
    }

    public int getMaxItems(AbstractFilters currentUserFilters) {
        int maxItems = 0;

        try (Connection connection = ConnectionManager.getInstance().getConnection()) {
            maxItems = productDAO.getAmountAllProducts(connection, currentUserFilters);
        } catch (SQLException e) {
            LOGGER.error("Can't get maxItems", e);
            throw new DBException("Can't get max amount of products", e);
        }

        return maxItems;
    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
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
