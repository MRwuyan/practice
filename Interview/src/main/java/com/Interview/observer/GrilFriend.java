package com.Interview.observer;

public class GrilFriend  extends Thread{
    private Boss boss;
    private Programmer programmer;
    public GrilFriend(Boss _boss,Programmer _programmer){
        this.boss = _boss;
        this.programmer = _programmer;
    }

    @Override
    public void run(){
        while (true){
            if(this.boss.getIsIsnotifyProgrammer()){
                this.programmer.work_overtime();
            }
        }
    }
}
