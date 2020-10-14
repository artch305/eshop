package com.epam.eshop.controller;

import com.epam.eshop.entity.Product;
import com.epam.eshop.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by artch on 01.10.2020.
 */
@WebServlet("/products/*")
@MultipartConfig
public class ProductServlet extends HttpServlet {

    private static final String PRODUCT_ACTION_CHANGE = "changeProduct";
    private static final String PRODUCT_ACTION_ADD = "addProduct";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("productAction");
        String category = request.getParameter("category");
        ProductService productService = new ProductService(category);
        String returnPath = request.getParameter("returnPath");

        if (returnPath == null || returnPath.trim().isEmpty()) {
            returnPath = request.getContextPath() + "/main";
        }

        boolean success = false;

        if (PRODUCT_ACTION_CHANGE.equals(action)) {
            success = productService.updateProductData(request);
        } else if (PRODUCT_ACTION_ADD.equals(action)) {
            int newProductId = productService.addNewProduct(request);

            if (newProductId != 0) {
                returnPath += newProductId;
                success = true;
            }
        }

        if (success) {
            response.sendRedirect(returnPath);
        } else {
            response.sendError(500);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Pattern pattern = Pattern.compile("[a-zA-Z]+(?=/[0-9]+)");
        Matcher matcher = pattern.matcher(request.getRequestURI());
        String category = "";

        if (matcher.find()) {
            category = matcher.group();
        }

        String id = request.getPathInfo();
        id = id.replace("/" + category + "/", "");

        ProductService productService = new ProductService(category);
        Product currentProduct = productService.getProduct(id);
        request.setAttribute("currentProduct", currentProduct);
        request.getRequestDispatcher("/jsp/viewProduct.jsp").forward(request, response);
    }
}
