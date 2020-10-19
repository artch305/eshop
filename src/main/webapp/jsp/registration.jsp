<%@ page import="com.epam.eshop.controller.constants.URLConstants" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Bootstrap Example</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>

<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>

<fmt:bundle basename="messages" prefix="registration.">
    <div class="container">
        <h2><fmt:message key="registration"/></h2>
        <form action="${pageContext.request.contextPath}/registration" method="post"
              class="<c:choose>
        <c:when test="${requestScope.errorMessage != null}">was-validated</c:when>
        <c:when test="${requestScope.errorMessage == null}">needs-validation</c:when>
        </c:choose>"
              novalidate>

            <div class="form-group">
                <label for="login"><fmt:message key="loginField"/></label>
                <input type="text" class="form-control" id="login" placeholder="<fmt:message key="loginMessage"/>" name="login" required>
                <div class="invalid-feedback"><c:if test="${requestScope.errorMessage == 'existLogin'}">
                    <fmt:message key="existLoginError"/>
                </c:if>
                </div>
            </div>


            <div class="form-group">
                <label for="email"><fmt:message key="emailField"/></label>
                <input type="email" class="form-control" id="email" placeholder="<fmt:message key="emailMessage"/>" name="email" required>
                <div class="invalid-feedback"><c:if test="${requestScope.errorMessage == 'existEmail'}">
                    <fmt:message key="existEmailError"/>
                </c:if></div>
            </div>


            <div class="form-group">
                <label for="pwd"><fmt:message key="passwordField"/></label>
                <input type="password" class="form-control" id="pwd" placeholder="<fmt:message key="passwordMessage"/>"
                       name="password" required>
                <div class="invalid-feedback"><c:if test="${requestScope.errorMessage == 'emptyField'}">
                    <fmt:message key="emptyFieldError"/>
                </c:if>
                </div>
            </div>

            <br/>
            <div class="row">
                <div class="col-" style="margin: 5px 5px">
                    <button type="submit" class="btn btn-primary"><fmt:message key="submit"/></button>
                </div>
                <div class="col-" style="margin: 5px 5px">
                    <a href="${pageContext.request.contextPath}/main" class="btn btn-success" role="button">Return</a>
                </div>
            </div>

        </form>
    </div>
</fmt:bundle>

<script>
    // Disable form submissions if there are invalid fields
    (function () {
        'use strict';
        window.addEventListener('load', function () {
            // Get the forms we want to add validation styles to
            var forms = document.getElementsByClassName('needs-validation');
            // Loop over them and prevent submission
            var validation = Array.prototype.filter.call(forms, function (form) {
                form.addEventListener('submit', function (event) {
                    if (form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        }, false);
    })();
</script>

</body>
</html>
