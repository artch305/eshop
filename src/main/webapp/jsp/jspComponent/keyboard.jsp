<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Keyboard</title>
</head>
<body>

<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>

<fmt:bundle basename="messages" prefix="">

    <c:set var="currentProduct" value="${requestScope.currentProduct}" target="com.epam.eshop.entity.KeyboardProduct"/>
    <div class="row">

        <div class="col-sm">
            <div class="row">
                <img src="${pageContext.request.contextPath}/${requestScope.currentProduct.imgURL}"
                     class="rounded" alt="${currentProduct.name}" style="max-width: 500px"/>
            </div>
            <div class="row">
                <c:if test="${sessionScope.currentUser.userRole.role == 'administrator'}">
                    <c:if test="${currentProduct.active}">
                        <span class="badge badge-primary"><h6><fmt:message key="product.active"/></h6></span>
                    </c:if>
                    <c:if test="${!currentProduct.active}">
                        <span class="badge badge-danger"><h6><fmt:message key="product.inactive"/></h6></span>
                    </c:if>
                </c:if>
            </div>
        </div>

        <div class="col-sm">
            <ul class="list-group">
                <li class="list-group-item">
                    <dt><fmt:message key="product.producer"/></dt>
                        ${currentProduct.producer}</li>
                <li class="list-group-item">
                    <dt><fmt:message key="product.name"/></dt>
                        ${currentProduct.name}</li>
                <li class="list-group-item">
                    <dt><fmt:message key="product.category"/></dt>
                        ${currentProduct.category.databaseValue}</li>
                <li class="list-group-item">
                    <dt><fmt:message key="keyboard.connectionType"/></dt>
                        ${currentProduct.connectionType}</li>
                <li class="list-group-item">
                    <dt><fmt:message key="keyboard.mechanical"/></dt>
                    <c:choose><c:when test="${currentProduct.mechanical}"><fmt:message key="keyboard.yes"/></c:when>
                        <c:otherwise><fmt:message key="keyboard.no"/></c:otherwise></c:choose></li>
                <li class="list-group-item">
                    <dt><fmt:message key="keyboard.lightColor"/></dt>
                        ${currentProduct.lightColor}</li>
                <li class="list-group-item">
                    <dt><fmt:message key="product.description"/></dt>
                        ${currentProduct.description}</li>
                <li class="list-group-item">
                    <dt><fmt:message key="product.addedDate"/></dt>
                        ${currentProduct.addedDate}</li>
                <li class="list-group-item"><kbd><fmt:message key="product.price"/></kbd> ${currentProduct.price}$</li>
            </ul>
        </div>

    </div>
</fmt:bundle>
</body>
</html>
