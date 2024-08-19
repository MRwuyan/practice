package com.roc.asm.server.handler;

import com.roc.asm.message.GroupCreateRequestMessage;
import com.roc.asm.message.GroupCreateResponseMessage;
import com.roc.asm.server.session.Group;
import com.roc.asm.server.session.GroupSessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;
import java.util.Set;

/**
 * @author pore
 * @date 2024年08月19日 15:57
 * @description todo 类描述
 */
@ChannelHandler.Sharable
public class GroupCreateRequestMessageHandle extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        Set<String> members = msg.getMembers();

        Group group = GroupSessionFactory.getGroupSession().createGroup(groupName, members);
        if (group == null) {
            ctx.channel().writeAndFlush(groupName + "群组创建成功");
            List<Channel> membersChannel = GroupSessionFactory.getGroupSession().getMembersChannel(groupName);
            for (Channel channel : membersChannel) {
                channel.writeAndFlush(new GroupCreateResponseMessage(true,"您已被拉入群组:" + groupName));
            }
        } else {
            ctx.channel().writeAndFlush(new GroupCreateResponseMessage(false,"该群组已存在:" + groupName));
        }

    }
}
