package com.roc.rpc.handler;

import com.roc.rpc.message.RpcRequestMessage;
import com.roc.rpc.message.RpcResponseMessage;
import com.roc.rpc.server.service.HelloService;
import com.roc.rpc.server.service.ServiceFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author pore
 * @date 2024年08月22日 11:32
 * @description todo 类描述
 */
@ChannelHandler.Sharable
@Slf4j
public class RpcResponseMessageHandler extends SimpleChannelInboundHandler<RpcResponseMessage> {

    public static final Map<Integer, Promise<Object>> PROMISES = new ConcurrentHashMap<>();
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponseMessage msg) throws Exception {

        final Promise<Object> promise = PROMISES.remove(msg.getSequenceId()); // 获取 并销毁值
        if (promise != null) {

            final Object returnValue = msg.getReturnValue();         // 正常结果
            final Exception exceptionValue = msg.getExceptionValue();// 异常结果 【约定 为 null才是正常的】
            if (exceptionValue != null) {

                promise.setFailure(exceptionValue);

            }else{
                promise.setSuccess(returnValue);
            }


        }
        log.info("{}",msg);
    }
}
