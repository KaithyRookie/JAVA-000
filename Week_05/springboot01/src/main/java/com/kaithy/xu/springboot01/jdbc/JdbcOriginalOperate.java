package com.kaithy.xu.springboot01.jdbc;

import com.kaithy.xu.springboot01.entity.User;
import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * @author kaithy.xu
 * @date 2020-12-20 16:55
 */
public class JdbcOriginalOperate<IUser extends User> implements IDatabaseOperate{


    public void execute(String sql) throws SQLException, ClassNotFoundException {

        Connection conn = getConnection();

        try {

            conn.setAutoCommit(false);

            Statement executeStatement = conn.createStatement();

            executeStatement.execute(sql);

            System.out.println("执行 SQL : "+sql+" 操作成功");

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if(null != conn) {
                System.out.println("执行 SQL : "+sql+" 操作失败,回滚事务操作");
                conn.rollback();
            }
        }finally {
            if(null != conn) {
                try {
                    conn.close();
                    System.out.println("SQL 执行完毕， 已关闭数据库连接");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void checkQuerySql(String sql) {
        if(null == sql || "".equals(sql) || !sql.startsWith("SELECT")) {
            if(null == sql || "".equals(sql)) {
                throw new NullPointerException("SQL 语句为空");
            }
            throw new IllegalArgumentException("非法的SQL: " + sql);
        }
    }

    public List<User> executeQuery(String sql) throws SQLException, ClassNotFoundException {
        checkQuerySql(sql);

        Connection conn = getConnection();
        List<User> userList = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            Statement queryStatement = conn.createStatement();
            resultSet = queryStatement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                int id = resultSet.getInt("id");
                String userName = resultSet.getString("user_name");
                int departmentId = resultSet.getInt("department_id");
                Date createTime = resultSet.getDate("create_time");
                user.setId(Optional.of(id).orElse(-1));
                user.setUserName(Optional.of(userName).orElse(""));
                user.setCreateTime(createTime);
                user.setDepartmentId(Optional.of(departmentId).orElse(-1));
                userList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(null != resultSet) {
                resultSet.close();
            }
            if(null != conn) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return userList;
    }

}
