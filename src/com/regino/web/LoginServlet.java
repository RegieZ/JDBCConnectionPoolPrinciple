package com.regino.web;

import com.regino.domain.User;
import com.regino.service.UserService;
import com.regino.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 1.接收请求参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 2.调用service
        UserService userService = new UserServiceImpl();
        User user = userService.login(username, password);
        // 3.判断
        if (user == null) {
            // 登录失败，友情提示
            request.setAttribute("error", "用户名或密码错误");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            // 登录成功
            request.getSession().setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/list.jsp");
        }
    }

}