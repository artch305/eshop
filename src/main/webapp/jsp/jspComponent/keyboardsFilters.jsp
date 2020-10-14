<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customTags" %>
<html>
<head>
    <title>Keyboard filters</title>
</head>
<body>

<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>

<fmt:bundle basename="messages" prefix="product.">
<div class="container p-3 my-3 border">
    <form action="${pageContext.request.contextPath}/products" method="post">
        <input type="hidden" name="action" value="applyFilters">
        <div class="row" style="margin: 10px 10px">
            <h3><span class="badge badge-pill badge-success"><fmt:message key="filters.title"/></span></h3>
        </div>

        <c:if test="${sessionScope.currentUser.userRole.role == 'administrator'}">
        <div class="container p-3 my-3 border" style="margin: 10px 10px">
            <div class="form-check">
                <label class="form-check-label">
                    <input type="checkbox" class="form-check-input" name="active" value="true"
                           <c:if test="${sessionScope.filters.active.contains(true)}">checked</c:if>><fmt:message key="active"/>
                </label>
            </div>
            <div class="form-check">
                <label class="form-check-label">
                    <input type="checkbox" class="form-check-input" name="active" value="false"
                           <c:if test="${sessionScope.filters.active.contains(false)}">checked</c:if>><fmt:message key="inactive"/>
                </label>
            </div>
        </div>
        </c:if>

        <div class="row" style="margin: 10px 10px">
            <h6><fmt:message key="filters.minPrice"/></h6><br>
        </div>
        <div class="row" style="margin: 10px 10px">
            <div class="form-group">
                <input type="number" min="0" class="form-control form-control-sm" placeholder="<fmt:message key="filters.minPrice"/>" name="minPrice"
                       value="<c:if test="${sessionScope.filters.minPrice != 0}">${sessionScope.filters.minPrice}</c:if>">
            </div>
        </div>

        <div class="row" style="margin: 10px 10px">
            <h6><fmt:message key="filters.maxPrice"/></h6><br>
        </div>
        <div class="row" style="margin: 10px 10px">
            <div class="form-group">
                <input type="number" min="0" class="form-control form-control-sm" placeholder="<fmt:message key="filters.maxPrice"/>" name="maxPrice"
                       value="<c:if test="${sessionScope.filters.maxPrice != 0}">${sessionScope.filters.maxPrice}</c:if>">
            </div>
        </div>

        <div class="container p-3 my-3 border" style="margin: 10px 10px">
            <c:set var="producersList" value="producers"/>
            <h6><fmt:message key="filters.producer"/>r</h6>
            <c:forEach var="connectionType" items="${sessionScope.allValuesForFilters.get(producersList)}">
                <div class="form-check">
                    <label class="form-check-label">
                        <input type="checkbox" class="form-check-input" name="producers" value="${connectionType}"
                               <c:if test="${sessionScope.filters.producers.contains(connectionType)}">checked</c:if>>${connectionType}
                    </label>
                </div>
            </c:forEach>
        </div>

        <div class="container p-3 my-3 border" style="margin: 10px 10px">
            <c:set var="connectionTypesList" value="connectionTypes"/>
            <h6><fmt:message key="filters.keyboard.connectionTypes"/></h6>
            <c:forEach var="connectionType" items="${sessionScope.allValuesForFilters.get(connectionTypesList)}">
                <div class="form-check">
                    <label class="form-check-label">
                        <input type="checkbox" class="form-check-input" name="connectionTypes" value="${connectionType}"
                               <c:if test="${sessionScope.filters.connectionTypes.contains(connectionType)}">checked</c:if>>${connectionType}
                    </label>
                </div>
            </c:forEach>
        </div>

        <div class="container p-3 my-3 border" style="margin: 10px 10px">
            <h6><fmt:message key="filters.keyboard.mechanical"/></h6>
            <div class="form-check">
                <label class="form-check-label">
                    <input type="checkbox" class="form-check-input" name="mechanical" value="true"
                           <c:if test="${sessionScope.filters.mechanical.contains(true)}">checked</c:if>><fmt:message key="filters.keyboard.yes"/>
                </label>
            </div>
            <div class="form-check">
                <label class="form-check-label">
                    <input type="checkbox" class="form-check-input" name="mechanical" value="false"
                           <c:if test="${sessionScope.filters.mechanical.contains(false)}">checked</c:if>><fmt:message key="filters.keyboard.no"/>
                </label>
            </div>
        </div>

        <div class="container p-3 my-3 border" style="margin: 10px 10px">
            <c:set var="lightColorsList" value="lightColors"/>
            <h6><fmt:message key="filters.keyboard.lightColor"/></h6>
            <c:forEach var="lightColor" items="${sessionScope.allValuesForFilters.get(lightColorsList)}">
                <div class="form-check">
                    <label class="form-check-label">
                        <input type="checkbox" class="form-check-input" name="lightColors" value="${lightColor}"
                               <c:if test="${sessionScope.filters.lightColors.contains(lightColor)}">checked</c:if>>${lightColor}
                    </label>
                </div>
            </c:forEach>
        </div>

        <br>
        <div class="row">
            <div class="col-" style="margin: 5px 5px">
                <button type="submit" class="btn btn-success" style="margin-left: 10px"><fmt:message key="filters.apply"/></button>
            </div>
        </div>
    </form>
    <div class="row">
        <form id="reset" action="${pageContext.request.contextPath}/products?category=keyboards" method="post">
            <input type="hidden" name="action" value="resetFilters">
            <button type="submit" class="btn btn-info" style="margin-left:  10px"><fmt:message key="filters.reset"/></button>
        </form>
    </div>
</div>
</fmt:bundle>
</body>
</html>
