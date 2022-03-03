package com.liuzhuowen.chatroom.center;

import com.liuzhuowen.chatroom.core.IOnlineUserRegistry;
import com.liuzhuowen.chatroom.model.OnlineUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
维护当前的在线用户情况
 */
public class UserCenter implements IOnlineUserRegistry {
    // 把构造方法定义成 private 的，不允许其他代码调用构造方法（其他代码无法实例化类的对象）
    private UserCenter() {
        System.out.println("UserCenter()");
    }

    // 无论谁使用 UserCenter 对象，只能通过 UserCenter.getInstance() 获取
    // getInstance 保证，返回 静态变量 instance，全局唯一
    private static final UserCenter instance = new UserCenter();
    public static UserCenter getInstance() {
        return instance;
    }


    private final List<OnlineUser> onlineUserList = new ArrayList<>();

    @Override
    public void online(OnlineUser user) throws IOException {
        if (onlineUserList.contains(user)) {
            // 该用户已经在线了，我们不允许一个用户多次登录
            // 所以把上次登录的用户踢下线
            int i = onlineUserList.indexOf(user);
            OnlineUser prevUser = onlineUserList.get(i);
            prevUser.kick();
            return;
        }
        onlineUserList.add(user);
        System.out.println("在线用户: " + onlineUserList);
    }

    @Override
    public void offline(OnlineUser user) {
        onlineUserList.remove(user);
        System.out.println("在线用户: " + onlineUserList);
    }

    public List<OnlineUser> getOnlineUserList() {
        // 构建一个新的 ArrayList，而不是直接返回 onlineUserList 引用
        // 目标是防止属性对象逃逸
        // 属性对象逃逸：本来 onlineUserList 指向的对象只由 UserCenter 对象管理
        // 如果这里返回了 onlineUserList 引用，把该引用指向的对象返回了
        // 会使得，可能被其他人直接修改
        return new ArrayList<>(onlineUserList);
    }
}
