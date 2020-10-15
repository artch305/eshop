package com.epam.eshop.service;

import com.epam.eshop.dao.AbstractTest;
import com.epam.eshop.dao.ConnectionManager;
import com.epam.eshop.dao.MonitorProductDAO;
import com.epam.eshop.dao.ProductDAO;
import com.epam.eshop.entity.Product;
import com.epam.eshop.filter.AbstractFilters;
import com.epam.eshop.filter.FiltersMonitorProducts;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by artch on 15.10.2020.
 */
public class ProductServiceTest extends AbstractTest {

    private ProductService productService;

    private ConnectionManager connectionManager;
    private ProductDAO productDAO;

    @Before
    public void initProductServiceTest() throws Exception {
        connectionManager = mock(ConnectionManager.class);
        productDAO = mock(MonitorProductDAO.class);
        productService = new ProductService(productDAO,connectionManager);
    }


    @Test
    public void testGetProducts() throws Exception {
        // PREDICATE
        List<Product> products = new ArrayList<Product>(){{
            add(new Product());
            add(new Product());
            add(new Product());
        }};
        String page = "3";
        String productsOnPage = "10";
        AbstractFilters filters = new FiltersMonitorProducts();
        when(connectionManager.getConnection()).thenReturn(connection);
        when(productDAO.getProducts(connection, filters, 20)).thenReturn(products);
        when(productDAO.getProductsOnOnePage()).thenReturn(10);
        // FUNCTIONALITY
        List<Product> actualProducts = productService.getProducts(filters,page,productsOnPage);
        // TESTS
        verify(connectionManager).getConnection();
        verify(productDAO).setProductsOnOnePage(Integer.parseInt(productsOnPage));
        verify(productDAO).getProducts(connection,filters,20);
        assertEquals(products, actualProducts);
    }

    @Test
    public void testGetProduct() throws Exception {
        // PREDICATE
        Product product = new Product();
        String productId = "1";
        product.setId(1);
        when(connectionManager.getConnection()).thenReturn(connection);
        when(productDAO.getProductById(connection, productId)).thenReturn(product);
        // FUNCTIONALITY
        Product actualProduct = productService.getProduct(productId);
        // TESTS
        verify(connectionManager).getConnection();
        verify(productDAO).getProductById(connection, productId);
        assertEquals(product, actualProduct);
    }

    @Test
    public void testUpdateProductData() throws Exception {
        // PREDICATE

        // FUNCTIONALITY
        fail();

        // TESTS
    }

    @Test
    public void testAddNewProduct() throws Exception {
        // PREDICATE

        // FUNCTIONALITY
        fail();

        // TESTS
    }

    @Test
    public void testGetMaxItems() throws Exception {
        // PREDICATE
        AbstractFilters filters = new FiltersMonitorProducts();
        int maxItems = 5;
        when(connectionManager.getConnection()).thenReturn(connection);
        when(productDAO.getAmountAllProducts(connection,filters)).thenReturn(maxItems);
        // FUNCTIONALITY
        int actualMaxItems = productService.getMaxItems(filters);

        // TESTS
        verify(connectionManager).getConnection();
        verify(productDAO).getAmountAllProducts(connection, filters);
        assertEquals(maxItems, actualMaxItems);
    }
}