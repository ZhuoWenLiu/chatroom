package com.liuzhuowen.chatroom.model;

import java.util.ArrayList;
import java.util.List;

public class UserListResult {
    public Integer onlineCount;
    public Integer totalCount;
    public List<UserListUser> userList = new ArrayList<>();
}
