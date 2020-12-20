package com.kaithy.xu.springboot01.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author kaithy.xu
 * @date 2020-12-20 15:21
 */
@Data
public class Klass {

    private String id;

    private String name;

    List<Student> students;

    public Klass(String id, String name) {
        this.id = id;
        this.name = name;
        students = new ArrayList<>();
    }


    public void addStudents(Student student) {
        students.add(student);
    }

    public void dong() {
        System.out.println(Arrays.toString(this.getStudents().toArray()));
    }
}
