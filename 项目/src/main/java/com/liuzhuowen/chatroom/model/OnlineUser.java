package com.liuzhuowen.chatroom.model;

import javax.websocket.CloseReason;
import javax.websocket.Session;

import java.io.IOException;
import java.util.Objects;

import static javax.websocket.CloseReason.CloseCodes.NORMAL_CLOSURE;

public class OnlineUser {
    private final User user;
    private final Session session;

    public OnlineUser(User user, Session session) {
        System.out.println("OnlineUser()");
        this.user = user;
        this.session = session;
    }

    public User getDo() {
        return user;
    }

    public void kick() throws IOException {
        CloseReason reason = new CloseReason(NORMAL_CLOSURE, "账号在别处登录");
        session.close(reason);
    }

    public boolean isNewUser() {
        return user.logoutAt == null;
    }

    public void send(String messageText) throws IOException {
        session.getBasicRemote().sendText(messageText);
    }

    @Override
    public String toString() {
        return "OnlineUser{" +
                "user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OnlineUser that = (OnlineUser) o;
        return user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }
}
