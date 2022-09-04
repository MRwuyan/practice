package com.example.rabbitdemo.receiver;

import com.example.rabbitdemo.config.DirectConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MsgReceiver2 {
    /**
     * 通过改方法监听指定队列的消息
     *
     * @param msg
     */
    @RabbitListener(queues = DirectConfig.QUEUE_NAME)
    public void handleMessage1(String msg) {
        System.out.println("QUEUE_NAME接收到消息:" + msg);
    }

    @RabbitListener(queues = DirectConfig.DIRECT_QUEUE_NAME)
    public void handleMessage2(String msg) {
        System.out.println("DIRECT_QUEUE_NAME接收到消息:" + msg);
    }

}
