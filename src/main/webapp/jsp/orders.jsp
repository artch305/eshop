<%@ page import="com.epam.eshop.entity.UserRole" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customTags" %>
<html>
<head>
    <title>Orders</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>

<c:import url="jspComponent/head.jsp?activePage=orders"/>

<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>

<c:set var="administrator" value="<%=UserRole.ADMINISTRATOR%>"/>

<fmt:bundle basename="messages" prefix="orders.">

    <div class="container-fluid" style="margin: 10px">

        <table class="table table-striped">
            <thead>
            <tr class="table-primary">
                <th class="align-top"><fmt:message key="orderId"/></th>
                <c:if test="${sessionScope.currentUser.userRole == administrator}">
                    <th class="align-top"><fmt:message key="userEmail"/></th>
                </c:if>
                <th class="align-top"><fmt:message key="totalPrice"/></th>
                <th class="align-top"><fmt:message key="createDate"/></th>
                <th class="align-top"><fmt:message key="lastUpdateDate"/></th>
                <th class="align-top"><fmt:message key="status"/></th>
                <th class="align-top"><fmt:message key="viewDetails"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${sessionScope.orders}">
            <tr>
                <td>${order.id}</td>
                <c:if test="${sessionScope.currentUser.userRole == administrator}">
                    <td>${order.userEmail}</td>
                </c:if>
                <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${order.totalPrice}"/>$</td>
                <td>${order.createDate}</td>
                <td>${order.lastUpdateDate}</td>
                <td>
                    <c:choose>
                        <c:when test="${order.orderStatus.status == 'registered'}">
                            <fmt:message key="registered"/>
                        </c:when>
                        <c:when test="${order.orderStatus.status == 'paid'}">
                            <fmt:message key="paid"/>
                        </c:when>
                        <c:when test="${order.orderStatus.status == 'canceled'}">
                            <fmt:message key="canceled"/>
                        </c:when>
                    </c:choose>
                </td>
                <td><a href="${pageContext.request.contextPath}/orders/${order.id}"
                       class="btn btn-info" role="button">
                    <img src="${pageContext.request.contextPath}/img/icons/icons8-view-64.png"
                         style="max-height: 30px">
                </a>
                </td>
            </tr>
            </c:forEach>

    </div>

    <script lang="javascript">


    </script>
</fmt:bundle>
</body>
</html>
