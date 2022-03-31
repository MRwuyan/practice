package practice.reactive;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class ReactiveStreamDemo2 {
    public static void main(String[] args) throws InterruptedException {
//        1.定义发布者,发布类型integer
        //直接使用submissionPublish
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();
        //2.定义处理器,对数据进行过滤
        MyProcessor processor = new MyProcessor();
        //3.发布者 和处理器建立订阅关系
        publisher.subscribe(processor);

        //4.定义最终消费者,消费string类型
        Flow.Subscriber<String> subscription = new Flow.Subscriber<>() {

            private Flow.Subscription subscription;
            private Integer i=0;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                //保存订阅关系，需要用他来发给发布者响应
                this.subscription = subscription;
                subscription.request(1);
            }

            @Override
            public void onNext(String item) {
                i++;
                //接受数据，处理
                System.out.println("接受到数据:"+item);
                //处理完调用request请求
                subscription.request(1);
//                //停止处理请求
//                if (i>=10) {
//                    System.out.println("次数够了,停止");
//                    subscription.cancel();
//                }

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
        //5.处理器和订阅者建立订阅关系
        processor.subscribe(subscription);
        publisher.submit(111);
        publisher.submit(-111);

        publisher.close();
        Thread.currentThread().join(1000);
    }
}
