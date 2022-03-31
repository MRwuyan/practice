package com.roc.proxy;

import com.roc.entity.MethodInfo;
import com.roc.entity.ServerInfo;
import com.roc.handle.RestHandle;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
public class JDKProxyCreator implements ProxyCreator{

    @Override
    public Object createProxy(Class<?> type) {
        //给每一个代理类一个实现
        RestHandle restHandle = null;

        ServerInfo serverInfo = extractServerInfo(type);
        //初始化服务器信息
        restHandle.init(serverInfo);
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{type}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //根据方法和参数得到调用信息
                MethodInfo methodInfo = extractMethodInfo(method,args);
//                调用rest
                return  restHandle.invokeRest(methodInfo);
            }

            private MethodInfo extractMethodInfo(Method method, Object[] args) {
                return null;
            }
        });
    }

    private ServerInfo extractServerInfo(Class<?> type) {
        return null;
    }
}
