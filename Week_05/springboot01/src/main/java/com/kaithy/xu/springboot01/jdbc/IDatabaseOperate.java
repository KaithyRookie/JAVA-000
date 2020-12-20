package com.kaithy.xu.springboot01.jdbc;

import com.kaithy.xu.springboot01.entity.User;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;

/**
 * @author kaithy.xu
 * @date 2020-12-20 17:38
 */
public interface IDatabaseOperate<T> {

    default Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://127.0.0.1:3306/bathroom?serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "Canc*12Yxu@56";
        Enumeration<Driver> divers =  DriverManager.getDrivers();

        Connection conn = DriverManager.getConnection(url, username, password);

        System.out.println("数据库连接就绪");
        System.out.println("当前事务隔离级别："+conn.getTransactionIsolation());

        return conn;
    }

}
