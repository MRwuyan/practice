package com.example.rabbitdemo.controller;

import com.example.rabbitdemo.config.RpcConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/send")
    public void hello(String message) {
        Message msg = MessageBuilder.withBody(message.getBytes()).build();
        Message result = rabbitTemplate.sendAndReceive(RpcConfig.RPC_EXCHANGE, RpcConfig.RPC_MSG_QUEUE, msg);
        if (result != null) {
            String correlationId = msg.getMessageProperties().getCorrelationId();
            String correlationId1 = result.getMessageProperties().getCorrelationId();
            String spring_returned_message_correlation =(String) result.getMessageProperties().getHeaders().get("spring_returned_message_correlation");
            System.out.println("spring_returned_message_correlation:"+spring_returned_message_correlation);
            System.out.println("correlationId:"+correlationId+";correlationId1:"+correlationId);
            if (correlationId.equals(correlationId1)) {
                System.out.println("收到服务端的响应:" + new String(result.getBody()));
            }
        }
    }

    /**
     * 单条消息的过期时间
     * @param message
     */
    @GetMapping("ttl/send")
    public void msgttl(String message) {
        Message msg = MessageBuilder.withBody(message.getBytes())
                //过期时间
                .setExpiration("10000")
                .build();
//        rabbitTemplate.send();
    }
    /**
     * 队列的过期时间
     * @param message
     */
    @GetMapping("ttl/queue/send")
    public void queueTtl(String message) {
        Message msg = MessageBuilder.withBody(message.getBytes())
                //过期时间
                .setExpiration("10000")
                .build();
//        rabbitTemplate.send();
    }
}
