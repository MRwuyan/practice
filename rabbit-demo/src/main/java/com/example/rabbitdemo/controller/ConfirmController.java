package com.example.rabbitdemo.controller;

import com.example.rabbitdemo.config.ConfirmConfig;
import com.example.rabbitdemo.config.RpcConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class ConfirmController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("confirm/send")
    public void hello(String message) {
        Message msg = MessageBuilder.withBody(message.getBytes()).build();
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME, "ConfirmConfig.CONFIRM_QUEUE_NAME",msg);
        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}