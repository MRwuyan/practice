package com.roc.asm.server;

import com.roc.asm.protocol.MessageCodecSharable;
import com.roc.asm.protocol.ProcotolFrameDecoder;
import com.roc.asm.server.handler.ChatRequestMessageHandler;
import com.roc.asm.server.handler.GroupCreateRequestMessageHandle;
import com.roc.asm.server.handler.LoginRequestMessagedHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatServer {

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.INFO);
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();

        ChatRequestMessageHandler CHAT_HANDLER = new ChatRequestMessageHandler();
        LoginRequestMessagedHandler LOGIN_HANDLER = new LoginRequestMessagedHandler();

        GroupCreateRequestMessageHandle GROUP_CREATE_HANDLE = new GroupCreateRequestMessageHandle();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.group(boss, worker);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch)  {
                    ch.pipeline().addLast(new ProcotolFrameDecoder());
//                    ch.pipeline().addLast(LOGGING_HANDLER);
                    ch.pipeline().addLast(MESSAGE_CODEC);
                    ch.pipeline().addLast(LOGIN_HANDLER);
                    ch.pipeline().addLast(CHAT_HANDLER);
                    ch.pipeline().addLast(GROUP_CREATE_HANDLE);
                }
            });
            Channel channel = serverBootstrap.bind(8080).sync().channel();
            channel.closeFuture().sync();
        }catch (InterruptedException e) {
            log.error("server error",e);
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }


    }

}
