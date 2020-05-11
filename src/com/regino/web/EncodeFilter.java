package com.regino.web;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/EncodeFilter")
public class EncodeFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws ServletException, IOException {
        // 向下转型
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 如果是post请求，统一request编码
        if (request.getMethod().equalsIgnoreCase("post")) {
            request.setCharacterEncoding("utf-8");
        }
        // 不管是post还是get请求，都要统一response编码
        response.setContentType("text/html;charset=utf-8");
        // 放行
        chain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }
}
