package com.roc.asm.server.handler;

import com.roc.asm.message.LoginRequestMessage;
import com.roc.asm.message.LoginResponseMessage;
import com.roc.asm.server.service.UserServiceFactory;
import com.roc.asm.server.session.SessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author pore
 * @date 2024年08月19日 11:31
 * @description 登录处理
 */
@ChannelHandler.Sharable
public class LoginRequestMessagedHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) {
        String username = msg.getUsername();
        String password = msg.getPassword();
        boolean login = UserServiceFactory.getUserService().login(username, password);

        LoginResponseMessage responseMessage;
        if (login) {
            //绑定session
            SessionFactory.getSession().bind(ctx.channel(), username);
            responseMessage = new LoginResponseMessage(true, "登陆成功");
        } else {
            responseMessage = new LoginResponseMessage(false, "账号或密码错误");
        }
        ctx.writeAndFlush(responseMessage);
    }
}
