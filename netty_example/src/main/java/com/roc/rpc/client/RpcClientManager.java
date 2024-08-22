package com.roc.rpc.client;

import com.roc.asm.protocol.MessageCodecSharable;
import com.roc.asm.protocol.ProcotolFrameDecoder;
import com.roc.rpc.handler.RpcResponseMessageHandler;
import com.roc.rpc.message.RpcRequestMessage;
import com.roc.rpc.protocol.SequenceIdGenerator;
import com.roc.rpc.server.service.HelloService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;

import static com.roc.rpc.handler.RpcResponseMessageHandler.PROMISES;

/**
 * @author pore
 * @date 2024年08月22日 15:58
 * @description todo 类描述
 */
@Slf4j
public class RpcClientManager {

    private static Channel channel;

    private static final Object LOCK = new Object();
    public static void main(String[] args) {
 /*       getChannel().writeAndFlush(
                new RpcRequestMessage(
                        1,
                        "com.rpc.server.service.HelloService",
                        "sayHello",
                        String.class,
                        new Class[]{String.class},
                        new Object[]{"helloworld!"}
                )
        );*/

        HelloService proxyService = getProxyService(HelloService.class);
        System.out.println(proxyService.sayHello("你好！"));
    }
    public static <T> T getProxyService(Class<T> serviceClass) {
        // 类加载器， 代理的实现接口的数组，
        ClassLoader loader = serviceClass.getClassLoader();
        Class[] interfaces = {serviceClass};
        final int nextId = SequenceIdGenerator.nextId();
        //                                                                  sayHello "你好！"
        final Object o = Proxy.newProxyInstance(loader, interfaces, (proxy, method, args) -> {
            // 1. 将方法调用 转为 消息对象
            final RpcRequestMessage msg = new RpcRequestMessage(
                    nextId,
                    serviceClass.getName(),
                    method.getName(),
                    method.getReturnType(),
                    method.getParameterTypes(),
                    args
            );
            //将消息发出去
            getChannel().writeAndFlush(msg);
            // 2. 等待结果 准备一个promise                      指定promise 对象异步接受结果的线程
            DefaultPromise<Object> promise = new DefaultPromise<>(getChannel().eventLoop());
            PROMISES.put(nextId, promise);
            promise.await();
            if (promise.isSuccess()) {
                //调用正常
                return promise.getNow();
            } else {
                //调用失败
                throw new RuntimeException(promise.cause());
            }
        });
        return (T)o;
    }
    private static Channel getChannel() {

        if (channel != null)return channel;

        synchronized (LOCK){

            if (channel != null)return channel;
            initChannel();
            return channel;
        }
    }

    private static void initChannel() {
        NioEventLoopGroup group = new NioEventLoopGroup();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.INFO);
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable(); // 【使用 asm包方法】
        // rpc 响应消息处理器，待实现
        RpcResponseMessageHandler RPC_RESPONSE_HANDLER = new RpcResponseMessageHandler();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class)
                .group(group)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ProcotolFrameDecoder());
                        ch.pipeline().addLast(LOGGING_HANDLER);
                        ch.pipeline().addLast(MESSAGE_CODEC);
                        ch.pipeline().addLast(RPC_RESPONSE_HANDLER);
                    }
                });
        try {
            channel = bootstrap.connect("localhost", 8080).sync().channel();
            channel.closeFuture().addListeners(future->{
                group.shutdownGracefully();
            });
        } catch (InterruptedException e) {
            log.error("client error", e);
        }
    }
}
