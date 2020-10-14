package com.epam.eshop.tag;

import com.epam.eshop.entity.Order;
import com.epam.eshop.entity.Product;
import com.epam.eshop.entity.User;
import com.epam.eshop.entity.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.*;

/**
 * Created by artch on 06.10.2020.
 */
public class PrintProductsInOrderOrCart extends TagSupport {

    private Map<Product, Integer> productsWithAmount;
    private Iterator<Map.Entry<Product, Integer>> iterator;
    private User user;
    private boolean inCart;
    private String contextPath;
    private Order order;
    private String currentLocal;
    private ResourceBundle rb;

    private static final Logger LOGGER = LoggerFactory.getLogger(PrintProductsInOrderOrCart.class);

    public void setProductsWithAmount(Map<Product, Integer> productsWithAmount) {
        this.productsWithAmount = productsWithAmount;
    }

    public void setInCart(boolean inCart) {
        this.inCart = inCart;
    }

    @Override
    public int doStartTag() throws JspException {
        Set<Map.Entry<Product, Integer>> productsEntry = productsWithAmount.entrySet();
        iterator = productsEntry.iterator();
        user = (User) pageContext.getSession().getAttribute("currentUser");
        contextPath = pageContext.getRequest().getServletContext().getContextPath();
        order = (Order) pageContext.getRequest().getAttribute("currentOrder");
        JspWriter out = pageContext.getOut();

        if (pageContext.getSession().getAttribute("lang") == null) {
            currentLocal = pageContext.getRequest().getLocale().getLanguage();
            pageContext.getSession().setAttribute("lang", currentLocal);
        }

        rb = ResourceBundle.getBundle("messages", Locale.forLanguageTag((String) pageContext.getSession().getAttribute("lang")));

        try {
            out.write("<ul class=\"list-group\">");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doAfterBody() throws JspException {
        JspWriter out = pageContext.getOut();
        if (iterator.hasNext()) {
            Map.Entry<Product, Integer> productEntry = iterator.next();
            try {
                out.write("<li class=\"list-group-item\">");
                out.write("<div class=\"row\">");
                out.write("<div class=\"col-3\">");
                out.write("<img src=\"" + contextPath +
                        "/" + productEntry.getKey().getImgURL() + "\" class=\"rounded\" alt=\"" + productEntry.getKey().getName() + "\"" +
                        "style=\"max-height:70px\"/>");
                out.write("</div>");
                out.write("<div class=\"col-5\">");
                out.write("<strong>" + productEntry.getKey().getProducer() + " " + productEntry.getKey().getName() + "</strong> ");
                out.write("<br/><kbd>" + rb.getString("product.priceInForm") + "</kbd> " + productEntry.getKey().getPrice() + "$");
                out.write("</div>");

                if (inCart) {
                    printEditRemoveViewButtons(out, productEntry, "/cart", "actionCart");
                } else {
                    if (UserRole.ADMINISTRATOR.equals(user.getUserRole().getRole())) {
                        printEditRemoveViewButtons(out, productEntry, "/orders/" + getOrderId(order), "actionOrder");
                    } else {
                        out.write("<div class=\"col-2\">");
                        out.write("<div class=\"row\">");
                        out.write("<strong>" + rb.getString("products.amount") + "</strong>");
                        out.write("<kbd>" + productEntry.getValue() + "</kbd>");
                        out.write("</div>");
                        out.write("</div>");
                        printViewButton(out, productEntry);
                    }
                }

                out.write("</div>");
                out.write("</li>");

                return EVAL_BODY_AGAIN;
            } catch (IOException e) {
                LOGGER.error("Writing error in custom tag |{}|", this.getClass().getSimpleName());
            }
        }

        return SKIP_BODY;
    }

    private void printEditRemoveViewButtons(JspWriter out, Map.Entry<Product, Integer> productEntry, String urlForButton, String actionName) throws IOException {
        String formChangeId = "form_change_id" + productEntry.getKey().getId();
        String formRemoveId = "form_remove_id" + productEntry.getKey().getId();
        Order order = (Order) pageContext.getRequest().getAttribute("currentOrder");

        out.write("<div class=\"col-2\">");
        out.write("<form id=\"" + formChangeId + "\"" +
                "action=\"" + contextPath + urlForButton + "\" method=\"post\">");
        out.write("<div class=\"row\">");
        out.write("<div class=\"form-group\">");
        out.write("<input type=\"number\" min=\"1\" class=\"form-control form-control-sm\" name=\"amount\"" +
                "value=\"" + productEntry.getValue() + "\">");
        out.write("</div>");
        out.write("</div>");
        out.write("<div class=\"row\">");
        out.write("<input type=\"hidden\" name=\"productId\" value=\"" + productEntry.getKey().getId() + "\">");
        out.write("<input type=\"hidden\" name=\"orderId\" value=\"" + getOrderId(order) + "\">");
        out.write("<input type=\"hidden\" name=\"" + actionName + "\" value=\"changeAmountProduct\">");
        out.write("<button type=\"submit\" class=\"btn btn-light\"><img src=\"" +
                contextPath + "/img/icons/icons8-edit-64.png\" style=\"max-height: 30px\"></button>");
        out.write("</div>");
        out.write("</form>");
        out.write("</div>");

        out.write("<div class=\"col-2\">");
        out.write("<div class=\"container\">");
        printViewButton(out, productEntry);
        out.write("<div class=\"row\">");
        out.write("<form id=\"" + formRemoveId + "\"" +
                "action=\"" + contextPath + urlForButton + "\" method=\"post\">");

        out.write("<input type=\"hidden\" name=\"productId\" value=\"" + productEntry.getKey().getId() + "\">");

        out.write("<input type=\"hidden\" name=\"orderId\" value=\"" + getOrderId(order) + "\">");

        out.write("<input type=\"hidden\" name=\"" + actionName + "\" value=\"removeProduct\">");
        out.write("<button type=\"submit\" class=\"btn btn-danger\"><img src=\"" + contextPath +
                "/img/icons/icons8-remove-64.png\" style=\"max-height: 30px\"></button>");
        out.write("</form>");
        out.write("</div>");
        out.write("</div>");
        out.write("</div>");
    }

    private Integer getOrderId(Order order) {
        if (order == null) {
            return null;
        }

        return order.getId();
    }

    private void printViewButton(JspWriter out, Map.Entry<Product, Integer> productEntry) throws IOException {
        out.write("<div class=\"row\">");
        out.write("<a href=\"" + contextPath + "/products/" +
                productEntry.getKey().getCategory().getDatabaseValue() + "/" + productEntry.getKey().getId() + "\"" +
                "class=\"btn btn-info\" role=\"button\" style=\"margin-bottom: 10px\"><img src=\"" + contextPath +
                "/img/icons/icons8-view-64.png\" style=\"max-height: 30px\"></a>");
        out.write("</div>");
    }

    @Override
    public int doEndTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            out.write("</ul>");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }
}
