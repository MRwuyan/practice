package com.roc.handle;

import com.roc.entity.MethodInfo;
import com.roc.entity.ServerInfo;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientRestHandle implements RestHandle {
    private WebClient webClient;

    /**
     * 初始化webclient端
     *
     * @param serverInfo
     */
    @Override
    public void init(ServerInfo serverInfo) {
        this.webClient = WebClient.create(serverInfo.getUrl());
    }

    /**
     * 处理rest请求
     *
     * @param methodInfo
     * @return
     */
    @Override
    public Object invokeRest(MethodInfo methodInfo) {
        Object result;
        WebClient.ResponseSpec retrieve = this.webClient
                .method(methodInfo.getMethod())
                .uri(methodInfo.getUrl())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();
        //处理body
        if (methodInfo.getReturnFlux()) {
            result = retrieve.bodyToFlux(methodInfo.getReturnElementType());
        } else {
            result = retrieve.bodyToMono(methodInfo.getReturnElementType());
        }


        return result;
    }
}
