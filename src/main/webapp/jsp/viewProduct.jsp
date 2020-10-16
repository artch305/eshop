<%@ page import="com.epam.eshop.entity.Category" %>
<%@ page import="com.epam.eshop.entity.UserRole" %>
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

<c:import url="jspComponent/head.jsp?currentPageForReturn=${pageContext.request.contextPath}/products/${requestScope.currentProduct.category.databaseValue}/${requestScope.currentProduct.id}"/>

<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>

<c:set var="administrator" value="<%=UserRole.ADMINISTRATOR%>"/>
<c:set var="customer" value="<%=UserRole.CUSTOMER%>"/>
<c:set var="monitors" value="<%=Category.MONITORS%>"/>
<c:set var="keyboards" value="<%=Category.KEYBOARDS%>"/>

<fmt:bundle basename="messages" prefix="product.">

<div class="container">
    <div class="row">

            <div class="col-1">
                    <a href="${pageContext.request.contextPath}/products?category=${requestScope.currentProduct.category.databaseValue}"
                       type="submit" class="btn btn-info"><fmt:message key="catalog"/></a>
            </div>

        <c:if test="${sessionScope.currentUser.userRole == administrator}">
            <div class="col-2">

                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal${requestScope.currentProduct.id}">
                    <img src="${pageContext.request.contextPath}/img/icons/icons8-edit-64.png"
                         style="max-height: 30px">
                </button>

                <fctg:modalForChangeProduct product="${requestScope.currentProduct}"
                        returnPath="${pageContext.request.contextPath}/products/${requestScope.currentProduct.category.databaseValue}/${requestScope.currentProduct.id}">

                </fctg:modalForChangeProduct>

            </div>
        </c:if>

        <c:if test="${sessionScope.currentUser.userRole == customer}">
            <div class="col-2">
                <c:choose>
                    <c:when test="${requestScope.currentProduct.active}">
                        <form id="form_add_id${requestScope.currentProduct.id}"
                              onsubmit="return addToCart('form_add_id${requestScope.currentProduct.id}')"
                              action="${pageContext.request.contextPath}/cart" method="post">
                            <input type="hidden" name="productId" value="${requestScope.currentProduct.id}">
                            <input type="hidden" name="actionCart" value="addProduct">
                            <button type="submit" class="btn btn-success">
                                <img src="${pageContext.request.contextPath}/img/icons/icons8-cart-80.png"
                                     style="max-height: 30px">
                            </button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <strong><fmt:message key="inactiveError"/></strong>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:if>

    </div>
</div>

    <c:if test="${requestScope.success != null}">
        <div class="alert alert-success">
            <strong><fmt:message key="success"/></strong><fmt:message key="successMessage"/>
        </div>
    </c:if>


<div class="container p-3 my-3 border">

        <c:choose>

            <c:when test="${requestScope.currentProduct.category == monitors}">
                <c:import url="jspComponent/monitor.jsp"/>
            </c:when>
            <c:when test="${requestScope.currentProduct.category == keyboards}">
                <c:import url="jspComponent/keyboard.jsp"/>
            </c:when>

        </c:choose>

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
                alert('Failed to add to cart');
            });

        return false;
    }

</script>
</body>
</html>