package com.kaithy.xu.springboot01.service;

import com.kaithy.xu.springboot01.entity.Example.UserExample;
import com.kaithy.xu.springboot01.entity.User;

import java.util.List;

/**
 * @author kaithy.xu
 * @date 2020-12-20 18:08
 */
public interface IUser {

    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(User record, UserExample example);

    int updateByExample(User record,UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
