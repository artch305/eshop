<%@ tag import="java.util.ResourceBundle" %>
<%@ tag import="java.util.Locale" %>
<%@ tag import="com.epam.eshop.controller.constants.AttributesNames" %>
<%@ tag import="com.epam.eshop.controller.constants.ParameterNames" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="maxItems" required="true" type="java.lang.Integer" rtexprvalue="true" %>
<%@ attribute name="productsOnPage" required="true" type="java.lang.Integer" rtexprvalue="true" %>
<%
    if (productsOnPage == null || productsOnPage == 0){
        productsOnPage = 5;
    }

    String currentLocal;
    if (session.getAttribute(AttributesNames.LANGUAGE) == null) {
        currentLocal = request.getLocale().getLanguage();
        session.setAttribute(AttributesNames.LANGUAGE, currentLocal);
    }
    ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", Locale.forLanguageTag((String) session.getAttribute(AttributesNames.LANGUAGE)));

    int pages;
    if (maxItems % productsOnPage > 0) {
        pages = maxItems / productsOnPage + 1;
    } else {
        pages = maxItems / productsOnPage;
    }

    int activePage = 0;
    String category = request.getParameter(ParameterNames.CATEGORY);
    String currentPageParam = request.getParameter(ParameterNames.PAGE);

    if (currentPageParam != null) {
        try {
            activePage = Integer.parseInt(request.getParameter(ParameterNames.PAGE));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    if (activePage <= 0) {
        activePage = 1;
    }

    out.write("<li class=\"page-item " + (activePage > 1 ? "" : "disabled") + "\">" +
            "<a class=\"page-link\" href=\"" +
            request.getContextPath() + "/products?category=" + category + "&page=" + (activePage - 1) + "\">" + resourceBundle.getString("products.previous") + "</a></li>");

    for (int i = 1; i <= pages; i++) {
        out.write("<li class=\"page-item " + (activePage == i ? "active" : "") + "\"><a class=\"page-link\" href=\"" +
                request.getContextPath() + "/products?category=" + category + "&page=" + i + "\">" + i + "</a></li>");
    }

    out.write("<li class=\"page-item " + (activePage < pages ? "" : "disabled") + "\">" +
            "<a class=\"page-link\" href=\"" +
            request.getContextPath() + "/products?category=" + category + "&page=" + (activePage + 1) + "\">" + resourceBundle.getString("products.next") + "</a></li>");

%>