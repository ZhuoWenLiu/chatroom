package com.liuzhuowen.chatroom.model;

public class UserListUser implements Comparable<UserListUser> {
    public Integer uid;
    public String nickname;
    public boolean online;

    @Override
    public int compareTo(UserListUser o) {
        // 比较 this 和 o 的大小
        if (this.online != o.online) {
            if (this.online) {
                return -1;
            } else {
                return 1;
            }
        }


        return this.uid - o.uid;
    }
}
