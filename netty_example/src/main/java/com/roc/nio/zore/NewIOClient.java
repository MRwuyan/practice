package com.roc.nio.zore;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel open = SocketChannel.open();
        open.connect(new InetSocketAddress("localhost", 7001));
        String filename = "";
        FileChannel fileChannel = new FileInputStream(filename).getChannel();
        long startTime = System.currentTimeMillis();
        long l = fileChannel.transferTo(0, fileChannel.size(), open);
        System.out.println("发送总字节数:"+l+"耗时:"+(System.currentTimeMillis()-startTime));
        fileChannel.close();

    }
}
