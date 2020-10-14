<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>error exception</title>
</head>
<body>

</body>
</html>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Error 500 page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
<c:import url="jspComponent/head.jsp?currentPageForReturn=${pageContext.request.contextPath}/main"/>

<div class="container">
    <a href="${pageContext.request.contextPath}/main" class="btn btn-success" role="button">Return</a>
</div>

<div class="col-7" style="margin: 10px 10px">
    <img src="${pageContext.request.contextPath}/img/error-image.png" class="img-thumbnail" alt="Cinque Terre"
         style="max-width: 1000px">
</div>

</body>
</html>
