package com.example.rabbitdemo.receiver;

import com.example.rabbitdemo.config.TopicConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class TopicReceiverTest {
    @Resource
    RabbitTemplate rabbitTemplate;
    @Test
    void contextLoads() {

        rabbitTemplate.convertAndSend(TopicConfig.TOPIC_EXCHANGE_NAME,"queue2.queue","hello,im queue");

     }
}