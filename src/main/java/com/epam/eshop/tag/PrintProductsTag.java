package com.epam.eshop.tag;

import com.epam.eshop.controller.Util;
import com.epam.eshop.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

/**
 * Created by artch on 28.09.2020.
 */
public class PrintProductsTag extends TagSupport {
    private List<Product> products;
    private int amountProducts;
    private int indexCurrentProduct;
    private User currentUser;
    private String contextPath;

    private static final Logger LOGGER = LoggerFactory.getLogger(PrintProductsTag.class);

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public int doStartTag() throws JspException {
        this.amountProducts = products.size();
        this.indexCurrentProduct = 0;
        this.currentUser = Util.getUserFromSession(pageContext.getSession());
        contextPath = pageContext.getRequest().getServletContext().getContextPath();

        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doAfterBody() throws JspException {
        JspWriter out = pageContext.getOut();
        if (indexCurrentProduct < amountProducts) {
            Product product = products.get(indexCurrentProduct);
            try {
                out.write("<div class=\"row\">");
                out.write("<div class=\"container p-3 my-3 border\">");
                out.write("<div class=\"row\">");

                out.write("<div class=\"col-sm-3\" style=\"margin-left: 10px; margin-right: 10px;\">");
                out.write("<img src=\"" + contextPath + "/" +
                        product.getImgURL() + "\" class=\"rounded\" alt=\"\"" +
                        " style=\"max-height: 100px; max-width: 200px\"/>");
                out.write("</div>");

                out.write("<div class=\"col-sm-6\" style=\"margin-left: 10px; margin-right: 10px;\">");
                out.write(product.toString() + "<br/><br/>");

                if (currentUser != null && currentUser.getUserRole().getRole().equals(UserRole.ADMINISTRATOR)) {
                    out.write(product.isActive() ? "<span class=\"badge badge-primary\">" + "<h6>active</h6>" + "</span>" :
                            "<span class=\"badge badge-danger\">" + "<h6>inactive</h6>" + "</span>");
                }

                out.write("</div>");

                out.write("<div class=\"col-sm-2\" style=\"margin-left: 10px; margin-right: 10px;\">");
                out.write("<div class=\"row\">");
                out.write("<a href=\"");
                out.write(contextPath + "/products/" + product.getCategory().getDatabaseValue() + "/" + product.getId() + "\"" +
                        " class=\"btn btn-info\" role=\"button\" style=\"margin: 5px 5px\">View details</a>");
                out.write("</div>");

                if (currentUser != null && currentUser.getUserRole().getRole().equals(UserRole.CUSTOMER)) {
                    String formId = "add_form" + product.getId();

                    out.write("<div class=\"row\">");
                    out.write("<form id=\"" + formId + "\" onsubmit=\"return addToCart(\'" + formId + "\')\" " +
                            "action=\"" + contextPath + "/cart\"" + " method=\"post\" >");
                    out.write("<input type=\"hidden\" name=\"actionCart\" value=\"addProduct\">");
                    out.write("<input type=\"hidden\" name=\"productId\" value=\"" + product.getId() + "\">");
                    out.write("<button  type=\"submit\"  class=\"btn btn-success\" style=\"margin: 5px 5px\">Add to cart</button>");
                    out.write("</form>");
                    out.write("</div>");
                } else if (currentUser != null && currentUser.getUserRole().getRole().equals(UserRole.ADMINISTRATOR)) {
                    out.write("<div class=\"row\">");

                    out.write("<button type=\"button\" class=\"btn btn-primary\" data-toggle=\"modal\" " +
                            "data-target=\"#myModal" + product.getId() + "\" style=\"margin: 5px 5px\">Edit</button>");
                    out.write("<div class=\"modal fade\" id=\"myModal" + product.getId() + "\">");
                    out.write("<div class=\"modal-dialog\">");
                    out.write("<div class=\"modal-content\">");
                    out.write("<div class=\"modal-header\">");
                    out.write("<h4 class=\"modal-title\">Edit product <strong>" +
                            product.getProducer() + " " + product.getName() + "</strong></h4>");
                    out.write("<button type=\"button\" class=\"close\" data-dismiss=\"modal\">×</button>");
                    out.write("</div>");
                    out.write("<div class=\"modal-body\">");
                    out.write("<div class=\"container p-3 my-3 border\">");
                    out.write("<form id=\"change_form" + product.getId() + "\" action=\"" + contextPath + "/products/" +
                            product.getCategory().getDatabaseValue() + "/" + product.getId() + "\" method=\"post\" enctype=\"multipart/form-data\">");
                    out.write("<input type=\"hidden\" value=\"changeProduct\" name=\"productAction\"/>");
                    out.write("<input type=\"hidden\" value=\"" + product.getCategory().getDatabaseValue() + "\" name=\"category\"/>");
                    out.write("<input type=\"hidden\" value=\"" + product.getId() + "\" name=\"productId\"/>");
                    out.write("<input type=\"hidden\" value=\"" + product.getImgURL() + "\" name=\"imgURL\"/>");
                    out.write("<input type=\"hidden\" value=\"" + contextPath + "/products?category=" + product.getCategory().getDatabaseValue() + "\" name=\"returnPath\"/>");

                    printFieldForModalWindow(out, "Producer", "text", "producer", product.getProducer());
                    printFieldForModalWindow(out, "Name", "text", "name", product.getName());
                    printFieldForModalWindow(out, "Price", "number", "price", String.valueOf(product.getPrice()));
                    printFieldForModalWindow(out, "Description", "text", "description", (product.getDescription() == null ? "" : product.getDescription()));

                    if (product instanceof MonitorProduct) {
                        MonitorProduct monitor = (MonitorProduct) product;
                        printFieldForModalWindow(out, "Diagonal", "number", "diagonal", String.valueOf(monitor.getDiagonal()));
                        printFieldForModalWindow(out, "Panel type", "text", "panelType", monitor.getPanelType());
                        printFieldForModalWindow(out, "Brightness", "number", "brightness", String.valueOf(monitor.getBrightness()));
                    } else if (product instanceof KeyboardProduct) {
                        KeyboardProduct keyboard = (KeyboardProduct) product;
                        printFieldForModalWindow(out, "Connection type", "text", "connectionType", keyboard.getConnectionType());
                        printFieldForModalWindow(out, "Light color", "text", "lightColor", keyboard.getLightColor());
                        out.write("<label for=\"sel2\"><strong>Mechanical:</strong></label>");
                        out.write("<select class=\"form-control\" id=\"sel2\" name=\"role\">");
                        out.write("<option value=\"1\" " + (keyboard.isMechanical() ? "selected" : "") + ">" + "Mechanical" + "</option>");
                        out.write("<option value=\"0\" " + (keyboard.isMechanical() ? "" : "selected") + ">" + "Not mechanical" + "</option>");
                        out.write("</select>");
                    }

                    out.write("<br/><label for=\"sel1\"><strong>Active:</strong></label>");
                    out.write("<select class=\"form-control\" id=\"sel1\" name=\"active\">");
                    out.write("<option value=\"1\" " + (product.isActive() ? "selected" : "") + ">" + "Active" + "</option>");
                    out.write("<option value=\"0\" " + (product.isActive() ? "" : "selected") + ">" + "InActive" + "</option>");
                    out.write("</select><br/>");

                    out.write("<div class=\"form-group\">");
                    out.write("<strong>Image:</strong>");
                    out.write("<input type=\"hidden\" value=\"img/" + product.getCategory().getDatabaseValue() + "\" name=\"destination\"/>");
                    out.write("<input type=\"file\" class=\"form-control-file border\" name=\"img\">");
                    out.write("</div>");

                    out.write("<div class=\"row\">");
                    out.write("<div class=\"col-\" style=\"margin: 5px 5px\">");
                    out.write("<button type=\"submit\" class=\"btn btn-success\" style=\"margin-left: 10px\">Apply</button>");
                    out.write("</div>");
                    out.write("</div>");

                    out.write("</form>");
                    out.write("</div>");
                    out.write("</div>");
                    out.write("</div>");
                    out.write("</div>");
                    out.write("</div>");

                    out.write("</div>");
                }

                out.write("</div>");

                out.write("</div>");
                out.write("</div>");
                out.write("</div>");

                indexCurrentProduct++;
                return EVAL_BODY_AGAIN;
            } catch (IOException e) {
                LOGGER.error("Writing error in custom tag |{}|", this.getClass().getSimpleName());
            }
        }
        return SKIP_BODY;
    }

    private void printFieldForModalWindow(JspWriter out, String fieldTitle, String fieldType, String fieldName, String fieldValue) throws IOException {
        out.write("<div class=\"row\" style=\"margin: 10px 10px\">");
        out.write("<div class=\"col-5\">");
        out.write("<strong style=\"margin-right: 10px\">" + fieldTitle + "</strong>");
        out.write("</div>");
        out.write("<div class=\"col-5\">");
        out.write("<div class=\"form-group\">");
        out.write("<input type=\"" + fieldType + "\" " + ("price".equals(fieldName) ? "min=\"0.00\" step=\"0.01\"" : "") + " class=\"form-control form-control-sm\"" +
                "name=\"" + fieldName + "\" value=\"" + fieldValue + "\"" + (("producer".equals(fieldName) || ("name".equals(fieldName)) ? "required" : "") + ">"));
        out.write("</div>");
        out.write("</div>");
        out.write("</div>");
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
