package practice.reactive;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * process 需要继承SubmissionPublisher 并实现Processor接口
 * 输入源数据integer,过滤掉小于0的,然后转成string输出
 */
public class MyProcessor extends SubmissionPublisher<String> implements Flow.Processor<Integer, String> {

    private Integer i = 0;
    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(Integer item) {

        System.out.println("接受到数据了:" + item);
        //过滤掉小于0的然后发布
        if (item > 0) {
            this.submit("转换的数据--->" + item);
        }
        //继续请求下个数据
        this.subscription.request(1);
        i++;
        if (i > 10) {
            this.subscription.cancel();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("处理器完事收工");
    }
}
