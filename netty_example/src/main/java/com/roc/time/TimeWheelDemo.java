package com.roc.time;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.util.concurrent.TimeUnit;

/**
 * 时间轮demo
 */
public class TimeWheelDemo {
    public static void main(String[] args) throws InterruptedException {
        // 创建一个时间轮，tickDuration为时间轮每一格的时间间隔，ticksPerWheel为时间轮的格子数
        HashedWheelTimer timer = new HashedWheelTimer(1, TimeUnit.SECONDS, 60);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                System.out.println("task 1");
                timer.newTimeout(this, 5, TimeUnit.SECONDS);
            }
        };
        // 添加一个任务到时间轮，delay为任务执行的延迟时间，unit为时间单位
        timer.newTimeout(timerTask, 5, TimeUnit.SECONDS);
        // 关闭时间轮
//        timer.stop();
        Thread.currentThread().join();
    }
}
