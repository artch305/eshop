<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customTags" %>
<html>
<head>
    <title>Filter for monitors</title>
</head>
<body>

<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>

<fmt:bundle basename="messages" prefix="product.">

<div class="container p-3 my-3 border">
    <form action="${pageContext.request.contextPath}/products" method="post">
        <input type="hidden" name="actionFilters" value="applyFilters">
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
            <h6><fmt:message key="producer"/></h6>
            <c:forEach var="producerForMonitors" items="${sessionScope.allValuesForFilters.producersForMonitors}">
                <div class="form-check">
                    <label class="form-check-label">
                        <input type="checkbox" class="form-check-input" name="producers" value="${producerForMonitors}"
                               <c:if test="${sessionScope.filters.producers.contains(producerForMonitors)}">checked</c:if>>${producerForMonitors}
                    </label>
                </div>
            </c:forEach>
        </div>

        <div class="row" style="margin: 10px 10px">
            <h6><fmt:message key="filters.monitor.brightness"/></h6><br>
        </div>
        <div class="row" style="margin: 10px 10px">
            <div class="form-group">
                <input type="number" min="0" class="form-control form-control-sm" placeholder="<fmt:message key="filters.monitor.brightness"/>" name="brightness"
                       value="<c:if test="${sessionScope.filters.brightness != 0}">${sessionScope.filters.brightness}</c:if>">
            </div>
        </div>

        <div class="container p-3 my-3 border" style="margin: 10px 10px">
            <c:set var="diadonalsList" value="diagonals"/>
            <h6><fmt:message key="filters.monitor.diagonal"/></h6>
            <c:forEach var="diagonal" items="${sessionScope.allValuesForFilters.diagonals}">
                <div class="form-check">
                    <label class="form-check-label">
                        <input type="checkbox" class="form-check-input" name="diagonals" value="${diagonal}"
                               <c:if test="${sessionScope.filters.diagonals.contains(diagonal)}">checked</c:if>>${diagonal}"
                    </label>
                </div>
            </c:forEach>
        </div>

        <div class="container p-3 my-3 border" style="margin: 10px 10px">
            <c:set var="panelTypesList" value="panelTypes"/>
            <h6><fmt:message key="filters.monitor.panelType"/></h6>
            <c:forEach var="panelType" items="${sessionScope.allValuesForFilters.panelTypes}">
                <div class="form-check">
                    <label class="form-check-label">
                        <input type="checkbox" class="form-check-input" name="panelTypes" value="${panelType}"
                               <c:if test="${sessionScope.filters.panelTypes.contains(panelType)}">checked</c:if>>${panelType}
                    </label>
                </div>
            </c:forEach>
        </div>

        <br>
        <div class="row">
            <button type="submit" class="btn btn-success" style="margin-left: 10px"><fmt:message key="filters.apply"/></button>
        </div>
    </form>
    <div class="row">
        <form id="reset" action="${pageContext.request.contextPath}/products" method="post">
            <input type="hidden" name="actionFilters" value="resetFilters">
            <button type="submit" class="btn btn-info" style="margin-left:  10px"><fmt:message key="filters.reset"/></button>
        </form>
    </div>
</div>
</fmt:bundle>
</body>
</html>
