package com.example.rabbitdemo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * topic:交换机会将到达交换机的routing_key相匹配的队列中去
 */
@Configuration
public class RpcConfig {
    public static final String RPC_MSG_QUEUE = "rpc_msg_queue";
    public static final String RPC_REPLY_MSG_QUEUE = "rpc_reply_msg_queue";
    public static final String RPC_EXCHANGE = "rpc_exchange";

    @Bean
    Queue rpcQueue() {
        return new Queue(RPC_MSG_QUEUE, true, false, false);
    }

    @Bean
    Queue rpcReplyQueue() {
        return new Queue(RPC_REPLY_MSG_QUEUE, true, false, false);
    }

    @Bean
    TopicExchange rpcExchange() {
        return new TopicExchange(RPC_EXCHANGE);
    }

    /**
     * 将交换机和队列绑定
     */
    @Bean
    Binding rpcBinding() {
        return BindingBuilder
                .bind(rpcQueue())//队列
                .to(rpcExchange())//交换机
                .with(RPC_MSG_QUEUE);//routing_key名称,#是一个通配符,表示将来消息的routing_key只要是以queue1开头,都将被路由到到queue1的队列
    }

    /**
     * 将交换机和队列绑定
     */
    @Bean
    Binding replyBinding() {
        return BindingBuilder
                .bind(rpcReplyQueue())//队列
                .to(rpcExchange())//交换机
                .with(RPC_REPLY_MSG_QUEUE);//routing_key名称,#是一个通配符,表示将来消息的routing_key只要是以queue2开头,都将被路由到到queue2的队列
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setReplyAddress(RPC_REPLY_MSG_QUEUE);
        rabbitTemplate.setReceiveTimeout(5);
        return rabbitTemplate;
    }

    @Bean
    SimpleMessageListenerContainer replyContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(connectionFactory);
        simpleMessageListenerContainer.setQueueNames(RPC_REPLY_MSG_QUEUE);
        simpleMessageListenerContainer.setMessageListener(rabbitTemplate(connectionFactory));
        return simpleMessageListenerContainer;
    }
}
