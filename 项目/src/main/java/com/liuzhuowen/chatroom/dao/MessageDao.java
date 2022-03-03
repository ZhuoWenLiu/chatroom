package com.liuzhuowen.chatroom.dao;

import com.liuzhuowen.chatroom.model.Message;
import com.liuzhuowen.chatroom.util.DBUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {
    private MessageDao() {
        System.out.println("MessageDao()");
    }

    private static final MessageDao instance = new MessageDao();
    public static MessageDao getInstance() {
        return instance;
    }

    public List<Message> selectListAfter(LocalDateTime logoutAt) {
        String sql = "SELECT " +
                "mid, u.uid, nickname, content, published_at" +
                "FROM users u " +
                "JOIN messages m ON u.uid = m.uid " +
                "WHERE published_at >= ? " +
                "ORDER BY published_at";

        List<Message> messageList = new ArrayList<>();
        try (Connection c = DBUtil.connection()) {
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setTimestamp(1, Timestamp.valueOf(logoutAt));

                System.out.println(ps);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Message message = new Message();
                        message.mid = rs.getInt("mid");
                        message.uid = rs.getInt("uid");
                        message.nickname = rs.getString("nickname");
                        message.content = rs.getString("content");
                        message.publishedAt = rs.getTimestamp("published_at").toLocalDateTime();

                        messageList.add(message);
                    }
                }
            }
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }

        return messageList;
    }

    public void insert(Message message) {
        String sql = "INSERT INTO messages (uid, content, published_at) VALUES (?, ?, ?)";

        try (Connection c = DBUtil.connection()) {
            try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, message.uid);
                ps.setString(2, message.content);
                ps.setTimestamp(3, Timestamp.valueOf(message.publishedAt));

                System.out.println(ps);
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    rs.next();
                    message.mid = rs.getInt(1);
                }
            }
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
    }
}
