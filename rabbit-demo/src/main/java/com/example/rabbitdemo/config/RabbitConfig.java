package com.example.rabbitdemo.config;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String QUEUE_NAME = "PORE";

    @Bean
    Queue poreQueue(){
        /**
         * 1.队列名称
         * 2.是否持久化
         * 3.排他性(队列谁创建的只能由那个connection消费
         * 4.自动删除(如果该队列没有消费者,是否删除)
         */
        return new Queue(QUEUE_NAME, true, false, false);
    }
}
