package com.roc.handle;

import com.roc.entity.MethodInfo;
import com.roc.entity.ServerInfo;

public interface RestHandle {
    /**
     * 初始化服务器信息
     * @param serverInfo
     */
    void init(ServerInfo serverInfo);

    /**
     * 用rest请求返回结果
     * @param methodInfo
     * @return
     */
    Object invokeRest(MethodInfo methodInfo);
}
