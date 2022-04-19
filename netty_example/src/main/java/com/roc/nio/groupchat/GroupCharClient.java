package com.roc.nio.groupchat;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class GroupCharClient {
    private final String HOST = "localhost";
    private final int PORT = 6667;

    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    public GroupCharClient() throws IOException {
        selector = Selector.open();
        //链接服务器
        socketChannel = SocketChannel.open((new InetSocketAddress(HOST, PORT)));
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //将channel注册到selector
        socketChannel.register(selector, SelectionKey.OP_READ);
        //得到username
        SocketAddress remoteAddress = socketChannel.getRemoteAddress();
        username = remoteAddress.toString();

        System.out.println(remoteAddress);
    }
    public void sendInfo(String info){
        info = username + "说:" + info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void readInfo(){
        try {
            int readChannels = selector.select();
            if (readChannels > 0) {//有可用的通道
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        channel.read(buffer);
                        String msg = new String(buffer.array());
                        System.out.println(msg);
                    }
                }
                iterator.remove();
            } else {
                System.out.println("没有可用的通道");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        GroupCharClient groupCharClient = new GroupCharClient();
        new Thread(() -> {
            while (true) {
                groupCharClient.readInfo();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            groupCharClient.sendInfo(s);
        }
    }
}
