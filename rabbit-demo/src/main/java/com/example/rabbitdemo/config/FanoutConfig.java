package com.example.rabbitdemo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * fanout:交换机会将到达交换机的所有的消息路由到与他绑定的所有对面上面来
 */
@Configuration
public class FanoutConfig {
    public static final String FANOUT_QUEUE_NAME1 = "fanout_queue_name1";
    public static final String FANOUT_QUEUE_NAME2 = "fanout_queue_name2";
    public static final String FANOUT_EXCHANGE_NAME = "fanout_exchange_name";

    @Bean
    Queue queue1() {

        return new Queue(FANOUT_QUEUE_NAME1, true, false, false);
    }

    @Bean
    Queue queue2() {

        return new Queue(FANOUT_QUEUE_NAME2, true, false, false);
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE_NAME, true, false);
    }

    /**
     * 将交换机和队列绑定
     */
    @Bean
    Binding fanoutBinding1() {
        return BindingBuilder
                .bind(queue1())//队列
                .to(fanoutExchange());//交换机
    }

    /**
     * 将交换机和队列绑定
     */
    @Bean
    Binding fanoutBinding2() {
        return BindingBuilder
                .bind(queue2())//队列
                .to(fanoutExchange());//交换机
    }
}
