package com.roc.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

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

        //比如:这里有个耗时很长的任务>异步执行>提交给channel对应的NIOEventLoop的taskQueue
        //方案1.
        ctx.channel().eventLoop().execute(() -> {
            try {
                Thread.sleep(5*1000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello ,唔西迪西,这里是read1", CharsetUtil.UTF_8));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        ctx.channel().eventLoop().execute(() -> {
            try {
                Thread.sleep(5*1000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello ,唔西迪西,这里是read2", CharsetUtil.UTF_8));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        //方案2:用户自定义定时任务->该任务是提交到scheduleTaskQueue
        ctx.channel().eventLoop().schedule(() -> {
            try {
                Thread.sleep(5*1000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello ,唔西迪西,这里是read3", CharsetUtil.UTF_8));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },5L, TimeUnit.SECONDS);
     /*   System.out.println("server ctx:" + ctx);
        //
        System.out.println("channel:"+ctx.channel());
        System.out.println("pipeline:"+ctx.pipeline());//双向链表
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("客户端发送的消息" + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址" + ctx.channel().remoteAddress());*/

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        //writeAndFlush =write+flush
        //将数据写入缓存,并刷新
        //一般讲,我们对这个发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello ,唔西迪西,这里是complete", CharsetUtil.UTF_8));
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
