package com.kaithy.xu.springboot01.jdbc;

import com.kaithy.xu.springboot01.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author kaithy.xu
 * @date 2020-12-20 18:19
 */
@Component
public class DatabaseOperateByHikari extends DatabaseOperateByPrepareStatement{

    @Autowired
    private DataSource dataSource;

    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        System.out.println("datasource: "+ dataSource);
        Connection conn = dataSource.getConnection();
        return conn;
    }

    @Override
    public void executeByParam(String sql, Map params) throws SQLException, ClassNotFoundException {
        super.executeByParam(sql, params);
    }

    @Override
    public List<User> executeQueryWithParams(String sql, Map params) throws SQLException, ClassNotFoundException {
        return super.executeQueryWithParams(sql, params);
    }
}
