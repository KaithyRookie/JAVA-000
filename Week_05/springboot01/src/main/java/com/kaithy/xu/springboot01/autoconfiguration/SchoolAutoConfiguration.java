package com.kaithy.xu.springboot01.autoconfiguration;

import com.kaithy.xu.springboot01.autoconfiguration.prop.SchoolPropertiesConfiguration;
import com.kaithy.xu.springboot01.entity.Klass;
import com.kaithy.xu.springboot01.entity.School;
import com.kaithy.xu.springboot01.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author kaithy.xu
 * @date 2020-12-20 15:34
 */
@Configuration
@ConditionalOnClass({School.class})
@ConditionalOnProperty(prefix = "spring.school", name="enables", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(SchoolPropertiesConfiguration.class)
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class SchoolAutoConfiguration {

    @Autowired
    private final SchoolPropertiesConfiguration props;

    @Bean(name = "school1")
    public School createSchool() {
        List<Klass> klasses = createKlass();
        School school = new School(klasses, klasses.get(0).getStudents().get(0));
        return school;
    }


    public List<Klass> createKlass() {
        List<String> klassIds = Arrays.asList(props.getKlassIds().split(","));
        List<String> klassNames = Arrays.asList(props.getKlassNames().split(","));
        int size = Math.min(klassIds.size(), klassNames.size());
        List<Klass> klasses = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            klasses.add(new Klass(klassIds.get(i), klassNames.get(i)));
        }

        List<Student> students = createStudents();

        for (int i = 0; i < students.size(); i++) {
            int klassIndex = i % size;
            klasses.get(klassIndex).addStudents(students.get(i));
        }
        return klasses;
    }

    public List<Student> createStudents() {
        List<String> studentIds = Arrays.asList(props.getStudentIds().split(","));
        List<String> studentNames = Arrays.asList(props.getStudentNames().split(","));

        int size = Math.min(studentIds.size(), studentNames.size());
        List<Student> students = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            students.add(new Student(studentIds.get(i), studentNames.get(i)));
        }
        return students;
    }

}
