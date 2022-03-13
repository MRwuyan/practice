package com.roc.practice.reactive;

import java.util.Spliterator;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * jdk9
 * flow ->publish  生产者
 * ->subscriber  消费者
 */
public class ReactiveStreamDemo1 {
    public static void main(String[] args) throws InterruptedException {
//        1.定义发布者，发布的数据类型是integer
        //直接使用jdk自带的submissionPublisher,他实现了publish接口

        SubmissionPublisher publisher = new SubmissionPublisher();
//        2.定义订阅者
        Flow.Subscriber<Integer> subscriber = new Flow.Subscriber<>() {

            private Flow.Subscription subscription;
            private Integer i=0;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                //保存订阅关系，需要用他来发给发布者响应
                this.subscription = subscription;
                subscription.request(1);
            }

            @Override
            public void onNext(Integer item) {
                i++;
                //接受数据，处理
                System.out.println("接受到数据:"+item);
                //处理完调用request请求
                subscription.request(1);
                //停止处理请求
                if (i>=10) {
                    System.out.println("次数够了,停止");
                    subscription.cancel();
                }

            }

            @Override
            public void onError(Throwable throwable) {
                //处理异常
                throwable.printStackTrace();
                //有异常就不发布了
                subscription.cancel();
            }

            @Override
            public void onComplete() {
                //全部数据处理完了,发布者也关闭了发布
                System.out.println("----------------------------gg----------------------------");
            }
        };
        //            3.发布者和订阅者建立订阅关系
        publisher.subscribe(subscriber);
        //生产数据,并发布
        //这里忽略数据生产过程
        for (int i = 0; i < 100; i++) {
            publisher.submit(i);
        }
        //5.结束后,关闭发布者
        //正式环境,应该是在finally中或者try-resouce确保关闭
        publisher.close();
        //主线程延迟停止,否则数据没有消费就退出了
//        Thread.sleep(10000L);
        Thread.currentThread().join(1000);
    }
}
