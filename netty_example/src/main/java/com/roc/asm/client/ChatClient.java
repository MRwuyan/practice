package com.roc.asm.client;

import com.roc.asm.message.LoginRequestMessage;
import com.roc.asm.protocol.MessageCodecSharable;
import com.roc.asm.protocol.ProcotolFrameDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class ChatClient {

    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.INFO);
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(group);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ProcotolFrameDecoder());
                    ch.pipeline().addLast(LOGGING_HANDLER);
                    ch.pipeline().addLast(MESSAGE_CODEC);

                    ch.pipeline().addLast("client handle",new ChannelInboundHandlerAdapter(){

                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) {

                           log.info("msg:{}",msg);
                        }

                        @Override
                        public void channelActive(ChannelHandlerContext ctx)   {
                            new Thread(()->{
                                Scanner scanner = new Scanner(System.in);
                                System.out.println("请输入用户名：");
                                String username = scanner.nextLine();
                                System.out.println("请输入密码：");
                                String password = scanner.nextLine();
                                LoginRequestMessage message = new LoginRequestMessage(username, password);
                                //发送消息
                                ctx.writeAndFlush(message);
                                System.out.println("等待。。。");
                             scanner.nextLine();
                            },"system in").start();

//                            super.channelActive(ctx);
                        }
                    });


                }
            });
            Channel channel = bootstrap.connect("localhost",8080).sync().channel();
            channel.closeFuture().sync();
        }catch (InterruptedException e) {
            log.error("client error",e);
        }finally {
            group.shutdownGracefully();
        }
    }
}
