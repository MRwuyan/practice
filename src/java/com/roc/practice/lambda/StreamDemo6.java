package com.roc.practice.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 收集器
 */
public class StreamDemo6 {
    public static void main(String[] args) {
        List<Student> list = new ArrayList<>();
        list.add(new Student(1, 1));
        list.add(new Student(2, 2));
        List<Integer> collect = list.stream().map(Student::getAge).collect(Collectors.toList());
        //统计
        Integer collect1 = list.stream().collect(Collectors.summingInt(Student::getAge));
        System.out.println(collect1);
        //分块
        Map<Boolean, List<Student>> collect2 = list.stream().collect(Collectors.partitioningBy(a -> a.getAge() > 1));
        System.out.println(collect2);
        //分组
        Map<Integer, List<Student>> collect3 = list.stream().collect(Collectors.groupingBy(Student::getAge));
        System.out.println(collect3.toString());
        //分组统计
        Map<Integer, Long> collect4 = list.stream().collect(Collectors.groupingBy(Student::getAge, Collectors.counting()));
        System.out.println(collect4);

    }

}

class Student{
    private Integer id;
    private Integer age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Student(Integer id, Integer age) {
        this.id = id;
        this.age = age;
    }
}
