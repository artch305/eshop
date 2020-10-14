package com.epam.eshop.controller;

import com.epam.eshop.entity.Product;
import com.epam.eshop.entity.User;
import com.epam.eshop.filter.AbstractFilters;
import com.epam.eshop.filter.FilterService;
import com.epam.eshop.filter.service.FilterServiceFactory;
import com.epam.eshop.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by artch on 28.09.2020.
 */
@WebServlet(value = "/products")
public class ProductsPageServlet extends HttpServlet {

    private static final String ACTION_APPLY_FILTERS = "applyFilters";
    private static final String ACTION_RESET_FILTERS = "resetFilters";
    private static final String ACTION_CHANGE_ORDERING = "changeOrdering";
    private static final String ACTION_CHANGE_AMOUNT_PRODUCTS_ON_PAGE = "changeProductsOnPage";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentAction = request.getParameter("action");
        HttpSession session = request.getSession();

        AbstractFilters currentUserFilters = (AbstractFilters) session.getAttribute("filters");
        String category = currentUserFilters.getFiltersCategory();

        FilterService filterService = FilterServiceFactory.getFilterService(category);

        switch (currentAction) {
            case ACTION_RESET_FILTERS: {
                filterService.resetFilters(currentUserFilters);
                break;
            }
            case ACTION_APPLY_FILTERS: {
                filterService.updateFilters(currentUserFilters, request);
                break;
            }
            case ACTION_CHANGE_ORDERING: {
                String orderingBy = request.getParameter("orderingBy");
                filterService.applyOrdering(currentUserFilters, orderingBy);
                break;
            }
            case ACTION_CHANGE_AMOUNT_PRODUCTS_ON_PAGE: {
                String amountProductsOnPage = request.getParameter("productsOnPage");
                session.setAttribute("productsOnPage", amountProductsOnPage);
                break;
            }
            default: {
                throw new UnsupportedOperationException("Not implemented in OrderServlet");
            }
        }

        response.sendRedirect("products?category=" + category);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String category = request.getParameter("category");
        String page = request.getParameter("page");
        ProductService productService = new ProductService(category);
        HttpSession session = request.getSession();
        User user = (User) request.getSession().getAttribute("currentUser");

        AbstractFilters currentUserFilters = (AbstractFilters) session.getAttribute("filters");
        currentUserFilters = checkAndGetCorrectFilters(user, category, session, currentUserFilters);

        List<Product> products = productService.getProducts(currentUserFilters, page, (String) session.getAttribute("productsOnPage"));

        session.setAttribute("maxItems", productService.getMaxItems(currentUserFilters));
        session.setAttribute("products", products);
        request.getRequestDispatcher("/jsp/products.jsp").forward(request, response);
    }

    private AbstractFilters checkAndGetCorrectFilters(User user, String category, HttpSession session, AbstractFilters currentUserFilters) {
        if (currentUserFilters != null && !currentUserFilters.getFiltersCategory().equals(category)) {
            currentUserFilters = null;
        }

        if (currentUserFilters == null) {
            FilterService filterService = FilterServiceFactory.getFilterService(category);
            currentUserFilters = filterService.getFiltersForUser(user);
            Map<String, List<String>> allValuesForFilters = filterService.getAllValuesForFilters(currentUserFilters);
            session.setAttribute("allValuesForFilters", allValuesForFilters);
            session.setAttribute("filters", currentUserFilters);
        }

        return currentUserFilters;
    }
}
