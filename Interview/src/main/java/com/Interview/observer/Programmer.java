package com.Interview.observer;

public class Programmer {
    public Programmer(String name) {
        this.name = name;
    }

    private String name;
    //省去构造函数和set/get
    //接到加班通知，然后加班
    public void work_overtime() {
        System.out.println(name+"要加班");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
