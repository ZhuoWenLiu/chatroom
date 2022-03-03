package com.liuzhuowen.chatroom.endpoint;

import com.liuzhuowen.chatroom.model.OnlineUser;
import com.liuzhuowen.chatroom.model.User;
import com.liuzhuowen.chatroom.service.MessageService;
import com.liuzhuowen.chatroom.service.UserService;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;

import static javax.websocket.CloseReason.CloseCodes.NORMAL_CLOSURE;

@ServerEndpoint(value = "/message", configurator = HttpSessionConfigurator.class)
public class MessageEndpoint {
    private final UserService userService = UserService.getInstance();
    private final MessageService messageService = MessageService.getInstance();
    private OnlineUser currentUser;

    public MessageEndpoint() {
        System.out.println("MessageEndpoint()");
    }

    @OnOpen
    public void onOpen(Session session) throws IOException {
        User user = getCurrentUser(session);
        if (user == null) {
            CloseReason reason = new CloseReason(NORMAL_CLOSURE, "必须先登录");
            session.close(reason);
            return;
        }

        currentUser = new OnlineUser(user, session);
        userService.online(currentUser);
    }

    @OnClose
    public void onClose() {
        userService.offline(currentUser);
    }

    @OnMessage
    public void onMessage(String content) throws IOException {
        messageService.publish(currentUser.getDo(), content);
    }

    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace(System.out);
    }

    private User getCurrentUser(Session session) {
        Map<String, Object> userProperties = session.getUserProperties();
        HttpSession httpSession = (HttpSession) userProperties.get("httpSession");
        if (httpSession == null) {
            return null;
        }

        return (User) httpSession.getAttribute("currentUser");
    }
}
