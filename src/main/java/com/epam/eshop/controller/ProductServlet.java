package com.epam.eshop.controller;

import com.epam.eshop.controller.constants.AttributesNames;
import com.epam.eshop.controller.constants.ParameterNames;
import com.epam.eshop.controller.constants.URLConstants;
import com.epam.eshop.entity.Product;
import com.epam.eshop.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by artch on 01.10.2020.
 */
@WebServlet("/products/*")
@MultipartConfig
public class ProductServlet extends HttpServlet {

    private static final String PRODUCT_ACTION_CHANGE = "changeProduct";
    private static final String PRODUCT_ACTION_ADD = "addProduct";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String returnPath = Util.getReturnPath(request);

        String action = request.getParameter(ParameterNames.PRODUCT_ACTION);
        String category = request.getParameter(ParameterNames.CATEGORY);
        ProductService productService = new ProductService(category);

        boolean success = false;

        if (PRODUCT_ACTION_CHANGE.equals(action)) {
            success = productService.updateProductData(request);
        } else if (PRODUCT_ACTION_ADD.equals(action)) {
            int newProductId = productService.addNewProduct(request);
            returnPath += newProductId;
            success = true;
        }

        if (success) {
            response.sendRedirect(returnPath);
        } else {
            response.sendError(500);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String category = Util.getCategoryFromURL(request.getRequestURI());

        String id = Util.getIdFromURL(request.getPathInfo());

        ProductService productService = new ProductService(category);
        Product currentProduct = productService.getProduct(id);
        request.setAttribute(AttributesNames.CURRENT_PRODUCT, currentProduct);
        Util.replaceAttributeFromSessionIntoRequest(request, AttributesNames.SUCCESS);

        request.getRequestDispatcher(URLConstants.VIEW_PRODUCT).forward(request, response);
    }
}
