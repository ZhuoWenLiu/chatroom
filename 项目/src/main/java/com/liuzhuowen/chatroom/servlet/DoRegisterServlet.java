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

@WebServlet("/do-register")
public class DoRegisterServlet extends HttpServlet {
    public DoRegisterServlet() {
        System.out.println("DoRegisterServlet()");
    }

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String username = req.getParameter("username");
        String nickname = req.getParameter("nickname");
        String password = req.getParameter("password");

        User user = userService.register(username, nickname, password);
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/plain");
        PrintWriter writer = resp.getWriter();
        req.getSession().setAttribute("currentUser", user);
        writer.println("注册并登录成功");
    }
}
