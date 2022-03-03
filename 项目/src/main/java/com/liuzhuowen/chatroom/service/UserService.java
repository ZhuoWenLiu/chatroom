package com.liuzhuowen.chatroom.service;

import com.liuzhuowen.chatroom.center.UserCenter;
import com.liuzhuowen.chatroom.core.IOnlineUserRegistry;
import com.liuzhuowen.chatroom.dao.MessageDao;
import com.liuzhuowen.chatroom.dao.UserDao;
import com.liuzhuowen.chatroom.encoder.MessageEncoder;
import com.liuzhuowen.chatroom.model.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class UserService implements IOnlineUserRegistry {
    // 暂时没有好的办法来处理单例问题，等学了 Spring 之后，处理单例就容易了
    private final UserCenter userCenter = UserCenter.getInstance();
    private final UserDao userDao = UserDao.getInstance();
    private final MessageDao messageDao = MessageDao.getInstance();
    private final MessageEncoder messageEncoder = MessageEncoder.getInstance();

    private static final UserService instance = new UserService();
    public static UserService getInstance() {
        return instance;
    }

    private UserService() {
        System.out.println("UserService()");
    }

    @Override
    public void online(OnlineUser user) throws IOException {

        if (!user.isNewUser()) {

            User userDo = user.getDo();
            List<Message> messageList = messageDao.selectListAfter(userDo.logoutAt);

            for (Message message : messageList) {
                String messageText = messageEncoder.encode(message);
                user.send(messageText);
            }
        }

        userCenter.online(user);
    }

    @Override
    public void offline(OnlineUser user) {

        User userDo = user.getDo();
        userDo.logoutAt = LocalDateTime.now();
        userDao.updateLogoutAt(userDo);

        userCenter.offline(user);
    }


    public User register(String username, String nickname, String password) {
        User user = new User();
        user.username = username;
        user.nickname = nickname;
        user.password = password;
        user.logoutAt = null;

        userDao.insert(user);

        return user;
    }

    public User login(String username, String password) {
        User user = userDao.selectOneByUsername(username);
        if (user == null) {
            return null;
        }

        if (user.password.equals(password)) {
            return user;
        }

        return null;
    }

    public UserListResult getUserList() {
        UserListResult result = new UserListResult();
        List<User> allUserList = userDao.selectListAll();
        List<OnlineUser> onlineUserList = userCenter.getOnlineUserList();
        Set<User> onlineUserSet = new HashSet<>();
        for (OnlineUser onlineUser : onlineUserList) {
            onlineUserSet.add(onlineUser.getDo());
        }

        result.onlineCount = onlineUserSet.size();
        result.totalCount = allUserList.size();
        for (User user : allUserList) {
            UserListUser ulu = new UserListUser();
            ulu.uid = user.uid;
            ulu.nickname = user.nickname;

            if (onlineUserSet.contains(user)) {
                ulu.online = true;
            } else {
                ulu.online = false;
            }

            result.userList.add(ulu);
        }


        Collections.sort(result.userList);

        return result;
    }
}
