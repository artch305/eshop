<%@ tag import="com.epam.eshop.entity.Product" %>
<%@ tag import="com.epam.eshop.entity.MonitorProduct" %>
<%@ tag import="com.epam.eshop.entity.KeyboardProduct" %>
<%@ tag import="com.sun.xml.internal.ws.api.ha.StickyFeature" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="product" required="true" type="com.epam.eshop.entity.Product" rtexprvalue="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="categoryParamName" value="category"/>

<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>

<fmt:bundle basename="messages" prefix="">

    <div class="modal fade" id="myModal">
        <div class="modal-dialog">
            <div class="modal-content">

                <div class="modal-header">
                    <h4 class="modal-title"><fmt:message key="product.editProduct"/> <strong>${product.producer} ${product.name}</strong></h4>
                    <button type="button" class="close" data-dismiss="modal">x</button>
                </div>

                <div class="modal-body">
                    <div class="container p-3 my-3 border">
                        <form id="change_form${product.id}"
                              action="${pageContext.request.contextPath}/products/${product.category.databaseValue}/${product.id}"
                              enctype="multipart/form-data" method="post">

                            <input type="hidden" value="changeProduct" name="productAction"/>
                            <input type="hidden" value="${product.category.databaseValue}" name="category"/>
                            <input type="hidden" value="${product.id}" name="productId"/>
                            <input type="hidden" value="${product.imgURL}" name="imgURL"/>
                            <input type="hidden"
                                   value="${pageContext.request.contextPath}/products/${product.category.databaseValue}/${product.id}"
                                   name="returnPath"/>

                            <div class="row" style="margin: 10px 10px">
                                <div class="col-5">
                                    <strong style="margin-right: 10px"><fmt:message key="product.producer"/></strong>
                                </div>
                                <div class="col-5">
                                    <div class="form-group">
                                        <input type="text" required class="form-control form-control-sm"
                                               name="producer" value="${product.producer}">
                                    </div>
                                </div>
                            </div>

                            <div class="row" style="margin: 10px 10px">
                                <div class="col-5">
                                    <strong style="margin-right: 10px"><fmt:message key="product.name"/></strong>
                                </div>
                                <div class="col-5">
                                    <div class="form-group">
                                        <input type="text" required class="form-control form-control-sm"
                                               name="name" value="${product.name}">
                                    </div>
                                </div>
                            </div>

                            <div class="row" style="margin: 10px 10px">
                                <div class="col-5">
                                    <strong style="margin-right: 10px"><fmt:message key="product.priceInForm"/> </strong>
                                </div>
                                <div class="col-5">
                                    <div class="form-group">
                                        <input type="number" min="0.00" step="0.01" class="form-control form-control-sm"
                                               name="price" value="${product.price}">
                                    </div>
                                </div>
                            </div>

                            <div class="row" style="margin: 10px 10px">
                                <div class="col-5">
                                    <strong style="margin-right: 10px"><fmt:message key="product.description"/></strong>
                                </div>
                                <div class="col-5">
                                    <div class="form-group">
                                        <input type="text" class="form-control form-control-sm"
                                               name="description" value="${product.description}">
                                    </div>
                                </div>
                            </div>

                            <% MonitorProduct monitor = null;
                                KeyboardProduct keyboard = null;
                                if (product instanceof MonitorProduct) {
                                    monitor = (MonitorProduct) product;
                                } else if (product instanceof KeyboardProduct) {
                                    keyboard = (KeyboardProduct) product;
                                }%>
                            <c:if test="<%=(monitor != null)%>"><% assert monitor != null; %>
                                <div class="row" style="margin: 10px 10px">
                                    <div class="col-5">
                                        <strong style="margin-right: 10px"><fmt:message key="monitor.diagonal"/></strong>
                                    </div>
                                    <div class="col-5">
                                        <div class="form-group">
                                            <input type="number" class="form-control form-control-sm"
                                                   name="diagonal" value="<%=monitor.getDiagonal()%>">
                                        </div>
                                    </div>
                                </div>

                                <div class="row" style="margin: 10px 10px">
                                    <div class="col-5">
                                        <strong style="margin-right: 10px"><fmt:message key="monitor.panelType"/></strong>
                                    </div>
                                    <div class="col-5">
                                        <div class="form-group">
                                            <input type="text" class="form-control form-control-sm"
                                                   name="panelType" value="<%=monitor.getPanelType()%>">
                                        </div>
                                    </div>
                                </div>

                                <div class="row" style="margin: 10px 10px">
                                    <div class="col-5">
                                        <strong style="margin-right: 10px"><fmt:message key="monitor.brightness"/></strong>
                                    </div>
                                    <div class="col-5">
                                        <div class="form-group">
                                            <input type="number" min="0" class="form-control form-control-sm"
                                                   name="brightness" value="<%=monitor.getBrightness()%>">
                                        </div>
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="<%=(keyboard != null)%>"><% assert keyboard != null; %>
                                <div class="row" style="margin: 10px 10px">
                                    <div class="col-5">
                                        <strong style="margin-right: 10px"><fmt:message key="keyboard.connectionType"/></strong>
                                    </div>
                                    <div class="col-5">
                                        <div class="form-group">
                                            <input type="text" class="form-control form-control-sm"
                                                   name="connectionType" value="<%=keyboard.getConnectionType()%>">
                                        </div>
                                    </div>
                                </div>

                                <div class="row" style="margin: 10px 10px">
                                    <div class="col-5">
                                        <strong style="margin-right: 10px"><fmt:message key="keyboard.lightColor"/></strong>
                                    </div>
                                    <div class="col-5">
                                        <div class="form-group">
                                            <input type="text" class="form-control form-control-sm"
                                                   name="lightColor" value="<%=keyboard.getLightColor()%>">
                                        </div>
                                    </div>
                                </div>

                                <label for="sel1"><fmt:message key="keyboard.mechanical"/></label>
                                <select class="form-control" id="sel1" name="mechanical">
                                    <option value="1" <c:if test="<%=keyboard.isMechanical()%>">
                                        selected
                                    </c:if>>
                                        mechanical
                                    </option>
                                    <option value="0" <c:if test="<%=!keyboard.isMechanical()%>">
                                        selected
                                    </c:if>>
                                        not mechanical
                                    </option>
                                </select>
                            </c:if>

                            <label for="sel1"><strong><fmt:message key="product.active"/></strong></label>
                            <select class="form-control" id="sel1" name="active">
                                <option value="1" <c:if test="<%=product.isActive()%>">
                                    selected
                                </c:if>>
                                    <fmt:message key="product.active"/>
                                </option>
                                <option value="0" <c:if test="<%=!product.isActive()%>">
                                    selected
                                </c:if>>
                                    <fmt:message key="product.inactive"/>
                                </option>
                            </select>
                            <br/>
                            <div class="form-group">
                                <strong>Image:</strong>
                                <input type="hidden" value="img/${product.category.databaseValue}" name="destination"/>
                                <input type="file" class="form-control-file border" name="img">
                            </div>

                            <div class="row">
                                <div class="col-" style="margin: 5px 5px">
                                    <button type="submit" class="btn btn-success" style="margin-left: 10px"><fmt:message key="product.filters.apply"/>
                                    </button>
                                </div>
                            </div>
                        </form>

                    </div>
                </div>
            </div>
        </div>
    </div>
</fmt:bundle>