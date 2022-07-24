package com.Interview.observer;

public class Client {
    public static void main(String[] args) throws InterruptedException {
        Boss boss = new Boss();
        Programmer programmer = new Programmer("小强");
        GrilFriend grilFriend = new GrilFriend(boss,programmer);
        grilFriend.start();
        boss.notifyProgrammer();
        Thread.sleep(1000);
    }
}
