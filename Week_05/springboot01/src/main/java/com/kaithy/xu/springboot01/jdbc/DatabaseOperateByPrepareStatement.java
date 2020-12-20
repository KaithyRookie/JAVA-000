package com.kaithy.xu.springboot01.jdbc;

import com.kaithy.xu.springboot01.entity.User;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * @author kaithy.xu
 * @date 2020-12-20 17:37
 */
public class DatabaseOperateByPrepareStatement<IUser extends User> implements IDatabaseOperate{


    public void executeByParam(String sql, Map<Integer, Object> params) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            statement = conn.prepareStatement(sql);

            for(Integer index : params.keySet()) {
                if(params.get(index) instanceof Number) {
                    statement.setInt(index, (Integer) params.get(index));
                }else {
                    statement.setString(index, (String) params.getOrDefault(index, ""));
                }
            }

            statement.execute();

            System.out.println("执行 SQL : "+sql+" 操作成功");
            conn.commit();


        }finally {
            if(null != statement)
                statement.close();
            if(null != conn)
                conn.close();
        }


    }


    public List<User> executeQueryWithParams(String sql, Map<Integer, Object> params) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<User> userList = new ArrayList<>();
        try {
            conn = getConnection();
            statement = conn.prepareStatement(sql);

            for(Integer index : params.keySet()) {
                if(params.get(index) instanceof Number) {
                    statement.setInt(index, (Integer) params.get(index));
                }else {
                    statement.setString(index, (String) params.getOrDefault(index, ""));
                }
            }

            resultSet = statement.executeQuery();
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

        }finally {
            if(null != resultSet) {
                resultSet.close();
            }
            if(null != statement)
                statement.close();
            if(null != conn)
                conn.close();
        }
        return userList;
    }
}
