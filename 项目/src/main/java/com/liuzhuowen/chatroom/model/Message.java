package com.liuzhuowen.chatroom.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Message {
    public Integer mid;
    public Integer uid;
    public String nickname;
    public String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime publishedAt;
}
