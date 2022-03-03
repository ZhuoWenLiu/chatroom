package com.liuzhuowen.chatroom.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.liuzhuowen.chatroom.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/get-current-user.json")
public class GetCurrentUserApi extends HttpServlet {
    private final ObjectMapper om = new ObjectMapper();

    public GetCurrentUserApi() {
        System.out.println("GetCurrentUserApi()");
        om.registerModule(new JavaTimeModule());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = null;
        HttpSession session = req.getSession(false);
        if (session != null) {
            user = (User) session.getAttribute("currentUser");
        }

        Map<String, Object> o = new HashMap<>();
        o.put("currentUser", user);

        String s = om.writeValueAsString(o);

        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        resp.getWriter().println(s);
    }
}
