<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<body>

<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>

<fmt:bundle basename="messages" prefix="authentication.">

    <div class="container">
        <h2><fmt:message key="authentication"/></h2>
        <form id="auth-form" action="${pageContext.request.contextPath}/login" method="post" class=" <c:choose>
        <c:when test="${sessionScope.errorLogin != null}">was-validated</c:when>
        <c:when test="${sessionScope.errorLogin == null}">needs-validation</c:when>
        </c:choose>"
              novalidate>
            <input type="hidden" name="currentPageForReturn" value="${param.currentPageForReturn}">
            <div class="form-group">
                <label for="login"><fmt:message key="loginField"/></label>
                <input type="text" class="form-control" id="login" placeholder="<fmt:message key="loginDefault"/>"
                       name="login" required>
            </div>
            <div class="form-group">
                <label for="pwd"><fmt:message key="passwordField"/></label>
                <input type="password" class="form-control" id="pwd" placeholder="<fmt:message key="passwordDefault"/>"
                       name="password" required>
                <div class="invalid-feedback"><c:if test="${sessionScope.errorLogin  != null}">
                    <fmt:message key="invalidData"/>
                </c:if>
                </div>
            </div>
            <div class="container" >
                <div class="row">
                    <div class="col-" style="margin-right: 20px; margin-bottom: 10px">
                        <button type="submit" class="btn btn-primary" ><fmt:message key="submit"/></button>
                    </div>
                    <div class="col-" >
                        <a href="${pageContext.request.contextPath}/registration" class="btn btn-info" role="button"><fmt:message
                            key="registrationButton"/></a>
                    </div>
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
