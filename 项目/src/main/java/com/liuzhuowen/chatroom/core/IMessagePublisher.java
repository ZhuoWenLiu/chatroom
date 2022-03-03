package com.liuzhuowen.chatroom.core;

import com.liuzhuowen.chatroom.model.User;

import java.io.IOException;

/*
I: 接口(Interface)
 */
public interface IMessagePublisher {
    void publish(User user, String content) throws IOException;
}
