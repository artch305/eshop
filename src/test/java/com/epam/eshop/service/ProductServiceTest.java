package com.epam.eshop.service;

import com.epam.eshop.controller.Util;
import com.epam.eshop.controller.constants.ParameterNames;
import com.epam.eshop.dao.*;
import com.epam.eshop.entity.MonitorProduct;
import com.epam.eshop.entity.Product;
import com.epam.eshop.filter.AbstractFilters;
import com.epam.eshop.filter.FiltersMonitorProducts;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
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
    private HttpServletRequest request;

    @Before
    public void initProductServiceTest() throws Exception {
        connectionManager = mock(ConnectionManager.class);
        productDAO = mock(MonitorProductDAO.class);
        request = mock(HttpServletRequest.class);
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
        String newProducer = "NEW_SAMSUNG";
        String newName = "23dsad";
        MonitorProductDAO monitorProductDAO = new MonitorProductDAO();
        Product product = monitorProductDAO.getProductById(connection, "1");
        MonitorProduct monitor = (MonitorProduct) product;

        when(request.getParameter(ParameterNames.PRODUCT_ID)).thenReturn(String.valueOf(product.getId()));
        when(request.getParameter(ParameterNames.CATEGORY)).thenReturn(product.getCategory().getDatabaseValue());
        when(request.getParameter(ParameterNames.PRODUCER)).thenReturn(newProducer);
        when(request.getParameter(ParameterNames.NAME)).thenReturn(newName);
        when(request.getParameter(ParameterNames.PRICE)).thenReturn(String.valueOf(product.getPrice()));
        when(request.getParameter(ParameterNames.DESCRIPTION)).thenReturn(product.getDescription());
        when(request.getParameter(ParameterNames.ACTIVE)).thenReturn(String.valueOf(product.isActive()));
        when(request.getParameter(ParameterNames.DIAGONAL)).thenReturn(String.valueOf(monitor.getDiagonal()));
        when(request.getParameter(ParameterNames.PANEL_TYPE)).thenReturn(monitor.getPanelType());
        when(request.getParameter(ParameterNames.BRIGHTNESS)).thenReturn(String.valueOf(monitor.getBrightness()));
        when(request.getParameter(ParameterNames.IMG_URL)).thenReturn(product.getImgURL());
        when(connectionManager.getConnection()).thenReturn(connection);
        // FUNCTIONALITY
        productService.updateProductData(request);
        Product changedProduct = monitorProductDAO.getProductById(connection,"1");
        // TESTS
        verify(connectionManager).getConnection();
        assertEquals(newProducer, changedProduct.getProducer());
        assertEquals(newName, changedProduct.getName());
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