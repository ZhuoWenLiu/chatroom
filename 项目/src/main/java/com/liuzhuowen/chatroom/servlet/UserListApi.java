package com.liuzhuowen.chatroom.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.liuzhuowen.chatroom.model.UserListResult;
import com.liuzhuowen.chatroom.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/user-list.json")
public class UserListApi extends HttpServlet {
    private final UserService userService = UserService.getInstance();
    private final ObjectMapper om = new ObjectMapper();

    public UserListApi() {
        System.out.println("UserListApi()");
        om.registerModule(new JavaTimeModule());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO: 验证用户登录后才能使用该接口

        UserListResult result = userService.getUserList();
        String s = om.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        System.out.println(s);

        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        resp.getWriter().println(s);
    }
}
