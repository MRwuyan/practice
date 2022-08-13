package com.example.rabbitdemo.config.receiver;

import com.example.rabbitdemo.config.RabbitConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MsgReceiver {
  /*  *//**
     * queues:通过改方法监听指定队列的消息
     *
     *//*
    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void handleMessage(String msg){
        System.out.println("b接收到消息:"+msg);
    }

    *//**
     *concurrency:30个线程
     * @param msg
     *//*
    @RabbitListener(queues = RabbitConfig.QUEUE_NAME,concurrency = "30")
    public void handleMessage2(String msg){
        System.out.println("a接收到消息:"+msg);
    }*/


    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void handleMessage(Message msg, Channel channel) throws IOException {

        System.out.println("b接收到消息:"+msg.getPayload());
        //手动确认消息,消费成功
        channel.basicReject((Long) msg.getHeaders().get(AmqpHeaders.DELIVERY_TAG),false);

    }

    /**
            *concurrency:1个线程
     * @param msg
     */
    @RabbitListener(queues = RabbitConfig.QUEUE_NAME,concurrency = "1")
    public void handleMessage2(Message msg, Channel channel) throws IOException {
        System.out.println("a接收到消息:"+msg.getPayload());
        //手动确认消息,消费成功
        //requeue 是否拒绝
        channel.basicReject((Long) msg.getHeaders().get(AmqpHeaders.DELIVERY_TAG),true);
    }
}
