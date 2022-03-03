package com.liuzhuowen.chatroom.core;

import com.liuzhuowen.chatroom.model.OnlineUser;

import java.io.IOException;

// I: 代表是接口(Interface)
// OnlineUser: 在线用户
// Registry: 注册处、管理处
public interface IOnlineUserRegistry {
    void online(OnlineUser user) throws IOException;

    void offline(OnlineUser user);
}
