package com.roc.nio.groupchat;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class GroupChatServer {

    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT=6667;
    public GroupChatServer(){
        try {
            //得到选择器
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
//            绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //设置非阻塞模式
            listenChannel.configureBlocking(false);
            //将listenChannel注册到选择器
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (Exception e) {
        }
    }

    /**
     * 监听
     */
    public void listen(){
        try {
            while (true) {
                int count = selector.select();
                if (count > 0) {
                    //遍历得到selectionKey集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        //取出key
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()) {
                            SocketChannel sc = listenChannel.accept();
                            sc.configureBlocking(false);
                            sc.register(selector, SelectionKey.OP_READ);
                            System.out.println(sc.getRemoteAddress());
                        }
                        if (key.isReadable()) {//通道发送read时间,即通道是可读状态
                            //处理读
                            readData(key);
                        }
//                        当前的key删除
                        iterator.remove();
                    }
                } else {
                    System.out.println("无事件,等待ing");
                }

            }
        } catch (Exception e) {
        }
    }

    private void readData(SelectionKey key) throws IOException {
        SocketChannel channel = null;
        try {
            //得到channel
            channel = (SocketChannel) key.channel();
            //得到buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            if (count>0) {
                String msg = new String(buffer.array());
                System.out.println("from 客户端:"+msg);
                //向其他客户端发起消息
                sendInfoToOtherClients(msg,channel);
            }

        } catch (IOException e) {
            try {
                SocketAddress remoteAddress = channel.getRemoteAddress();
                System.out.println(remoteAddress + "离线了");
                key.cancel();
                channel.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }
    private void sendInfoToOtherClients(String msg,SocketChannel self) throws IOException {
        for (SelectionKey key : selector.keys()) {
            //获取channel
            Channel channel = key.channel();
            //判断为SocketChannel和不是自己
            if (channel instanceof SocketChannel &&channel != self ) {
                SocketChannel dest = (SocketChannel) channel;
                ByteBuffer wrap = ByteBuffer.wrap(msg.getBytes());
                dest.write(wrap);
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer server = new GroupChatServer();
        server.listen();

    }
}
