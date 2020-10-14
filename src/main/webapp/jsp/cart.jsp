<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customTags" %>
<html>
<head>
    <title>Cart</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>

<c:import url="jspComponent/head.jsp?currentPage=cart"/>

<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>

<fmt:bundle basename="messages" prefix="cart.">

<div id="cartBody" class="container">
    <c:choose>

        <c:when test="${requestScope.success}">
            <div class="alert alert-success">
                <strong>Success!</strong> Thank you for order!
            </div>
        </c:when>
        <c:when test="${requestScope.success}">
            <img src="${pageContext.request.contextPath}/img/error.png" class="mx-auto d-block"
                 style="max-width: 200px">
        </c:when>
    </c:choose>


    <ctg:printProductsInCartOrOrder productsWithAmount="${sessionScope.currentUserCart.productsInCart}" inCart="true">

    </ctg:printProductsInCartOrOrder>


    <c:choose>
        <c:when test="${sessionScope.currentUserCart.productsInCart.isEmpty()}">
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <div class="container">
                <img src="${pageContext.request.contextPath}/img/empty-cart.png" class="mx-auto d-block"
                     style="max-width: 200px">
            </div>
        </c:when>
        <c:otherwise>
            <br/>
            <br/>
            <br/>

            <div class="navbar navbar-expand-sm bg-dark navbar-dark fixed-bottom" style="margin-top: 10px">
                <div class="col-7">

                </div>
                <div class="col-2">
                    <kbd><fmt:message key="totalPrice"/></kbd>
                    <h4>
                        <span class="badge badge-secondary">
                        <fmt:formatNumber type="number" maxFractionDigits="2"
                                          value="${sessionScope.currentUserCart.getTotalPrice()}"/>$
                        </span>
                    </h4>
                </div>
                <div class="col-2">
                    <form id="form_confirm" action="${pageContext.request.contextPath}/cart" method="post">
                        <input type="hidden" name="actionCart" value="confirmOrder">
                        <button type="submit" class="btn btn-primary"><fmt:message key="confirmOrder"/></button>
                    </form>
                </div>
            </div>

        </c:otherwise>
    </c:choose>


</div>
</fmt:bundle>

<script lang="javascript">

    function change(formId) {

        var formElement = $('#' + formId);
        var addUrl = formElement.prop('action');

        $.post(addUrl, formElement.serializeArray())
            .done(function (resp) {
                window.location.reload();
            })
            .fail(function (err) {
                console.log(err);
                alert('Failed to change cart');
            });

        return false;
    }

    (function () {
        $('#form_confirm').on('submit', function (e) {
            e.preventDefault();

            confirmOrder();
        })
    })();

    function confirmOrder() {

        var formElement = $('#form_confirm');
        var confirmOrderUrl = formElement.prop('action');

        $.post(confirmOrderUrl, formElement.serializeArray())
            .done(function (resp) {
                var newCartBody = $($.parseHTML(resp)).filter("#cartBody").html();

                $('#cartBody').html(newCartBody);
            })
            .fail(function (err) {
                console.log(err);
                alert('Failed to confirm order');
            });

    }

</script>
</body>
</html>