package com.example.rabbitdemo.config;

import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;


@Configuration
public class ConfirmConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {
    public static final String CONFIRM_EXCHANGE_NAME = "confirm_exchange_name";
    public static final String CONFIRM_QUEUE_NAME = "confirm_queue_name";
    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void initRabbitTemplate(){
        rabbitTemplate.setReturnsCallback(this);
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setConfirmCallback(this);
    }


    @Bean
    Binding confirmBinding() {
        return BindingBuilder.bind(confirmQueue())
                .to(confirmExchange())
                .with(CONFIRM_QUEUE_NAME);
    }

    @Bean
    DirectExchange confirmExchange() {
        return new DirectExchange(CONFIRM_EXCHANGE_NAME, true, false);
    }

    @Bean
    Queue confirmQueue() {
        return new Queue(CONFIRM_QUEUE_NAME, true, false, false);
    }

    /**
     * 消息成功到达交换机,会触发该方法
     *
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            System.out.println("消息到交换机Leeee");
        } else {
            System.err.println("消息没到交换机Leeee");
        }
    }

    /**
     * 消息未成功到达队列,会触发该方法
     *
     * @param returnedMessage
     */
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        System.out.println("消息没到队列");
    }
}
