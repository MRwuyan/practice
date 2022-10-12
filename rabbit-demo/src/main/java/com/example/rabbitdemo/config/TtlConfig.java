package com.example.rabbitdemo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TtlConfig {
    private final static String TTL_EXCHANGE_NAME = "ttl_exchange_name";
    private final static String TTL_QUEUE_NAME = "ttl_exchange_queue";

    @Bean
    Queue ttlQueue(){
        Map<String, Object> map = new HashMap<>();
        //给消息队列设置过期时间,改队列中的消息,10s之内没消费,则过期
        map.put("x-message-ttl", 10000);
        return new Queue(TTL_QUEUE_NAME,true,false,false,map);
    }
}
