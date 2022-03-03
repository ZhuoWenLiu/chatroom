package com.liuzhuowen.chatroom.dao;

import com.liuzhuowen.chatroom.model.User;
import com.liuzhuowen.chatroom.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private UserDao() {
        System.out.println("UserDao()");
    }

    private static final UserDao instance = new UserDao();
    public static UserDao getInstance() {
        return instance;
    }

    public void updateLogoutAt(User user) {
        String sql = "UPDATE users SET logout_at = ? WHERE uid = ?";
        try (Connection c = DBUtil.connection()) {
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setTimestamp(1, Timestamp.valueOf(user.logoutAt));
                ps.setInt(2, user.uid);

                System.out.println(ps);
                ps.executeUpdate();
            }
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
    }

    public void insert(User user) {
        // 不用插入 logout_at，因为 这个字段在建表时设置了默认值(null)
        String sql = "INSERT INTO users (username, nickname, password) VALUES (?, ?, ?)";
        try (Connection c = DBUtil.connection()) {
            try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, user.username);
                ps.setString(2, user.nickname);
                ps.setString(3, user.password);

                System.out.println(ps);
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    rs.next();

                    user.uid = rs.getInt(1);
                }
            }
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
    }

    public User selectOneByUsername(String username) {
        String sql = "SELECT uid, username, nickname, password, logout_at FROM users WHERE username = ?";

        try (Connection c = DBUtil.connection()) {
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1, username);

                System.out.println(ps);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return null;
                    }

                    User user = new User();
                    user.uid = rs.getInt("uid");
                    user.username = rs.getString("username");
                    user.nickname = rs.getString("nickname");
                    user.password = rs.getString("password");
                    Timestamp logoutAtTS = rs.getTimestamp("logout_at");
                    if (logoutAtTS != null) {
                        user.logoutAt = logoutAtTS.toLocalDateTime();
                    } else {
                        user.logoutAt = null;
                    }

                    return user;
                }
            }
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
    }

    public List<User> selectListAll() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT uid, nickname FROM users ORDER BY uid";
        try (Connection c = DBUtil.connection()) {
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                System.out.println(ps);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        User user = new User();
                        user.uid = rs.getInt("uid");
                        user.nickname = rs.getString("nickname");

                        userList.add(user);
                    }
                }
            }
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }

        return userList;
    }
}
