<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<body>

<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>

<fmt:bundle basename="messages" prefix="langChangePanel.">

    <c:choose>
        <c:when test="${sessionScope.lang != null}">
            <c:set var="lang" value="${sessionScope.lang}"/>
        </c:when>
        <c:otherwise>
            <c:set var="lang" value="${pageContext.request.locale.language}"/>
        </c:otherwise>
    </c:choose>

<nav class="navbar navbar-expand-sm bg-light navbar-light">
    <ul class="navbar-nav">
        <c:if test="${lang == 'en'}">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle"  href="#" id="navbardrop" data-toggle="dropdown">
                    <fmt:message key="langEnglish"/>
                </a>
                <div class="dropdown-menu">
                    <a class="dropdown-item" role="button" onclick="changeLang('ru')"><fmt:message key="langRussian"/></a>
                </div>
            </li>
        </c:if>
        <c:if test="${lang == 'ru'}">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                    <fmt:message key="langRussian"/>
                </a>
                <div class="dropdown-menu">
                    <a class="dropdown-item" role="button" onclick="changeLang('en')"><fmt:message key="langEnglish"/></a>
                </div>
            </li>
        </c:if>

    </ul>

</nav>
</fmt:bundle>


<script lang="javascript">

    function changeLang(lang) {
        $.post('${pageContext.request.contextPath}/lang?lang=' + lang)
            .done(function (resp) {
                window.location.reload();
            })
            .fail(function (e) {
                console.log(e);
                alert('Failed to change language');
            })
        ;
    }

</script>

</body>
</html>
