<%@ page import="com.epam.eshop.entity.UserRole" %>
<%@ page import="com.epam.eshop.entity.UserStatus" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customTags" %>
<html>
<head>
    <title>Users</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>

<c:import url="jspComponent/head.jsp?activePage=users"/>

<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>

<fmt:bundle basename="messages" prefix="users.">

<div class="container-fluid" style="margin: 10px">

    <c:if test="${requestScope.success != null}">
        <div class="alert alert-success">
            <strong><fmt:message key="success"/></strong><fmt:message key="successMessage"/>
        </div>
    </c:if>

    <c:set var="administrator" value="<%=UserRole.ADMINISTRATOR%>"/>
    <c:set var="customer" value="<%=UserRole.CUSTOMER%>"/>
    <c:set var="active" value="<%=UserStatus.ACTIVE%>"/>
    <c:set var="banned" value="<%=UserStatus.BANNED%>"/>

    <table class="table table-striped">
        <thead>
        <tr class="table-primary">
            <th class="align-top">ID</th>
            <th class="align-top"><fmt:message key="login"/></th>
            <th class="align-top">Email</th>
            <th class="align-top"><fmt:message key="registrationDate"/></th>
            <th class="align-top"><fmt:message key="role"/></th>
            <th class="align-top"><fmt:message key="status"/></th>
            <th class="align-top"><fmt:message key="edit"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${requestScope.users}">
        <tr>
            <td>${user.id}</td>
            <td>${user.login}</td>
            <td>${user.email}</td>
            <td>${user.registrationDate}</td>
            <td>
            <c:choose>
                <c:when test="${user.userRole == administrator}">
                    <fmt:message key="administrator"/>
                </c:when>
                <c:when test="${user.userRole == customer}">
                    <fmt:message key="customer"/>
                </c:when>
            </c:choose>
            </td>
            <td>
                <c:choose>
                    <c:when test="${user.userStatus == active}">
                        <fmt:message key="active"/>
                    </c:when>
                    <c:when test="${user.userStatus == banned}">
                        <fmt:message key="banned"/>
                    </c:when>
                </c:choose>
            </td>
            <td><!-- Button to Open the Modal -->
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal${user.id}">
                    <img src="${pageContext.request.contextPath}/img/icons/icons8-edit-64.png"
                         style="max-height: 30px">
                </button>

                <!-- The Modal -->
                <div class="modal fade" id="myModal${user.id}">
                    <div class="modal-dialog">
                        <div class="modal-content">

                            <!-- Modal Header -->
                            <div class="modal-header">
                                <h4 class="modal-title"><fmt:message key="editUser"/> <strong>${user.login}</strong></h4>
                                <button type="button" onclick="closeChangeWindow()" class="close" data-dismiss="modal">×</button>
                            </div>

                            <!-- Modal body -->

                            <div class="modal-body">
                                <div class="container p-3 my-3 border">
                                    <form action="${pageContext.request.contextPath}/users" method="post">
                                        <input type="hidden" name="userId" value="${user.id}">

                                        <div class="row" style="margin: 10px 10px">
                                            <div class="col-3">
                                                <strong style="margin-right: 10px"><fmt:message key="login"/> </strong>
                                            </div>
                                            <div class="col-6">
                                                <div class="form-group">
                                                    <input type="text" class="form-control form-control-sm"
                                                           name="login" value="${user.login}">
                                                </div>
                                            </div>
                                            <c:if test="${requestScope.userIdWithError == user.id && requestScope.errorMessage == 'existLogin'}">
                                                <p class="text-danger"><fmt:message key="existLoginError"/></p>
                                            </c:if>
                                        </div>

                                        <div class="row" style="margin: 10px 10px">
                                            <div class="col-3">
                                                <strong style="margin-right: 10px">Email </strong>
                                            </div>
                                            <div class="col-6">
                                                <div class="form-group">
                                                    <input type="text" class="form-control form-control-sm"
                                                           name="email" value="${user.email}">
                                                </div>
                                            </div>
                                            <c:if test="${requestScope.userIdWithError == user.id && requestScope.errorMessage == 'existEmail'}">
                                                <p class="text-danger"><fmt:message key="existEmailError"/></p>
                                            </c:if>
                                        </div>

                                        <div class="row" style="margin: 10px 10px">
                                            <div class="col-3">
                                                <strong style="margin-right: 10px"><fmt:message key="password"/> </strong>
                                            </div>
                                            <div class="col-6">
                                                <div class="form-group">
                                                    <input type="password" class="form-control form-control-sm"
                                                           name="password" value="${user.password}">
                                                </div>
                                            </div>
                                        </div>

                                            <label for="sel1"><fmt:message key="status"/></label>
                                            <select class="form-control" id="sel1" name="status">
                                                <option value="${active.status}" <c:if test="${user.userStatus == active}">
                                                    selected
                                                </c:if>>
                                                    <fmt:message key="active"/>
                                                </option>
                                                <option value="${banned.status}" <c:if test="${user.userStatus == banned}">
                                                    selected
                                                </c:if>>
                                                    <fmt:message key="banned"/>
                                                </option>
                                            </select>

                                            <br/>

                                            <label for="sel2"><fmt:message key="role"/></label>
                                            <select class="form-control" id="sel2" name="role">
                                                <option value="${administrator.role}" <c:if test="${user.userRole == administrator}">
                                                    selected
                                                </c:if>>
                                                    <fmt:message key="administrator"/>
                                                </option>
                                                <option value="${customer.role}" <c:if test="${user.userRole == customer}">
                                                    selected
                                                </c:if>>
                                                    <fmt:message key="customer"/>
                                                </option>
                                            </select>

                                        <br>
                                        <c:if test="${requestScope.errorMessage == 'emptyField'}">
                                            <p class="text-danger"><fmt:message key="emptyFieldError"/></p>
                                        </c:if>
                                        <div class="row">
                                            <div class="col-" style="margin: 5px 5px">
                                                <button type="submit" class="btn btn-success" style="margin-left: 10px">
                                                    <fmt:message key="apply"/>
                                                </button>
                                            </div>
                                        </div>

                                    </form>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>

            </td>
        </tr>
        </c:forEach>

</div>
</fmt:bundle>

<script lang="javascript">
    $(window).on('load',function(){
        $('#myModal${requestScope.userIdWithError}').modal('show');
    });

    function closeChangeWindow() {
        window.location.href='${pageContext.request.contextPath}/users';
    }


</script>
</body>
</html>
