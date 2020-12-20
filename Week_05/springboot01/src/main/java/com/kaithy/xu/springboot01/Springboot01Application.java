package com.kaithy.xu.springboot01;

import com.kaithy.xu.springboot01.entity.Example.UserExample;
import com.kaithy.xu.springboot01.entity.School;
import com.kaithy.xu.springboot01.entity.User;
import com.kaithy.xu.springboot01.jdbc.DatabaseOperateByHikari;
import com.kaithy.xu.springboot01.jdbc.DatabaseOperateByPrepareStatement;
import com.kaithy.xu.springboot01.jdbc.JdbcOriginalOperate;
import com.kaithy.xu.springboot01.service.IUser;
import com.zaxxer.hikari.pool.HikariPool;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

@SpringBootApplication(scanBasePackages = "com.kaithy.xu.springboot01")
@MapperScan({"com.kaithy.xu.springboot01.dao"})
public class Springboot01Application {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ApplicationContext context = SpringApplication.run(Springboot01Application.class, args);

        System.out.println("Start Success! Hello World!");

        School school = (School) context.getBean("school1");

        school.ding();
        System.out.println(school);

        operateByHakariManual(context);

    }

    public static void operateByHakariManual(ApplicationContext context) throws SQLException, ClassNotFoundException {

        DatabaseOperateByHikari operate = context.getBean(DatabaseOperateByHikari.class);

        String insertSql = "INSERT INTO bathroom.t_user (user_name, password, department_id) values(?, ?, ?);";
        Map<Integer, Object> params = new HashMap<>(4);
        params.put(1, "Lucifier");
        params.put(2, "6024db274e8708afc4ed94f280bef16f");
        params.put(3, 4);


        operate.executeByParam(insertSql, params);
        String querySql = "SELECT * FROM bathroom.t_user WHERE user_name = ? ;";
        Map<Integer, Object> queryMap = new HashMap<>(3);
        queryMap.put(1, "Lucifier");

        List<User> userList = operate.executeQueryWithParams(querySql, queryMap);
        if(!CollectionUtils.isEmpty(userList)) {
            System.out.println("Lucifier : "+ Arrays.toString(userList.toArray()));
        }
    }

    public static void operateByHikari(ApplicationContext context) {
        IUser userService = (IUser) context.getBean("userService");


        User record = new User();
        record.setUserName("Cindy");
        record.setDepartmentId(3);
        record.setPassword("6024db274e8708afc4ed94f280bef16f");

        userService.insertSelective(record);


        UserExample example = new UserExample();
        example.or().andUserNameEqualTo("Cindy");
        List<User> userList = userService.selectByExample(example);
        if(!CollectionUtils.isEmpty(userList)) {
            System.out.println("Cindy : "+ Arrays.toString(userList.toArray()));
        }

    }

    public static void operateByPrepare() throws SQLException, ClassNotFoundException {
        String insertSql = "INSERT INTO bathroom.t_user (user_name, password, department_id) values(?, ?, ?);";
        Map<Integer, Object> params = new HashMap<>(4);
        params.put(1, "Lily");
        params.put(2, "6024db274e8708afc4ed94f280bef16f");
        params.put(3, 3);

        DatabaseOperateByPrepareStatement operate = new DatabaseOperateByPrepareStatement();

        operate.executeByParam(insertSql, params);
        String querySql = "SELECT * FROM bathroom.t_user WHERE user_name = ? ;";
        Map<Integer, Object> queryMap = new HashMap<>(3);
        queryMap.put(1, "Lily");

        List<User> userList = operate.executeQueryWithParams(querySql, queryMap);
        if(!CollectionUtils.isEmpty(userList)) {
            System.out.println("Lily : "+ Arrays.toString(userList.toArray()));
        }
    }

    public static void operateOriginalJdbc() throws SQLException, ClassNotFoundException {
        String querySql = "SELECT * FROM bathroom.t_user LIMIT 0,2;";

        JdbcOriginalOperate operate = new JdbcOriginalOperate();

        List<User> userList = operate.executeQuery(querySql);

        if(!CollectionUtils.isEmpty(userList)) {
            System.out.println("userList: "+ Arrays.toString(userList.toArray()));
        }

        String insertSql = "INSERT INTO bathroom.t_user (user_name, password, department_id) values('Linda', '5ffccdbc7ed101354553fb509df67758', 2);";
        operate.execute(insertSql);
        querySql = "SELECT * FROM bathroom.t_user WHERE user_name = 'Linda';";
        userList = operate.executeQuery(querySql);
        if(!CollectionUtils.isEmpty(userList)) {
            System.out.println("Linda : "+ Arrays.toString(userList.toArray()));
        }
    }

}
