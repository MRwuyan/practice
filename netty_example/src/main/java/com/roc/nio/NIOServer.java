package com.roc.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    public static void main(String[] args) throws IOException {
        //serverSocketChannel->socketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        serverSocketChannel.configureBlocking(false);

        Selector selector = Selector.open();

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            if (selector.select(1000)==0) {
                System.out.println("服务器登录1s,无连接");
                continue;
            }
            //如果返回>0,就获取相关的selectionKey集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();//是发生事件的key
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            if (iterator.hasNext()) {
                //获取到key
                SelectionKey selectionKey = iterator.next();
                //是否有新的客户端链接事件
                if (selectionKey.isAcceptable()) {
                    //监听客户端注册,这里不会等待阻塞,因为已经确定有链接事件
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("新的客户端链接成功:"+socketChannel.toString());
                    //将socketChannel设置为非阻塞
                    socketChannel.configureBlocking(false);
                    //监听一个OP_READ事件,将channel注册到selector,并且关联一个buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    System.out.println("连接到的客户端一共有:"+selector.keys());
                }

                //获取OP_READ事件
                if (selectionKey.isReadable()) {
                    //获取channel
                    SocketChannel channel = (SocketChannel)selectionKey.channel();
                    //获取buffer
                    ByteBuffer byteBuffer =(ByteBuffer) selectionKey.attachment();
                    channel.read(byteBuffer);

                    System.out.println("from 客户端 "+new String(byteBuffer.array()));
                }
                //手动从集合中移除当前的selectionKey,防止重复操作
                iterator.remove();
            }
        }
    }
}
