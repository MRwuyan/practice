package com.example.rabbitdemo;

import com.example.rabbitdemo.config.DirectConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class RabbitDemoApplicationTests {

    @Resource
    RabbitTemplate rabbitTemplate;
    @Test
    void contextLoads() {
        rabbitTemplate.convertAndSend(DirectConfig.QUEUE_NAME,"hello,im pore");
    }

}
