package com.epam.eshop.controller.filter;

import com.epam.eshop.controller.Util;
import com.epam.eshop.controller.constants.URLConstants;
import com.epam.eshop.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class checks access rights for getting resources. urlPatterns contains URLs where forbidden
 * access for uloggined user
 *
 * Created by artch on 23.09.2020.
 */
@WebFilter(urlPatterns = {"/cart", "/orders", "/orders/*", "/users"})
public class AccessFilter implements Filter {

    public void destroy() {
        // NOOP
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        User user = Util.getUserFromSession(((HttpServletRequest) req).getSession());

        if (user == null) {
            response.sendRedirect(request.getContextPath() + URLConstants.MAIN);
        } else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {
        // NOOP
    }
}
