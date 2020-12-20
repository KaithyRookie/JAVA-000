package com.kaithy.xu.springboot01.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer id;

    private String userName;

    private String password;

    private Integer departmentId;

    private Date createTime;

}