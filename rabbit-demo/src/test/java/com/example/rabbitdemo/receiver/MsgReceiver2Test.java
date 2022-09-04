package com.example.rabbitdemo.receiver;

import com.example.rabbitdemo.config.DirectConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
@SpringBootTest
class MsgReceiver2Test {
    @Resource
    RabbitTemplate rabbitTemplate;
    @Test
    void contextLoads() {
        rabbitTemplate.convertAndSend(DirectConfig.DIRECT_EXCHANGE_NAME,DirectConfig.QUEUE_NAME,"hello,im pore");
        rabbitTemplate.convertAndSend(DirectConfig.DIRECT_QUEUE_NAME,DirectConfig.DIRECT_QUEUE_NAME,"hello,im direct");
    }
}