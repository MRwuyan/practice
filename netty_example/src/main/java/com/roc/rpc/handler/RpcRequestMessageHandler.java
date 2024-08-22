package com.roc.rpc.handler;

import com.roc.rpc.message.RpcRequestMessage;
import com.roc.rpc.message.RpcResponseMessage;
import com.roc.rpc.server.service.HelloService;
import com.roc.rpc.server.service.ServiceFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author pore
 * @date 2024年08月22日 11:32
 * @description todo 类描述
 */

@ChannelHandler.Sharable
@Slf4j
public class RpcRequestMessageHandler extends SimpleChannelInboundHandler<RpcRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequestMessage message) throws Exception {
        final RpcResponseMessage response = new RpcResponseMessage();
        response.setSequenceId(message.getSequenceId());


        try {
            //根据路径获取接口
            final Class<?> interfaceClazz = Class.forName(message.getInterfaceName());
            // 根据接口类 获取 【接口实现类】
            final HelloService service = (HelloService) ServiceFactory.getService(interfaceClazz);
            // clazz 根据 方法名和参数类型 确定 【具体方法】
            final Method method = service.getClass().getMethod(message.getMethodName(), message.getParameterTypes());
            // 根据 具体方法 使用代理 【执行方法】
            final Object invoke = method.invoke(service, message.getParameterValue());

            response.setReturnValue(invoke);
        } catch (Exception e) {
            log.debug("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx 出异常了 xxxxxxxxxxxxxxxxxxxxxxxxxx");
            e.printStackTrace();
//            response.setExceptionValue(e);                         【e.getCause() 拿到问题的起因】
            response.setExceptionValue(new Exception("远程调用出错：" + e.getCause().getMessage()));
        }
        ctx.writeAndFlush(response);
    }
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        final RpcRequestMessage requestMsg = new RpcRequestMessage(
                1,
                "com.roc.rpc.server.service.HelloService",
                "sayHello",
                String.class,
                new Class[]{String.class},
                new Object[]{"helloworld!"}
        );
        // 上面对象里 获取【接口类】全限定名
        final Class<?> interfaceClazz = Class.forName(requestMsg.getInterfaceName());
        // 根据接口类 获取 【接口实现类】
        final HelloService service = (HelloService) ServiceFactory.getService(interfaceClazz);
        // 根据 方法名和参数类型 确定 【具体方法】
        final Method method = service.getClass().getMethod(requestMsg.getMethodName(), requestMsg.getParameterTypes());
        // 根据 具体方法 使用代理 【执行方法】
        final Object invoke = method.invoke(service, requestMsg.getParameterValue());
        System.out.println(invoke);


    }
}
