package com.roc.rpc.server.service;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String msg) {
        /*
        不做处理的话：爆出：io.netty.handler.codec.TooLongFrameException: Adjusted frame length exceeds 1024: 11509 - discarded
        处理地方：RpcRequestMessageHandler ------42行
         */
//        int i = 1 / 0;
        return "服务端ROBOT ：你好, " + msg;
    }
    public static void main(String[] args) {
        try {
            InetAddress myIP = InetAddress.getLocalHost();
            System.out.println("本地 IP 地址: " + myIP.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}