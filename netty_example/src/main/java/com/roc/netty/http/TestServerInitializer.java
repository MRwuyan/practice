package com.roc.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //向管道加入处理器
        //得到管道
        ChannelPipeline pipeline = socketChannel.pipeline();
        //加入一个netty提供的httpServerCodec codec>[coder - decoder]
        //HttpServerCodec 是netty提供的处理http的编码解码器
        //this.lineParser = new HttpObjectDecoder.LineParser(seq, maxInitialLineLength);
        //            this.headerParser = new HttpObjectDecoder.HeaderParser(seq, maxHeaderSize);
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());


        pipeline.addLast("MyTestHttpServerHandler", new TestHttpServerHandler());

    }
}
