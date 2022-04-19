package com.roc.nio.zore;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NewIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket socket = serverSocketChannel.socket();
        socket.bind(new InetSocketAddress(7001));
        ///
        ByteBuffer byteBuffer = ByteBuffer.allocate(5000);
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            int readCount = 0;
            while (readCount != -1) {
                readCount = socketChannel.read(byteBuffer);
                byteBuffer.rewind();//倒带,position = 0;mark = -1;

            }
        }
    }
}
