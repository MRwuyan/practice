package com.roc.netty;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;


@Slf4j
public class TestEventLoop {
    public static void main(String[] args) {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup(2);
        eventExecutors.next().submit(() -> {
            log.info("ok");
        });
        eventExecutors.next().scheduleWithFixedDelay(() -> {
            log.info("这是定时任务");
                }, 1, 2, TimeUnit.SECONDS);

        log.info("这是主线程");
    }
}
