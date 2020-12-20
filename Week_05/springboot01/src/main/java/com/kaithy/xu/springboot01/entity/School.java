package com.kaithy.xu.springboot01.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kaithy.xu
 * @date 2020-12-20 15:25
 */
@Data
public class School implements ISchool{

    Klass klass;

    Student students;

    List<Klass> klasses;

    public School(List<Klass> klass1, Student students) {
        this.klasses = klass1;
        this.students = students;
    }

    public School() {
        klasses = new ArrayList<>();
    }

    public void addKlass(Klass klass) {
        klasses.add(klass);
    }


    @Override
    public void ding() {
        System.out.println("Class have " + this.klasses.get(0).getStudents().size() + " students and one is " + this.students);
    }

    @Override
    public String toString() {
        return "School{" +
            "klass=" + klass +
            ", students=" + students +
            ", klasses=" + klasses +
            '}';
    }
}
