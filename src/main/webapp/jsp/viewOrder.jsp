<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customTags" %>
<html>
<head>
    <title>View order</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>

<c:import url="jspComponent/head.jsp"/>

<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>

<fmt:bundle basename="messages" prefix="orders.">

<div class="container">
    <table class="table table-bordered">
        <thead>
        <tr>
            <th class="align-top">Order ID</th>
            <c:if test="${sessionScope.currentUser.userRole.role == 'administrator'}">
                <th class="align-top"><fmt:message key="userEmail"/></th>
            </c:if>
            <th class="align-top"><fmt:message key="totalPrice"/></th>
            <th class="align-top"><fmt:message key="createDate"/></th>
            <th class="align-top"><fmt:message key="lastUpdateDate"/></th>
            <th class="align-top"><fmt:message key="status"/></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${requestScope.currentOrder.id}</td>
            <c:if test="${sessionScope.currentUser.userRole.role == 'administrator'}">
                <td>${requestScope.currentOrder.userEmail}</td>
            </c:if>
            <td><fmt:formatNumber type="number" maxFractionDigits="2"
                                  value="${requestScope.currentOrder.totalPrice}"/>$
            </td>
            <td>${requestScope.currentOrder.createDate}</td>
            <td>${requestScope.currentOrder.lastUpdateDate}</td>
            <td><c:if test="${sessionScope.currentUser.userRole.role == 'customer'}">
                ${requestScope.currentOrder.orderStatus.status}
            </c:if>
                <c:if test="${sessionScope.currentUser.userRole.role == 'administrator'}">
                    <div class="container-fluid">
                        <form action="${pageContext.request.contextPath}/orders/${requestScope.currentOrder.id}" method="post">
                            <input type="hidden" name="actionOrder" value="changeStatus">
                            <input type="hidden" name="orderId" value="${requestScope.currentOrder.id}">
                            <div class="row" style="margin: 10px 10px">
                                <div class="col-7">
                                    <select class="form-control" id="sel1" name="newStatusId">
                                        <option value="1" <c:if test="${requestScope.currentOrder.orderStatus.status == 'registered'}">
                                            selected
                                        </c:if>>
                                            <fmt:message key="registered"/>
                                        </option>
                                        <option value="2" <c:if test="${requestScope.currentOrder.orderStatus.status == 'paid'}">
                                            selected
                                        </c:if>>
                                            <fmt:message key="paid"/>
                                        </option>
                                        <option value="3" <c:if test="${requestScope.currentOrder.orderStatus.status == 'canceled'}">
                                            selected
                                        </c:if>>
                                            <fmt:message key="canceled"/>
                                        </option>
                                    </select>
                                </div>

                                <div class="col-2">
                                    <button type="submit" class="btn btn-success" style="margin-left: 10px">
                                        <fmt:message key="apply"/>
                                    </button>
                                </div>

                            </div>
                        </form>
                    </div>
                </c:if>
            </td>
        </tr>
        </tbody>
    </table>


    <ctg:printProductsInCartOrOrder productsWithAmount="${requestScope.productsInOrder}" inCart="false">

    </ctg:printProductsInCartOrOrder>
</div>
</fmt:bundle>
</body>
</html>