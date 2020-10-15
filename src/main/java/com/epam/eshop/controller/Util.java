package com.epam.eshop.controller;

import com.epam.eshop.controller.constants.AttributesNames;
import com.epam.eshop.controller.constants.ParameterNames;
import com.epam.eshop.controller.constants.URLConstants;
import com.epam.eshop.entity.Cart;
import com.epam.eshop.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by artch on 15.10.2020.
 */
public class Util {
    public static User getUserFromSession (HttpSession session){
        return (User) session.getAttribute(AttributesNames.CURRENT_USER);
    }

    public static Cart getCartFromSession (HttpSession session){
        return (Cart) session.getAttribute(AttributesNames.CURRENT_USER_CART);
    }

    public static void setAttributeToSession(HttpSession session, Object object, String attributeName){
        session.setAttribute(attributeName, object);
    }

    public static void replaceSuccessAttrFromSessionIntoRequest (HttpServletRequest request){
        HttpSession session = request.getSession();
        request.setAttribute(AttributesNames.SUCCESS, session.getAttribute(AttributesNames.SUCCESS));
        session.removeAttribute(AttributesNames.SUCCESS);
    }

    public static boolean checkUserRole (User user, String role){
        return user != null && user.getUserRole().getRole().equals(role);
    }

    public static String getIdFromURL(String pathInfo){
        return pathInfo.replaceAll("(/?\\w+/)|(/)", "");
    }

    public static String getReturnPath (HttpServletRequest request){
        String returnPath = request.getParameter(ParameterNames.CURRENT_PAGE_FOR_RETURN);

        if (returnPath == null || returnPath.trim().isEmpty()) {
            returnPath = request.getContextPath() + URLConstants.MAIN;
        }
        return returnPath;
    }

    public static String getCategoryFromURL(String requestURI){
        String category = "";
        Pattern pattern = Pattern.compile("[a-zA-Z]+(?=/[0-9]+)");
        Matcher matcher = pattern.matcher(requestURI);

        if (matcher.find()) {
            category = matcher.group();
        }
        return category;
    }
}
