package com.epam.eshop.controller;

import com.epam.eshop.controller.constants.AttributesNames;
import com.epam.eshop.controller.constants.ParameterNames;
import com.epam.eshop.controller.constants.URLConstants;
import com.epam.eshop.entity.Cart;
import com.epam.eshop.entity.User;
import com.epam.eshop.entity.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides utility methods for using in servlets and services.
 *
 * Created by artch on 15.10.2020.
 */
public class Util {

    private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);

    /**
     * get user from session in attribute "currentUser"
     *
     * @param session where contains instance {@link User}
     * @return {@link User} if exists in current session
     */
    public static User getUserFromSession (HttpSession session){
        return (User) session.getAttribute(AttributesNames.CURRENT_USER);
    }


    /**
     * get {@link Cart} from session in attribute "currentUserCart"
     *
     * @param session where contains instance {@link Cart}
     * @return {@link Cart} if exists in current session
     */
    public static Cart getCartFromSession (HttpSession session){
        return (Cart) session.getAttribute(AttributesNames.CURRENT_USER_CART);
    }


    public static void replaceAttributeFromSessionIntoRequest(HttpServletRequest request, String attribute){
        HttpSession session = request.getSession();
        request.setAttribute(attribute, session.getAttribute(attribute));
        session.removeAttribute(attribute);
    }

    public static boolean checkUserRole (User user, UserRole role){
        return user != null && user.getUserRole() == role;
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

    public static String uploadImage(HttpServletRequest request) {
        String path = request.getParameter(ParameterNames.DESTINATION);

        String newImgURL = "";

        try {
            Part filePart = request.getPart(ParameterNames.IMG);
            String fileName = getFileName(filePart);

            if (fileName.isEmpty()){
                return newImgURL;
            }

            Path dir = Paths.get(request.getServletContext().getRealPath("")).resolve(path);
            dir.toFile().mkdirs();
            Path createdImageFilePath = dir.resolve(fileName);
            InputStream fileInputStream = filePart.getInputStream();
            Files.copy(fileInputStream, createdImageFilePath, StandardCopyOption.REPLACE_EXISTING);
            newImgURL = path + "/" + fileName.replace("\\", "/");
        } catch (IOException | ServletException e) {
            LOGGER.error("Can't upload image", e);
        }
        return newImgURL;
    }

    private static String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : partHeader.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }


}
