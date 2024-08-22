package com.roc.rpc.client;

import com.roc.asm.protocol.MessageCodecSharable;
import com.roc.asm.protocol.ProcotolFrameDecoder;
import com.roc.rpc.handler.RpcResponseMessageHandler;
import com.roc.rpc.message.RpcRequestMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author pore
 * @date 2024年08月22日 14:12
 * @description todo 类描述
 */
@Slf4j
public class RpcClient {
    public static void main(String[] args) {
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
            Channel channel = bootstrap.connect("localhost", 8080).sync().channel();
            channel.writeAndFlush(
                    new RpcRequestMessage(
                            1,
                            "com.roc.rpc.server.service.HelloService",
                            "sayHello",
                            String.class,
                            new Class[]{String.class},
                            new Object[]{"helloworld!"}
                    )
                    // 发送不成功  【打印错误信息】
            ).addListener(promise->{
                if(!promise.isSuccess()){
                    Throwable cause = promise.cause();
                    log.error("error : ", cause);
                }
            });

            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("client error", e);
        }finally {
            group.shutdownGracefully();
        }

    }
}
