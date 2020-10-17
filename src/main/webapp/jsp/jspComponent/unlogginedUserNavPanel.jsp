<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>

<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>

<fmt:bundle basename="messages" prefix="unlogginedUserNavPanel.">
    <div class="container-fluid">
        <div class="row bg-secondary mx-2" >

            <div class="col-9" style="margin: 10px 10px">
                <nav class="navbar navbar-expand-sm bg-secondary navbar-dark">
                    <ul class="navbar-nav">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                                <fmt:message key="products"/>
                            </a>
                            <div class="dropdown-menu">
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/products?category=monitors"><fmt:message key="showMonitors"/></a>
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/products?category=keyboards"><fmt:message key="showKeyboards"/></a>
                            </div>
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
