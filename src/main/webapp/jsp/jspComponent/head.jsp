<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="container-fluid">
    <div class="row">

        <div class="col-7" style="margin: 10px 10px">
            <img src="${pageContext.request.contextPath}/img/banner.jpg" class="img-thumbnail" alt="Cinque Terre" width="1000" height="280" style="max-height: 280px">
        </div>

        <div class="col-3" style="margin: 10px 10px">
            <c:choose>
                <c:when test="${sessionScope.currentUser != null}">
                    <div><c:import url="jspComponent/welcome.jsp"/></div>
                </c:when>
                <c:when test="${sessionScope.currentUser == null}">
                    <div><c:if test="${sessionScope.currentUser == null}"><c:import url="jspComponent/authentication.jsp?currentPageForReturn=${param.currentPageForReturn}"/></c:if></div>
                </c:when>
            </c:choose>
        </div>
    </div>
</div>
<br/>
<c:if test="${sessionScope.currentUser.userRole.role == 'administrator'}">
    <c:import url="jspComponent/adminNavPanel.jsp?currentPage=${param.currentPage}&currentPageForReturn=${param.currentPageForReturn}"/>

</c:if>

<c:if test="${sessionScope.currentUser.userRole.role == 'customer'}">
    <c:import url="jspComponent/userNavPanel.jsp?currentPage=${param.currentPage}&currentPageForReturn=${param.currentPageForReturn}"/>
</c:if>

<c:if test="${sessionScope.currentUser == null}">
    <c:import url="jspComponent/unlogginedUserNavPanel.jsp"/>
</c:if>



</body>
</html>
