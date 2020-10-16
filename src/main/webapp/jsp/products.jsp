<%@ page import="com.epam.eshop.entity.Category" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customTags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="fctg" %>
<html>
<head>
    <title>Products</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>


<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>

<fmt:bundle basename="messages" prefix="products.">

    <c:set var="monitors" value="<%=Category.MONITORS%>"/>
    <c:set var="keyboards" value="<%=Category.KEYBOARDS%>"/>


    <c:import
            url="jspComponent/head.jsp?currentPageForReturn=${pageContext.request.contextPath}/products?category=${pageContext.request.getParameter(categoryParamName)}"/>

    <c:if test="${requestScope.success != null}">
        <div class="alert alert-success">
            <strong><fmt:message key="success"/></strong><fmt:message key="successMessage"/>
        </div>
    </c:if>
    <div class="container-fluid">
        <div class="row">

            <div class="col-sm-2" style="margin-left: 10px; margin-right: 10px;">
                <c:set var="categoryParamName" value="category"/>
                <c:choose>
                    <c:when test="${pageContext.request.getParameter(categoryParamName) == monitors.databaseValue}">
                        <c:import url="jspComponent/monitorsFilters.jsp"/>
                    </c:when>
                    <c:when test="${pageContext.request.getParameter(categoryParamName) == keyboards.databaseValue}">
                        <c:import url="jspComponent/keyboardsFilters.jsp"/>
                    </c:when>
                </c:choose>

            </div>

            <div class="col-sm-8">
                <div class="row" style="margin-top: 15px">

                    <div class="col-6" style="margin-right: 10px;">
                        <div class="row">
                               <strong style="margin-left: 15px"><fmt:message key="sortedOn"/></strong>
                        </div>
                        <div class="row" style="margin-top: 15px">
                            <form action="${pageContext.request.contextPath}/products"
                                  method="post">
                                <input type="hidden" name="actionFilters" value="changeOrdering">
                                <div class="form-inline">
                                    <div class="col-7" style=" margin-right: 5px;">
                                        <select name="orderingBy" class="form-control">
                                            <option value="minPrice" <c:if
                                                    test="${sessionScope.filters.orderingName == 'minPrice'}">
                                                selected
                                            </c:if>><fmt:message key="minPrice"/>
                                            </option>
                                            <option value="maxPrice"<c:if
                                                    test="${sessionScope.filters.orderingName == 'maxPrice'}">
                                                selected
                                            </c:if>><fmt:message key="maxPrice"/>
                                            </option>
                                            <option value="producerA-Z" <c:if
                                                    test="${sessionScope.filters.orderingName == 'producerA-Z'}">
                                                selected
                                            </c:if>><fmt:message key="producerA-Z"/>
                                            </option>
                                            <option value="producerZ-A" <c:if
                                                    test="${sessionScope.filters.orderingName == 'producerZ-A'}">
                                                selected
                                            </c:if>><fmt:message key="producerZ-A"/>
                                            </option>
                                        </select>
                                    </div>
                                    <div class="col-3" style=" margin-right: 5px;">
                                        <button type="submit" class="btn btn-primary"><fmt:message key="sort"/></button>
                                    </div>
                                </div>
                            </form>
                        </div>

                    </div>

                    <div class="col-5">

                        <div class="row">
                            <strong style="margin-left: 15px"><fmt:message key="productsOnPage"/></strong>
                        </div>

                        <div class="row" style="margin-top: 15px">
                            <form action="${pageContext.request.contextPath}/products"
                                  method="post">
                                <input type="hidden" name="actionFilters" value="changeProductsOnPage">
                                <div class="form-inline">
                                    <div class="col-5" style=" margin-right: 15px;">
                                        <select name="productsOnPage" class="form-control">
                                            <option value="5" <c:if
                                                    test="${sessionScope.productsOnPage == 5}">
                                                selected
                                            </c:if>>5
                                            </option>
                                            <option value="10"<c:if
                                                    test="${sessionScope.productsOnPage == 10}">
                                                selected
                                            </c:if>>10
                                            </option>
                                            <option value="15" <c:if
                                                    test="${sessionScope.productsOnPage == 15}">
                                                selected
                                            </c:if>>15
                                            </option>
                                            <option value="20" <c:if
                                                    test="${sessionScope.productsOnPage == 20}">
                                                selected
                                            </c:if>
                                            >20
                                            </option>
                                        </select>
                                    </div>
                                    <div class="col-5" style="margin-right: 5px;">
                                        <button type="submit" class="btn btn-primary"><fmt:message
                                                key="change"/></button>
                                    </div>
                                </div>
                            </form>
                        </div>

                    </div>
                </div>

                <c:forEach var="product" items="${sessionScope.products}">
                    <fctg:printProduct product="${product}">

                    </fctg:printProduct>
                </c:forEach>

            </div>
        </div>
    </div>

    <div class="container">
        <ul class="pagination justify-content-center">
            <fctg:pagination maxItems="${sessionScope.maxItems}" productsOnPage="${sessionScope.productsOnPage}">

            </fctg:pagination>
        </ul>
    </div>
</fmt:bundle>

<script lang="javascript">

    function addToCart(formId) {

        var formElement = $('#' + formId);
        var addUrl = formElement.prop('action');

        $.post(addUrl, formElement.serializeArray())
            .done(function (resp) {
                var newUserCart = $($.parseHTML(resp)).filter("#userCart").html();
                $('#userCart').html(newUserCart);
            })
            .fail(function (err) {
                console.log(err);
                alert('Failed to authorize user');
            });

        return false;
    }

</script>
</body>
</html>
