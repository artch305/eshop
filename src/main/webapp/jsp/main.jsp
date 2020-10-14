<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Main page</title>
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

<fmt:bundle basename="messages" prefix="main.">

<div class="container">
    <div class="row">

        <div class="col-md" style="margin: 10px 10px">
            <div class="card" style="width:400px;  height: auto">
                <img class="card-img-top" src="${pageContext.request.contextPath}/img/monitor.jpg" alt="Card image" style="width:100%">
                <div class="card-body">
                    <h4 class="card-title"><fmt:message key="monitors"/></h4>
                    <p class="card-text"><fmt:message key="monitorText"/></p>
                    <a href="${pageContext.request.contextPath}/products?category=monitors" class="btn btn-primary stretched-link"><fmt:message key="seeMonitors"/></a>
                </div>
            </div>
        </div>

        <div class="col-md" style="margin: 10px 10px">
            <div class="card" style="width:400px; height: auto ">
                <img class="card-img-top" src="${pageContext.request.contextPath}/img/keyboard.jpg" alt="Card image" style="width:100%">
                <div class="card-body">
                    <h4 class="card-title"><fmt:message key="keyboards"/></h4>
                    <p class="card-text"><fmt:message key="keyboardText"/></p>
                    <a href="${pageContext.request.contextPath}/products?category=keyboards" class="btn btn-primary stretched-link"><fmt:message key="seeKeyboards"/></a>
                </div>
            </div>
        </div>
    </div>
</div>
</fmt:bundle>
</body>
</html>
