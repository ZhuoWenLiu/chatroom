package com.liuzhuowen.chatroom.service;

import com.liuzhuowen.chatroom.center.UserCenter;
import com.liuzhuowen.chatroom.core.IMessagePublisher;
import com.liuzhuowen.chatroom.dao.MessageDao;
import com.liuzhuowen.chatroom.encoder.MessageEncoder;
import com.liuzhuowen.chatroom.model.Message;
import com.liuzhuowen.chatroom.model.OnlineUser;
import com.liuzhuowen.chatroom.model.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class MessageService implements IMessagePublisher {
    private final MessageDao messageDao = MessageDao.getInstance();
    private final UserCenter userCenter = UserCenter.getInstance();
    private final MessageEncoder messageEncoder = MessageEncoder.getInstance();

    private MessageService() {
        System.out.println("MessageService()");
    }

    private static final MessageService instance = new MessageService();
    public static MessageService getInstance() {
        return instance;
    }

    @Override
    public void publish(User user, String content) throws IOException {

        Message message = new Message();
        message.uid = user.uid;
        message.nickname = user.nickname;
        message.content = content;
        message.publishedAt = LocalDateTime.now();


        messageDao.insert(message);

        String messageText = messageEncoder.encode(message);
        List<OnlineUser> onlineUserList = userCenter.getOnlineUserList();
        for (OnlineUser onlineUser : onlineUserList) {
            onlineUser.send(messageText);
        }
    }
}
