package com.roc.rpc.server;

import com.roc.asm.protocol.MessageCodecSharable;
import com.roc.asm.protocol.ProcotolFrameDecoder;
import com.roc.rpc.handler.RpcRequestMessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author pore
 * @date 2024年08月22日 11:26
 * @description rpc 服务端
 */
@Slf4j
public class RpcServer {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.INFO);
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        // rpc 请求消息处理器，待实现
        RpcRequestMessageHandler RPC_REQUEST_HANDLER = new RpcRequestMessageHandler();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        sc.pipeline().addLast(new ProcotolFrameDecoder());
                        sc.pipeline().addLast(LOGGING_HANDLER);
                        sc.pipeline().addLast(MESSAGE_CODEC);
                        sc.pipeline().addLast(RPC_REQUEST_HANDLER);

                    }
                });
        try {
            Channel channel = bootstrap.bind(8080).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("server error,{}", e.getMessage());
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }
}
