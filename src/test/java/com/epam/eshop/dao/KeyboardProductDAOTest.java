package com.epam.eshop.dao;

import com.epam.eshop.entity.*;
import com.epam.eshop.filter.AbstractFilters;
import com.epam.eshop.filter.FilterService;
import com.epam.eshop.filter.service.FilterServiceFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by artch on 15.10.2020.
 */
public class KeyboardProductDAOTest extends AbstractTest {

    private static KeyboardProductDAO keyboardProductDAO;

    @BeforeClass
    public static void initKeyboardProductDAOTest (){
        keyboardProductDAO = new KeyboardProductDAO();
    }

    @Test
    public void testGetAmountAllProducts() throws Exception {
        // PREDICATE
        FilterService filterService = FilterServiceFactory.getFilterService(Category.MONITORS.getDatabaseValue());
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserById(connection,1);
        AbstractFilters filters = filterService.getFiltersForUser(user);

        // FUNCTIONALITY
        int amountMonitors = keyboardProductDAO.getAmountAllProducts(connection,filters);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select count(product_id) from keyboard_products group by product_id" );
        int expectedAmount = -1;
        if (resultSet.next()){
            expectedAmount = resultSet.getInt(1);
        }
        // TESTS
        assertNotEquals(expectedAmount,amountMonitors);
    }

    @Test
    public void testGetProducts() throws Exception {
        // PREDICATE
        FilterService filterService = FilterServiceFactory.getFilterService(Category.KEYBOARDS.getDatabaseValue());
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserById(connection,1);
        AbstractFilters filters = filterService.getFiltersForUser(user);
        int startItem = 0;
        int productsOnPage = 10;
        // FUNCTIONALITY
        List<Product> products = keyboardProductDAO.getProducts(connection,filters,startItem);
        keyboardProductDAO.setProductsOnOnePage(productsOnPage);
        List<Product> products2 = keyboardProductDAO.getProducts(connection,filters,startItem);
        keyboardProductDAO.setProductsOnOnePage(5);
        // TESTS
        assertEquals(5,products.size());
        assertEquals(productsOnPage,products2.size());
    }

    @Test
    public void testGetProductById() throws Exception {
        // PREDICATE
        String productId = "2";

        // FUNCTIONALITY
        Product product = keyboardProductDAO.getProductById(connection,productId);

        // TESTS
        assertEquals("Logitech" ,product.getProducer());
        assertEquals("MK270" ,product.getName());

    }

    @Test
    public void testUpdateProduct() throws Exception {
        // PREDICATE
        String productId = "5";
        Product product = keyboardProductDAO.getProductById(connection, productId);
        String newProductName = "qwe";
        String newProductDescription = "any description";
        Map<String,String> values = new HashMap<String, String>(){{
            put(Columns.PRODUCTS_ID, productId);
            put(Columns.PRODUCTS_CATEGORY, product.getCategory().getDatabaseValue());
            put(Columns.PRODUCTS_PRODUCER, product.getProducer());
            put(Columns.PRODUCTS_NAME, newProductName);
            put(Columns.PRODUCTS_PRICE, String.valueOf(product.getPrice()));
            put(Columns.PRODUCTS_DESCRIPTION, newProductDescription);
            put(Columns.PRODUCTS_ACTIVE, "1");
            put(Columns.KEYBOARD_PRODUCTS_CONNECTION_TYPE, "USB");
            put(Columns.KEYBOARD_PRODUCTS_MECHANICAL, "1");
            put(Columns.KEYBOARD_PRODUCTS_LIGHT_COLOR, "RGB");
            put(Columns.PRODUCTS_IMG_URL, "");
        }};
        // FUNCTIONALITY
        keyboardProductDAO.updateProduct(connection, values);
        Product changedProduct = keyboardProductDAO.getProductById(connection, productId);

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
        String connectionType = "PS/2";
        String mechanical = "0";
        String lightColor = "RED";
        String imgUrl = "";

        Map<String,String> values = new HashMap<String, String>(){{
            put(Columns.PRODUCTS_CATEGORY, Category.KEYBOARDS.getDatabaseValue());
            put(Columns.PRODUCTS_PRODUCER, producer);
            put(Columns.PRODUCTS_NAME, name);
            put(Columns.PRODUCTS_PRICE, price);
            put(Columns.PRODUCTS_DESCRIPTION, description);
            put(Columns.PRODUCTS_ACTIVE, active);
            put(Columns.KEYBOARD_PRODUCTS_CONNECTION_TYPE, connectionType);
            put(Columns.KEYBOARD_PRODUCTS_MECHANICAL, mechanical);
            put(Columns.KEYBOARD_PRODUCTS_LIGHT_COLOR, lightColor);
            put(Columns.PRODUCTS_IMG_URL, imgUrl);
        }};
        // FUNCTIONALITY
        int newMonitorId = keyboardProductDAO.addNewProduct(connection,values);
        Product newProduct = keyboardProductDAO.getProductById(connection,String.valueOf(newMonitorId));

        // TESTS
        assertNotEquals(0, newMonitorId);
        assertEquals(producer, newProduct.getProducer());
        assertEquals(name, newProduct.getName());
        assertEquals(price, String.valueOf(newProduct.getPrice()));
        assertEquals(description, newProduct.getDescription());
        KeyboardProduct keyboardProduct = (KeyboardProduct) newProduct;
        assertEquals(connectionType, String.valueOf(keyboardProduct.getConnectionType()));
        assertFalse(keyboardProduct.isMechanical());
        assertEquals(lightColor, String.valueOf(keyboardProduct.getLightColor()));
    }
}