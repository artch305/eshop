package com.epam.eshop.service;

import com.epam.eshop.dao.ProductDAO;
import com.epam.eshop.entity.Category;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by artch on 15.10.2020.
 */
public class ProductDAOFactoryTest {

    @Test
    public void testGetProductDAO() throws Exception {
        // PREDICATE
        ProductDAO productDAO1;
        ProductDAO productDAO2;
        // FUNCTIONALITY
        productDAO1 = ProductDAOFactory.getProductDAO(Category.MONITORS.getDatabaseValue());
        productDAO2 = ProductDAOFactory.getProductDAO(Category.KEYBOARDS.getDatabaseValue());

        // TESTS
        assertNotNull(productDAO1);
        assertNotNull(productDAO2);
    }
}