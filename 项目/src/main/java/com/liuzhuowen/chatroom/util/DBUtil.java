package com.liuzhuowen.chatroom.util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {
    private static final DataSource dataSource;

    static {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        // 由于是本机 && 是标准端口(3306)
        // 所以，可以省略 ip + port
        mysqlDataSource.setUrl("jdbc:mysql:///db_1_18?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai");
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("123456");

        dataSource = mysqlDataSource;
    }

    public static Connection connection() throws SQLException {
        return dataSource.getConnection();
    }
}
