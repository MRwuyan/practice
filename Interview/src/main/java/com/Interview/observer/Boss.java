package com.Interview.observer;

public class Boss {
    //用来定义今晚是否加班
    private boolean isnotifyProgrammer = false;
    public boolean getIsIsnotifyProgrammer() {
        return isnotifyProgrammer;
    }

    //通知所有程序员加班
    public void notifyProgrammer() {
        System.out.println("所有程序员今晚加班");
        this.isnotifyProgrammer = true;
    }

}
