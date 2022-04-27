package com.roc.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 1.自定义一个handle 需要继续netty规定好的某个handlerAdapter规范
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 读取数据
     *
     * @param ctx 上下文对象,含有管道pipeline,通道channel ,地址能
     * @param msg 客户端发送的数据 默认object
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx:" + ctx);
        //
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("客户端发送的消息" + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址" + ctx.channel().remoteAddress());

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        //writeAndFlush =write+flush
        //将数据写入缓存,并刷新
        //一般讲,我们对这个发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello ,唔西迪西", CharsetUtil.UTF_8));
    }

    /**
     * 处理异常,一般关闭通道
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
