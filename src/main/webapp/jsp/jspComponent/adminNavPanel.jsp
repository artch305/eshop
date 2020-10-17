<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="fctg" %>

<html>
<body>

<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>

<fmt:bundle basename="messages" prefix="adminNavPanel.">
    <div class="container-fluid">
        <div class="row bg-dark mx-2">

            <div class="col-9 my-2">
                <nav class="navbar navbar-expand-sm bg-dark navbar-dark">
                    <ul class="navbar-nav">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                                <fmt:message key="products"/>
                            </a>
                            <div class="dropdown-menu">
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/products?category=monitors"><fmt:message key="showMonitors"/></a>
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/products?category=keyboards"><fmt:message key="showKeyboards"/></a>
                                <a class="dropdown-item" type="button" data-toggle="modal" data-target="#myModal_monitors"><fmt:message key="addMonitor"/></a>
                                <a class="dropdown-item" type="button" data-toggle="modal" data-target="#myModal_keyboards"><fmt:message key="addKeyboard"/></a>
                            </div>
                        </li>
                        <li class="nav-item">
                            <a href="${pageContext.request.contextPath}/users"
                                    <c:choose>
                                        <c:when test="${'users'.equals(param.activePage)}">
                                            class="nav-link active"
                                        </c:when>
                                        <c:otherwise>
                                            class="nav-link"
                                        </c:otherwise>
                                    </c:choose>
                            ><fmt:message key="showUsers"/></a>
                        </li>
                        <li class="nav-item">
                            <a  href="${pageContext.request.contextPath}/orders"
                                    <c:choose>
                                        <c:when test="${'orders'.equals(param.activePage)}">
                                            class="nav-link active"
                                        </c:when>
                                        <c:otherwise>
                                            class="nav-link"
                                        </c:otherwise>
                                    </c:choose>
                            ><fmt:message key="showOrders"/></a>
                        </li>
                        <li class="nav-item">
                            <a href="${pageContext.request.contextPath}/logout?currentPageForReturn=${param.currentPageForReturn}" class="nav-link" role="button"><fmt:message
                                    key="SingOut"/></a>
                        </li>
                    </ul>
                </nav>
            </div>

            <div class="col-2 my-2">
                <c:import url="jspComponent/langChangePanel.jsp"/>
            </div>

        </div>
    </div>

    <fctg:modelForAddProduct category="monitors"></fctg:modelForAddProduct>

    <fctg:modelForAddProduct category="keyboards"></fctg:modelForAddProduct>

</fmt:bundle>

</body>
</html>
