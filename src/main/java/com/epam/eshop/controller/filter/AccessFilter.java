package com.epam.eshop.controller.filter;

import com.epam.eshop.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
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

        User user = (User) request.getSession(true).getAttribute("currentUser");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/main");
        } else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {
        // NOOP
    }
}
