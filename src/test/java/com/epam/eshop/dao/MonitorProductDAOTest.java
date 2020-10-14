package com.epam.eshop.dao;

import com.epam.eshop.entity.Category;
import com.epam.eshop.entity.MonitorProduct;
import com.epam.eshop.entity.Product;
import com.epam.eshop.entity.User;
import com.epam.eshop.filter.AbstractFilters;
import com.epam.eshop.filter.FilterService;
import com.epam.eshop.filter.service.FilterServiceFactory;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by artch on 14.10.2020.
 */
public class MonitorProductDAOTest extends AbstractTest {

    @Test
    public void testGetAmountAllProducts() throws Exception {
        // PREDICATE
        MonitorProductDAO monitorProductDAO = new MonitorProductDAO();
        FilterService filterService = FilterServiceFactory.getFilterService(Category.MONITORS.getDatabaseValue());
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserById(connection,1);
        AbstractFilters filters = filterService.getFiltersForUser(user);

        // FUNCTIONALITY
        int amountMonitors = monitorProductDAO.getAmountAllProducts(connection,filters);

        // TESTS
        assertNotEquals(0,amountMonitors);
    }

    @Test
    public void testGetProducts() throws Exception {
        // PREDICATE
        MonitorProductDAO monitorProductDAO = new MonitorProductDAO();
        FilterService filterService = FilterServiceFactory.getFilterService(Category.MONITORS.getDatabaseValue());
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserById(connection,1);
        AbstractFilters filters = filterService.getFiltersForUser(user);
        int startItem = 0;
        int productsOnPage = 10;
        // FUNCTIONALITY
        List<Product> products = monitorProductDAO.getProducts(connection,filters,startItem);
        monitorProductDAO.setProductsOnOnePage(productsOnPage);
        List<Product> products2 = monitorProductDAO.getProducts(connection,filters,startItem);
        // TESTS
        assertEquals(5,products.size());
        assertEquals(productsOnPage,products2.size());
    }

    @Test
    public void testGetProductById() throws Exception {
        // PREDICATE
        MonitorProductDAO monitorProductDAO = new MonitorProductDAO();
        String productId = "1";

        // FUNCTIONALITY
        Product product = monitorProductDAO.getProductById(connection,productId);

        // TESTS
        assertEquals("Samsung" ,product.getProducer());
        assertEquals("S22R350FHN" ,product.getName());
    }

    @Test
    public void testUpdateProduct() throws Exception {
        // PREDICATE
        MonitorProductDAO monitorProductDAO = new MonitorProductDAO();
        String productId = "3";
        Product product = monitorProductDAO.getProductById(connection, productId);
        String newProductName = "qwe";
        String newProductDescription = "any description";
        Map<String,String> values = new HashMap<String, String>(){{
            put(Columns.PRODUCTS_ID, productId);
            put(Columns.PRODUCTS_CATEGORY, product.getCategory().getDatabaseValue());
            put(Columns.PRODUCTS_PRODUCER, product.getProducer());
            put(Columns.PRODUCTS_NAME, newProductName);
            put(Columns.PRODUCTS_PRICE, String.valueOf(product.getPrice()));
            put(Columns.PRODUCTS_DESCRIPTION, newProductDescription);
            put(Columns.PRODUCTS_ACTIVE, String.valueOf(1));
            put(Columns.MONITOR_PRODUCTS_DIAGONAL, "24");
            put(Columns.MONITOR_PRODUCTS_PANEL_TYPE, "");
            put(Columns.MONITOR_PRODUCTS_BRIGHTNESS, "1");
            put(Columns.PRODUCTS_IMG_URL, "");
        }};
        // FUNCTIONALITY
        monitorProductDAO.updateProduct(connection, values);
        Product changedProduct = monitorProductDAO.getProductById(connection, productId);

        // TESTS
        assertEquals(newProductName, changedProduct.getName());
        assertEquals(newProductName, changedProduct.getName());
        assertEquals(newProductDescription, changedProduct.getDescription());
    }

    @Test
    public void testAddNewProduct() throws Exception {
        // PREDICATE
        String producer = "some producer";
        String name = "some name";
        String price = "256.12";
        String description = "";
        String active = "1";
        String diagonal = "27.0";
        String panelType = "IPS";
        String brightness = "200";
        String imgUrl = "";

        MonitorProductDAO monitorProductDAO = new MonitorProductDAO();
        Map<String,String> values = new HashMap<String, String>(){{
            put(Columns.PRODUCTS_CATEGORY, Category.MONITORS.getDatabaseValue());
            put(Columns.PRODUCTS_PRODUCER, producer);
            put(Columns.PRODUCTS_NAME, name);
            put(Columns.PRODUCTS_PRICE, price);
            put(Columns.PRODUCTS_DESCRIPTION, description);
            put(Columns.PRODUCTS_ACTIVE, active);
            put(Columns.MONITOR_PRODUCTS_DIAGONAL, diagonal);
            put(Columns.MONITOR_PRODUCTS_PANEL_TYPE, panelType);
            put(Columns.MONITOR_PRODUCTS_BRIGHTNESS, brightness);
            put(Columns.PRODUCTS_IMG_URL, imgUrl);
        }};
        // FUNCTIONALITY
        int newMonitorId = monitorProductDAO.addNewProduct(connection,values);
        Product newProduct = monitorProductDAO.getProductById(connection,String.valueOf(newMonitorId));

        // TESTS
        assertNotEquals(0, newMonitorId);
        assertEquals(producer, newProduct.getProducer());
        assertEquals(name, newProduct.getName());
        assertEquals(price, String.valueOf(newProduct.getPrice()));
        assertEquals(description, newProduct.getDescription());
        MonitorProduct monitorProduct = (MonitorProduct) newProduct;
        assertEquals(diagonal, String.valueOf(monitorProduct.getDiagonal()));
        assertEquals(panelType, monitorProduct.getPanelType());
        assertEquals(brightness, String.valueOf(monitorProduct.getBrightness()));
    }
}