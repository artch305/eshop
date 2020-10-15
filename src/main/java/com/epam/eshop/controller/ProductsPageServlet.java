package com.epam.eshop.controller;

import com.epam.eshop.controller.constants.AttributesNames;
import com.epam.eshop.controller.constants.ParameterNames;
import com.epam.eshop.controller.constants.URLConstants;
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
        HttpSession session = request.getSession();

        AbstractFilters currentUserFilters = (AbstractFilters) session.getAttribute(AttributesNames.FILTERS);

        String category = currentUserFilters.getFiltersCategory();
        FilterService filterService = FilterServiceFactory.getFilterService(category);

        String currentAction = request.getParameter(ParameterNames.ACTION_FILTERS);

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
                String orderingBy = request.getParameter(ParameterNames.ORDERING_BY);
                filterService.applyOrdering(currentUserFilters, orderingBy);
                break;
            }
            case ACTION_CHANGE_AMOUNT_PRODUCTS_ON_PAGE: {
                String amountProductsOnPage = request.getParameter(ParameterNames.PRODUCTS_ON_PAGE);
                session.setAttribute(ParameterNames.PRODUCTS_ON_PAGE, amountProductsOnPage);
                break;
            }
            default: {
                throw new UnsupportedOperationException("Not implemented in ProductsPageServlet");
            }
        }

        response.sendRedirect("products?category=" + category);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        AbstractFilters currentUserFilters = (AbstractFilters) session.getAttribute(AttributesNames.FILTERS);

        String category = request.getParameter(ParameterNames.CATEGORY);
        User user = (User) request.getSession().getAttribute(AttributesNames.CURRENT_USER);
        currentUserFilters = checkAndGetCorrectFilters(user, category, session, currentUserFilters);

        ProductService productService = new ProductService(category);
        String page = request.getParameter(ParameterNames.PAGE);
        String productsOnPage = (String) session.getAttribute(AttributesNames.PRODUCTS_ON_PAGE);
        List<Product> products = productService.getProducts(currentUserFilters, page, productsOnPage);
        session.setAttribute(AttributesNames.PRODUCTS, products);

        session.setAttribute(AttributesNames.MAX_ITEMS, productService.getMaxItems(currentUserFilters));
        Util.replaceSuccessAttrFromSessionIntoRequest(request);

        request.getRequestDispatcher(URLConstants.PRODUCTS_JSP).forward(request, response);
    }

    private AbstractFilters checkAndGetCorrectFilters(User user, String category, HttpSession session, AbstractFilters currentUserFilters) {
        if (!isCorrectFilters(category, currentUserFilters)) {
            currentUserFilters = null;
        }

        if (currentUserFilters == null) {
            FilterService filterService = FilterServiceFactory.getFilterService(category);
            currentUserFilters = filterService.getFiltersForUser(user);
            session.setAttribute(AttributesNames.FILTERS, currentUserFilters);

            Map<String, List<String>> allValuesForFilters = filterService.getAllValuesForFilters(currentUserFilters);
            session.setAttribute(AttributesNames.ALL_VALUES_FOR_FILTERS, allValuesForFilters);
        }

        return currentUserFilters;
    }

    private boolean isCorrectFilters(String category, AbstractFilters currentUserFilters) {
        return currentUserFilters != null && currentUserFilters.getFiltersCategory().equals(category);
    }
}
