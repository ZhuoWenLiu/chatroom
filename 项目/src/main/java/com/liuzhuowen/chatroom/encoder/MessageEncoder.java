package com.liuzhuowen.chatroom.encoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.liuzhuowen.chatroom.model.Message;

import java.io.IOException;

public class MessageEncoder {
    private static final MessageEncoder instance = new MessageEncoder();
    public static MessageEncoder getInstance() {
        return instance;
    }

    private final ObjectMapper om = new ObjectMapper();

    private MessageEncoder() {
        System.out.println("MessageEncoder()");
        om.registerModule(new JavaTimeModule());
    }

    public String encode(Message message) throws IOException {
        return om.writeValueAsString(message);
    }
}
