package com.roc.asm.server.handler;

import com.roc.asm.message.ChatRequestMessage;
import com.roc.asm.message.ChatResponseMessage;
import com.roc.asm.server.session.SessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author pore
 * @date 2024年08月19日 11:37
 * @description todo 类描述
 */
@ChannelHandler.Sharable
public class ChatRequestMessageHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatRequestMessage msg) throws Exception {

        String to = msg.getTo();
        String from = msg.getFrom();
        Channel channel = SessionFactory.getSession().getChannel(to);
        String content = msg.getContent();

        if (channel != null) {
            channel.writeAndFlush(new ChatResponseMessage(from, content));
        } else {
            ctx.channel().writeAndFlush(new ChatResponseMessage(false, "对方用户不在线"));
        }


    }
}
