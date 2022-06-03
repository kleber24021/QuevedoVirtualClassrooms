package com.quevedo.virtualclassroomsserver.logic.ee.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "FilterCorsPoliciesAll", urlPatterns = "/*")
public class FilterCorsPoliciesAll implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        ((HttpServletResponse)response).addHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse)response).addHeader("Access-Control-Allow-Credentials", "true");
        ((HttpServletResponse)response).addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        ((HttpServletResponse)response).addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        ((HttpServletResponse)response).addHeader("Access-Control-Expose-Headers", "Authorization");
        chain.doFilter(request, response);
    }
}
