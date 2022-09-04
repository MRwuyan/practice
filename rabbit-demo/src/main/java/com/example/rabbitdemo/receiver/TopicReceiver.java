package com.example.rabbitdemo.receiver;

import com.example.rabbitdemo.config.TopicConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TopicReceiver {
    /**
     * 通过改方法监听指定队列的消息
     *
     * @param msg
     */
    @RabbitListener(queues = TopicConfig.TOPIC_QUEUE_NAME)
    public void topic1(String msg) {
        System.out.println("QUEUE_NAME接收到消息:" + msg);
    }

    @RabbitListener(queues = TopicConfig.TOPIC_QUEUE_NAME1)
    public void topic2(String msg) {
        System.out.println("queue1接收到消息:" + msg);
    }
    @RabbitListener(queues = TopicConfig.TOPIC_QUEUE_NAME2)
    public void topic3(String msg) {
        System.out.println("queue2接收到消息:" + msg);
    }
}
