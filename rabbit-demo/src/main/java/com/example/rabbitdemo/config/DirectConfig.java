package com.example.rabbitdemo.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectConfig {

    public static final String QUEUE_NAME = "PORE";
    public static final String DIRECT_QUEUE_NAME = "direct_queue_name";
    public static final String DIRECT_EXCHANGE_NAME = "direct_exchange_name";


    @Bean
    Queue poreQueue() {
        /**
         * 1.队列名称
         * 2.是否持久化
         * 3.排他性(队列谁创建的只能由那个connection消费
         * 4.自动删除(如果该队列没有消费者,是否删除)
         */
        return new Queue(QUEUE_NAME, true, false, false);
    }

    @Bean
    Queue directQueue() {
        /**
         * 1.队列名称
         * 2.是否持久化
         * 3.排他性(队列谁创建的只能由那个connection消费
         * 4.自动删除(如果该队列没有消费者,是否删除)
         */
        return new Queue(DIRECT_QUEUE_NAME, true, false, false);
    }

    /**
     * Direct:直连交换机,这种路由策略,将消息队列绑定到DirectExchange上,当消息到达交换机的时候,消息会携带一个routing_key,然后交换机会找到名为
     * routing_key的队列,将消息路由过去
     */
    @Bean
    DirectExchange directExchange() {
        //1.交换机的名称
        //2.交换机是否持久化
        //3.如果没有队列与之绑定,是否自动删除交换机
        return new DirectExchange(DIRECT_EXCHANGE_NAME, true, false);
    }

    /**
     * 将交换机和队列绑定
     */
    @Bean
    Binding directBinding1() {
        return BindingBuilder
                .bind(poreQueue())//队列
                .to(directExchange())//交换机
                .with(QUEUE_NAME);//routing_key名称,此处用了队列名称
    }
    @Bean
    Binding directBinding2() {
        return BindingBuilder
                .bind(directQueue())//队列
                .to(directExchange())//交换机
                .with(DIRECT_QUEUE_NAME);//routing_key名称,此处用了队列名称
    }
}
