package com.Interview;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class LMDemo {
    public static void main(String[] args) {
        String string1 = "abc---abc---abc";
        String string2 = "---";
        System.out.println(splitString(string1, string2));
        Integer[] arr= new Integer[]{1,1,1,1,2,3,4,4,5,5,5,6};
        Set<Integer> integers = duplicateRemoval(arr);
        System.out.println(integers);
    }

    /**
     * 1. Java: 代码、算法
     * 请仅使用String的indexOf和substring方法实现splitString函数。
     * 该函数接受两个参数string1和string2，返回值为使用string2分割string1的字符串列表。
     *
     * @return
     */
    private static String splitString(String s1, String s2) {
        if (!"".equals(s1) && !"".equals(s2)) {
            String[] split = s1.split(s2);
            return Arrays.toString(split);
        }
        return "";
    }
    /**
     * 2. Java: 代码、算法
     * 请编写一个函数，这个函数接受一个已升序排序的数组作为参数。该函数删除输入数组中的重复值。
     * 例如，给定一个数组[1,1,1,1,2,3,4,4,5,5,5,6]，经过该函数处理后，数组变为[1,2,3,4,5,6]。
     */
    private static Set<Integer> duplicateRemoval(Integer[] arr){
        if (arr!=null&&arr.length!=0){
            return Arrays.stream(arr).collect(Collectors.toSet());
        }
        return null;
    }
    /**
     * 3. Java: SQL关系数据库
     * 请在一个支持标准sql的数据库系统中，建立一个“学生考试答题”数据表，这个表包含三个字段：
     * 学生id，
     * 考题id，以及
     * 学生答案是否正确（每个学生每个题只可能有 正确 错误 两种可能）。
     *
     * a) 请写出创建该数据表的sql语句。在创建数据表时，请考虑每个字段采用合适的数据类型，并创建合理的索引。
     * b) 请写一个select查询语句，该语句输出一个字段，包含的结果为所有 回答错误的考题id。
     */
    /**
     a:
         create table answer
         (
             student_id   bigint not null comment '学生id',
             questions_id bigint not null,
             is_correct   bit    not null comment '是否正确',
             constraint answer_pk
             unique (student_id, questions_id)
         );
     b:
         select questions_id from table_name
         where is_correct = 0;
     */

}
