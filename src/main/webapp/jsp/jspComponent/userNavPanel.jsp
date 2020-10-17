<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>

<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>

<fmt:bundle basename="messages" prefix="userNavPanel.">
    <div class="container-fluid" id="userCart">
        <div class="row bg-primary mx-2">

            <div class="col-9 my-2">
                <nav class="navbar navbar-expand-sm bg-primary navbar-dark">
                    <ul class="navbar-nav">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                                <fmt:message key="products"/>
                            </a>
                            <div class="dropdown-menu">
                                <a class="dropdown-item"
                                   href="${pageContext.request.contextPath}/products?category=monitors"><fmt:message
                                        key="showMonitors"/></a>
                                <a class="dropdown-item"
                                   href="${pageContext.request.contextPath}/products?category=keyboards"><fmt:message
                                        key="showKeyboards"/></a>
                            </div>
                        </li>
                        <li class="nav-item">
                            <a href="${pageContext.request.contextPath}/orders"
                                    <c:choose>
                                        <c:when test="${'orders'.equals(param.activePage)}">
                                            class="nav-link active"
                                        </c:when>
                                        <c:otherwise>
                                            class="nav-link"
                                        </c:otherwise>
                                    </c:choose>
                            ><fmt:message key="showMyOrders"/></a>
                        </li>
                            <li class="nav-item">
                                <a href="${pageContext.request.contextPath}/cart"
                                        <c:choose>
                                            <c:when test="${'cart'.equals(param.activePage)}">
                                                class="nav-link active"
                                            </c:when>
                                            <c:otherwise>
                                                class="nav-link"
                                            </c:otherwise>
                                        </c:choose>
                                ><fmt:message key="showCart"/><span class="badge badge-light"
                                                                    style="margin-left: 5px">${sessionScope.currentUserCart.getAllAmountProductsInCart()}</span></a>
                            </li>
                        <li class="nav-item">
                            <a href="${pageContext.request.contextPath}/logout?currentPageForReturn=${param.currentPageForReturn}"
                               class="nav-link" role="button"><fmt:message
                                    key="SingOut"/></a>
                        </li>
                    </ul>
                </nav>
            </div>
            <div class="col-2" style="margin: 10px 10px">
                <c:import url="jspComponent/langChangePanel.jsp"/>
            </div>
        </div>
    </div>
</fmt:bundle>
</body>
</html>
