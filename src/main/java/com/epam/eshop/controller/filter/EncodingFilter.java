package com.epam.eshop.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * This class set StandardCharset UTF8 for all requests.
 *
 * Created by artch on 23.09.2020.
 */
@WebFilter("/*")
public class EncodingFilter implements Filter {

    public void destroy() {
        // NOOP
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        String encoding = StandardCharsets.UTF_8.name();

        req.setCharacterEncoding(encoding);
        resp.setCharacterEncoding(encoding);

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        // NOOP
    }
}
