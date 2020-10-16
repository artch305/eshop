<%@ tag import="com.sun.xml.internal.ws.api.ha.StickyFeature" %>
<%@ tag import="java.util.List" %>
<%@ tag import="com.epam.eshop.controller.Util" %>
<%@ tag import="com.epam.eshop.entity.*" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="fctg" %>
<%@ attribute name="product" required="true" type="com.epam.eshop.entity.Product" rtexprvalue="true" %>

<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>

<fmt:bundle basename="messages" prefix="">

    <%
        User user = Util.getUserFromSession(session);
        MonitorProduct monitor = null;
        KeyboardProduct keyboard = null;

        if (product.getCategory().getDatabaseValue().equals(Category.MONITORS.getDatabaseValue())) {
            monitor = (MonitorProduct) product;
        } else if (product.getCategory().getDatabaseValue().equals(Category.KEYBOARDS.getDatabaseValue())) {
            keyboard = (KeyboardProduct) product;
        }


    %>

    <div class="row">
        <div class="container p-3 my-3 border">
            <div class="row">
                <div class="col-sm-3" style="margin-left: 10px; margin-right: 10px;">
                    <img src="${pageContext.request.contextPath}/${product.imgURL}"
                         style="max-height: 100px; max-width: 200px"/>
                </div>
                <div class="col-sm-6" style="margin-left: 10px; margin-right: 10px;">
                    <strong><fmt:message key="product.producer"/></strong> ${product.producer};
                    <strong><fmt:message key="product.name"/></strong> ${product.name};
                    <c:choose>
                        <c:when test="<%=monitor != null%>"><% assert monitor != null; %>
                            <strong><fmt:message key="monitor.diagonal"/></strong> <%=monitor.getDiagonal()%>;
                            <strong><fmt:message key="monitor.panelType"/></strong> <%=monitor.getPanelType()%>;
                        </c:when>
                        <c:when test="<%=keyboard != null%>"><% assert keyboard != null; %>
                            <strong><fmt:message
                                    key="keyboard.connectionType"/></strong> <%=keyboard.getConnectionType()%>;
                            <strong><fmt:message key="keyboard.mechanical"/></strong>
                            <c:if test="<%=keyboard.isMechanical()%>">
                                <fmt:message key="keyboard.yes"/>;
                            </c:if>
                            <c:if test="<%=!keyboard.isMechanical()%>">
                                <fmt:message key="keyboard.no"/>;
                            </c:if>
                        </c:when>
                    </c:choose>
                    <kbd><fmt:message key="product.price"/></kbd> ${product.price}$
                    <br/>
                    <br/>
                    <c:if test="<%=(user != null && UserRole.ADMINISTRATOR.equals(user.getUserRole()))%>">
                        <c:choose>
                            <c:when test="${product.active}">
                                <span class="badge badge-primary"><fmt:message key="product.active"/></span>
                            </c:when>
                            <c:when test="${!product.active}">
                                <span class="badge badge-primary"><fmt:message key="product.inactive"/></span>
                            </c:when>
                        </c:choose>
                    </c:if>
                </div>
                <div class="col-sm-2" style="margin-left: 10px; margin-right: 10px;">

                    <div class="row">
                        <a href="${pageContext.request.contextPath}/products/${product.category.databaseValue}/${product.id}"
                           class="btn btn-info" role="button" style="margin: 5px 5px">
                            <img src="${pageContext.request.contextPath}/img/icons/icons8-view-64.png"
                                 style="max-height: 30px"></a>
                    </div>

                    <c:choose>
                        <c:when test="<%=(user != null && UserRole.CUSTOMER.equals(user.getUserRole()))%>">
                            <div class="row">
                                <form id="add_form${product.id}" onsubmit="return addToCart('add_form${product.id}')"
                                      action="${pageContext.request.contextPath}/cart" method="post">
                                    <input type="hidden" name="actionCart" value="addProduct">
                                    <input type="hidden" name="productId" value="${product.id}">
                                    <button type="submit" class="btn btn-success" style="margin: 5px 5px">
                                        <img src="${pageContext.request.contextPath}/img/icons/icons8-cart-80.png"
                                             style="max-height: 30px">
                                    </button>
                                </form>
                            </div>
                        </c:when>
                        <c:when test="<%=(user != null && UserRole.ADMINISTRATOR.equals(user.getUserRole()))%>">
                            <div class="row">
                                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal${product.id}"
                                         style="margin: 5px 5px">
                                    <img src="${pageContext.request.contextPath}/img/icons/icons8-edit-64.png"
                                         style="max-height: 30px">
                                </button>
                            </div>
                            <fctg:modalForChangeProduct product="${product}"
                            returnPath="${pageContext.request.contextPath}/products?category=${product.category.databaseValue}">

                            </fctg:modalForChangeProduct>
                        </c:when>
                    </c:choose>

                </div>
            </div>
        </div>
    </div>
</fmt:bundle>



