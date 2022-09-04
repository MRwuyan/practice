package com.example.rabbitdemo.receiver;

import com.example.rabbitdemo.config.FanoutConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutReceiver {
    /**
     * 通过改方法监听指定队列的消息
     *
     * @param msg
     */
    @RabbitListener(queues = FanoutConfig.FANOUT_QUEUE_NAME1)
    public void handleMessage1(String msg) {
        System.out.println("QUEUE_NAME接收到消息:" + msg);
    }

    @RabbitListener(queues = FanoutConfig.FANOUT_QUEUE_NAME2)
    public void handleMessage2(String msg) {
        System.out.println("DIRECT_QUEUE_NAME接收到消息:" + msg);
    }

}
