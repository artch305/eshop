<%@ tag import="com.epam.eshop.entity.Product" %>
<%@ tag import="com.epam.eshop.entity.MonitorProduct" %>
<%@ tag import="com.epam.eshop.entity.KeyboardProduct" %>
<%@ tag import="com.sun.xml.internal.ws.api.ha.StickyFeature" %>
<%@ tag import="com.epam.eshop.entity.Category" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="category" required="true" type="java.lang.String" rtexprvalue="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    Product product = null;
    if (Category.MONITORS.getDatabaseValue().equals(category)){
        product = new MonitorProduct();
        product.setCategory(category);
    } else if (Category.KEYBOARDS.getDatabaseValue().equals(category)){
        product = new KeyboardProduct();
        product.setCategory(category);
    }
%>

<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>

<fmt:bundle basename="messages" prefix="">

<div class="modal fade" id="myModal_<%=category%>">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <h4 class="modal-title"><fmt:message key="product.addNewProduct"/></h4>
                <button type="button" class="close" data-dismiss="modal">x</button>
            </div>

            <div class="modal-body">
                <div class="container p-3 my-3 border">
                    <form action="${pageContext.request.contextPath}/products/<%=category%>/0"
                          enctype="multipart/form-data" method="post">

                        <input type="hidden" value="<%=category%>" name="category"/>
                        <input type="hidden" value="addProduct" name="productAction"/>
                        <input type="hidden" value="${pageContext.request.contextPath}/products/<%=category%>/" name="currentPageForReturn"/>

                        <div class="row" style="margin: 10px 10px">
                            <div class="col-5">
                                <strong style="margin-right: 10px"><fmt:message key="product.producer"/></strong>
                            </div>
                            <div class="col-5">
                                <div class="form-group">
                                    <input type="text" required class="form-control form-control-sm"
                                           name="producer" value="">
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
                                           name="name" value="">
                                </div>
                            </div>
                        </div>

                        <div class="row" style="margin: 10px 10px">
                            <div class="col-5">
                                <strong style="margin-right: 10px"><fmt:message key="product.priceInForm"/></strong>
                            </div>
                            <div class="col-5">
                                <div class="form-group">
                                    <input type="number" min="0.00" step="0.01" required class="form-control form-control-sm"
                                           name="price" value="">
                                </div>
                            </div>
                        </div>

                        <div class="row" style="margin: 10px 10px">
                            <div class="col-5">
                                <strong style="margin-right: 10px"><fmt:message key="product.description"/></strong>
                            </div>
                            <div class="col-5">
                                <div class="form-group">
                                    <textarea class="form-control form-control-sm"
                                               name="description"></textarea>
                                </div>
                            </div>
                        </div>

                            <% MonitorProduct monitor=null;
    KeyboardProduct keyboard=null;
if (product instanceof MonitorProduct) {
    monitor = (MonitorProduct) product;
    } else if (product!=null) {
    keyboard = (KeyboardProduct) product;
}%>

                            <% assert product != null; %>
                        <c:if test="${category == 'monitors'}"><% assert monitor != null; %>
                        <div class="row" style="margin: 10px 10px">
                            <div class="col-5">
                                <strong style="margin-right: 10px"><fmt:message key="monitor.diagonal"/></strong>
                            </div>
                            <div class="col-5">
                                <div class="form-group">
                                    <input type="number" required min="0" class="form-control form-control-sm"
                                           name="diagonal" value="">
                                </div>
                            </div>
                        </div>

                        <div class="row" style="margin: 10px 10px">
                            <div class="col-5">
                                <strong style="margin-right: 10px"><fmt:message key="monitor.panelType"/></strong>
                            </div>
                            <div class="col-5">
                                <div class="form-group">
                                    <input type="text" required class="form-control form-control-sm"
                                           name="panelType" value="">
                                </div>
                            </div>
                        </div>

                            <div class="row" style="margin: 10px 10px">
                                <div class="col-5">
                                    <strong style="margin-right: 10px"><fmt:message key="monitor.brightness"/></strong>
                                </div>
                                <div class="col-5">
                                    <div class="form-group">
                                        <input type="number" min="0" required class="form-control form-control-sm"
                                               name="brightness" value="">
                                    </div>
                                </div>
                            </div>
                        </c:if>

                        <c:if test="${category == 'keyboards'}"><% assert keyboard != null; %>
                        <div class="row" style="margin: 10px 10px">
                            <div class="col-5">
                                <strong style="margin-right: 10px"><fmt:message key="keyboard.connectionType"/></strong>
                            </div>
                            <div class="col-5">
                                <div class="form-group">
                                    <input type="text" required class="form-control form-control-sm"
                                           name="connectionType" value="">
                                </div>
                            </div>
                        </div>

                        <div class="row" style="margin: 10px 10px">
                            <div class="col-5">
                                <strong style="margin-right: 10px"><fmt:message key="keyboard.lightColor"/></strong>
                            </div>
                            <div class="col-5">
                                <div class="form-group">
                                    <input type="text"  class="form-control form-control-sm"
                                           name="lightColor" value="">
                                </div>
                            </div>
                        </div>

                            <label for="sel1">
                                <strong style="margin-right: 10px"><fmt:message key="keyboard.mechanical"/></strong>
                            </label>
                            <select class="form-control" required id="sel1" name="mechanical">
                                <option value="1"><fmt:message key="keyboard.mechanicalSelection"/></option>
                                <option value="0" selected ><fmt:message key="keyboard.noMechanicalSelection"/></option>
                            </select>
                        </c:if>

                        <label for="sel1"><strong><fmt:message key="product.active"/></strong></label>
                        <select class="form-control" required id="sel1" name="active">
                            <option value="1" selected><fmt:message key="product.active"/></option>
                            <option value="0"><fmt:message key="product.inactive"/></option>
                        </select>
                        <br/>
                        <div class="form-group">
                            <strong><fmt:message key="product.img"/></strong>
                            <input type="hidden"  value="img/<%=category%>" name="destination"/>
                            <input type="file" class="form-control-file border" name="img">
                        </div>

                        <div class="row">
                            <div class="col-" style="margin: 5px 5px">
                                <button type="submit" class="btn btn-success" style="margin-left: 10px"><fmt:message key="product.filters.apply"/></button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</fmt:bundle>