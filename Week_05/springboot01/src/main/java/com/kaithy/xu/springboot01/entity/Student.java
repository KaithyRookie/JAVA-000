package com.kaithy.xu.springboot01.entity;

import java.io.Serializable;

/**
 * @author kaithy.xu
 * @date 2020-12-20 15:21
 */
public class Student implements Serializable {

    private String id;

    private String name;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Student() {
    }

    public void init() {
        System.out.println("Hello ......");
    }

    @Override
    public String toString() {
        return "Student{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            '}';
    }
}
