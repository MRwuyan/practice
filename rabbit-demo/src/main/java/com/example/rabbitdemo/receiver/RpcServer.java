package com.example.rabbitdemo.receiver;

import com.example.rabbitdemo.config.RpcConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RpcServer {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RpcConfig.RPC_MSG_QUEUE)
    public void process(Message message){
        byte[] body = message.getBody();
        Message build = MessageBuilder.withBody(("我是服务端,我收到了消息:" + new String(body)).getBytes()).build();
        CorrelationData correlationData = new CorrelationData(message.getMessageProperties().getCorrelationId());
        rabbitTemplate.sendAndReceive(RpcConfig.RPC_EXCHANGE, RpcConfig.RPC_REPLY_MSG_QUEUE, build, correlationData);
    }
}
