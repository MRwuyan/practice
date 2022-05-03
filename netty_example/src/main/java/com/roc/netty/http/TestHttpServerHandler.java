package com.roc.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * 1.SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter
 *  * 2.HttpObject客户端和服务器端互相通讯的数据被封装成HttpObject
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject httpObject) throws Exception {

        //判断是否是httpRequest请求
        if (httpObject instanceof HttpRequest) {
            System.out.println("httpObject类型="+httpObject.getClass());
            System.out.println("客户端地址="+ctx.channel().remoteAddress());

            //回复个浏览器[http协议]
            ByteBuf byteBuf = Unpooled.copiedBuffer("hello,我是玛卡巴卡", CharsetUtil.UTF_8);

            //构造一个http的响应,即HttpResponse
            DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
            defaultFullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            defaultFullHttpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());
            //将构建好的response返回
            ctx.writeAndFlush(defaultFullHttpResponse);
        }
    }
}
