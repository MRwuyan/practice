package com.example.rabbitdemo.receiver;

import com.example.rabbitdemo.config.FanoutConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
@SpringBootTest
class FanoutReceiverTest {
    @Resource
    RabbitTemplate rabbitTemplate;
    @Test
    void contextLoads() {
        rabbitTemplate.convertAndSend(FanoutConfig.FANOUT_EXCHANGE_NAME,"hello,im pore");
    }
}