package com.example.rabbitdemo;

import com.example.rabbitdemo.config.RabbitConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class RabbitDemoApplicationTests {

    @Resource
    RabbitTemplate rabbitTemplate;
    @Test
    void contextLoads() {
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_NAME,"hello,im pore");
    }

}
