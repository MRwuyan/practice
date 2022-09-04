package com.example.rabbitdemo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * topic:交换机会将到达交换机的routing_key相匹配的队列中去
 */
@Configuration
public class TopicConfig {
    public static final String TOPIC_QUEUE_NAME = "topic_queue_name";
    public static final String TOPIC_QUEUE_NAME1 = "topic_queue_name1";
    public static final String TOPIC_QUEUE_NAME2 = "topic_queue_name2";
    public static final String TOPIC_EXCHANGE_NAME = "topic_exchange_name";

    @Bean
    Queue topicQueue1() {
        return new Queue(TOPIC_QUEUE_NAME1, true, false, false);
    }

    @Bean
    Queue topicQueue2() {
        return new Queue(TOPIC_QUEUE_NAME2, true, false, false);
    }
    @Bean
    Queue topicQueue3() {
        return new Queue(TOPIC_QUEUE_NAME, true, false, false);
    }
    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME, true, false);
    }

    /**
     * 将交换机和队列绑定
     */
    @Bean
    Binding topicBinding1() {
        return BindingBuilder
                .bind(topicQueue1())//队列
                .to(topicExchange())//交换机
                .with("queue1.#");//routing_key名称,#是一个通配符,表示将来消息的routing_key只要是以queue1开头,都将被路由到到queue1的队列
    }

    /**
     * 将交换机和队列绑定
     */
    @Bean
    Binding topicBinding2() {
        return BindingBuilder
                .bind(topicQueue2())//队列
                .to(topicExchange())//交换机
                .with("queue2.#");//routing_key名称,#是一个通配符,表示将来消息的routing_key只要是以queue2开头,都将被路由到到queue2的队列
    }

    @Bean
    Binding topicBinding3() {
        return BindingBuilder
                .bind(topicQueue3())//队列
                .to(topicExchange())//交换机
                .with("#.queue.#");//routing_key名称,#是一个通配符,表示将来消息的routing_key只要包含queue,都将被路由到到queue的队列
    }
}
