package com.liuzhuowen.chatroom.servlet;

import com.liuzhuowen.chatroom.model.User;
import com.liuzhuowen.chatroom.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/do-login")
public class DoLoginServlet extends HttpServlet {
    public DoLoginServlet() {
        System.out.println("DoLoginServlet()");
    }

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = userService.login(username, password);
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/plain");
        PrintWriter writer = resp.getWriter();
        if (user != null) {
            req.getSession().setAttribute("currentUser", user);
            resp.sendRedirect("/");

        } else {
            writer.println("登录失败");
        }
    }
}
