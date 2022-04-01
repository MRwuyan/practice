package com.roc.proxy;

import com.roc.cons.ApiServer;
import com.roc.entity.MethodInfo;
import com.roc.entity.ServerInfo;
import com.roc.handle.RestHandle;
import com.roc.handle.WebClientRestHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class JDKProxyCreator implements ProxyCreator {
    @Override
    public Object createProxy(Class<?> type) {
        log.info("createProxy-->" + type);
        //给每一个代理类一个实现
        RestHandle restHandle = new WebClientRestHandle();
        ServerInfo serverInfo = extractServerInfo(type);
        log.info("serverInfo-->" + serverInfo);
        //初始化服务器信息
        restHandle.init(serverInfo);
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{type}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //根据方法和参数得到调用信息
                MethodInfo methodInfo = extractMethodInfo(method, args);
//                调用rest
                return restHandle.invokeRest(methodInfo);
            }

            /**
             * 获取方法类型和参数
             * @param method
             * @param args
             * @return
             */
            private MethodInfo extractMethodInfo(Method method, Object[] args) {
                MethodInfo methodInfo = new MethodInfo();
                extractUrlAndMethod(method, methodInfo);
                //获取方法参数
                extractParamAndBody(method, args, methodInfo);
                return methodInfo;
            }

            private void extractParamAndBody(Method method, Object[] args, MethodInfo methodInfo) {
                Parameter[] parameters = method.getParameters();
                Map<String, Object> paramMap = new LinkedHashMap<>();
                methodInfo.setParams(paramMap);
                for (int i = 0; i < parameters.length; i++) {
                    //是否带@PathVariable
                    PathVariable pathVariable = parameters[i].getAnnotation(PathVariable.class);
                    if (pathVariable != null) {
                        paramMap.put(pathVariable.name(), args[i]);
                    }
                    //是否带@RequestBody
                    RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
                    if (requestBody != null) {
                        paramMap.put(pathVariable.name(), args[i]);
                        methodInfo.setBody((Mono<?>) args[i]);
                    }
                }
            }

            /**
             * 获取方法url和方法类型
             * @param method
             * @param methodInfo
             */
            private void extractUrlAndMethod(Method method, MethodInfo methodInfo) {
                Annotation[] annotations = method.getAnnotations();
                //获取方法类型
                for (Annotation annotation : annotations) {
                    //get
                    if (annotation instanceof GetMapping) {
                        GetMapping a = (GetMapping) annotation;
                        methodInfo.setUrl(a.value()[0]);
                        methodInfo.setMethod(HttpMethod.GET);
                    } else if (annotation instanceof PostMapping) {
                        PostMapping a = (PostMapping) annotation;
                        methodInfo.setUrl(a.value()[0]);
                        methodInfo.setMethod(HttpMethod.POST);
                    } else if (annotation instanceof PutMapping) {
                        PutMapping a = (PutMapping) annotation;
                        methodInfo.setUrl(a.value()[0]);
                        methodInfo.setMethod(HttpMethod.PUT);
                    } else if (annotation instanceof DeleteMapping) {
                        DeleteMapping a = (DeleteMapping) annotation;
                        methodInfo.setUrl(a.value()[0]);
                        methodInfo.setMethod(HttpMethod.DELETE);
                    }
                }
            }
        });
    }

    /**
     * 获取服务器信息
     *
     * @param type
     * @return
     */
    private ServerInfo extractServerInfo(Class<?> type) {
        ServerInfo serverInfo = new ServerInfo();
        ApiServer annotation = type.getAnnotation(ApiServer.class);
        serverInfo.setUrl(annotation.value());
        return serverInfo;
    }
}
