package com.roc.nio;




import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.roc.uitl.ByteBufferUtil.debugAll;

/**
 * 边界处理
 */
@Slf4j
public class BoundaryTreatment {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();//打开流
        ssc.bind(new InetSocketAddress(8080));//绑定8080
        ssc.configureBlocking(false);//非阻塞模式

        //1.注册channel
        Selector selector = Selector.open();
        SelectionKey sscKey = ssc.register(selector, 0, null);//将ssc交给selector托管
        sscKey.interestOps(SelectionKey.OP_ACCEPT);//专注于accept事件

        while (true) {
            //2.select 方法
            selector.select();//监听

            // 3.处理事件
            Iterator<SelectionKey> iterator = selector.keys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();//要先移除，否则会一直处理该事件
                if (key.isAcceptable()) {
                    //处理accept
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    log.debug("key:{}",key);
                    SocketChannel sc = channel.accept();//获取到消息通道channel
                    ByteBuffer buffer = ByteBuffer.allocate(8);//将buffer放到read中
                    SelectionKey scKey = sc.register(selector, 0, buffer);//将sc交给selector托管
                    scKey.interestOps(SelectionKey.OP_READ);//该key专注于read事件，上面的sscKey才是专注于accept的，谨防混淆
                    log.debug("sc->{}",sc);
                } else if (key.isReadable()) {
                    try {
                        //处理read事件
                        SocketChannel channel =(SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        int len = channel.read(buffer);
                        System.out.println(len);
                        if (len == -1) {
                            key.cancel();//客户端断开，会收到一个-1的读取
                            System.out.println("主动断开链接");
                        } else {
                            split(buffer);
                            if (buffer.position()==buffer.limit()) {//说明没有发生切割，需要扩容
                                ByteBuffer newByteBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
                                buffer.flip();
                                newByteBuffer.put(buffer);
                                key.attach(newByteBuffer);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        key.cancel();
                        System.out.println("强制断开连接 ");
                    }

                }

            }
        }
    }

    private static void split(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            // 找到一条完整消息
            if (source.get(i) == '\n') {
                int length = i + 1 - source.position();
                // 把这条完整消息存入新的 ByteBuffer
                ByteBuffer target = ByteBuffer.allocate(length);
                // 从 source 读，向 target 写
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                debugAll(target);
            }
        }
        source.compact(); // 0123456789abcdef  position 16 limit 16
    }
}
