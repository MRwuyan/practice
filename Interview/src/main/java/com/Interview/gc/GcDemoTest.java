package com.Interview.gc;

import java.util.ArrayList;
import java.util.List;

public class GcDemoTest {

    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        while (true) {
            System.out.println("add object");
            list.add(Object.class);
        }
    }
}
