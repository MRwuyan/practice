package com.roc.bio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Scattering: 从buffer写入数据时,可以采用buffer数组,依次写
 * Gathering:从buffer读入数据时,可以采用buffer数组,依次读
 */
public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws IOException {
        //使用ServerSocketChannel 和ServerSocket 网络
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        //绑定端口到socket并且启动
        serverSocketChannel.socket().bind(inetSocketAddress);
        //创建buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);
        //等待客户端链接(telnet)
        SocketChannel socketChannel = serverSocketChannel.accept();
        int messageLength = 8;
        while (true) {
            int byteRead=0;
            while (byteRead < messageLength) {
                long l = socketChannel.read(byteBuffers);
                byteRead +=l;//累计读取字节数
                System.out.println("byteRead="+ byteRead);
                Arrays.asList(byteBuffers).stream()
                        .map(buffer -> "position="+buffer.position()+",limit="+buffer.limit())
                        .forEach(System.out::println);
            }
            Arrays.asList(byteBuffers).forEach(buffer -> buffer.flip());
            //将数据显示到客户端
            long byteWrite=0;
            while (byteWrite<messageLength) {
                socketChannel.write(byteBuffers);
                byteWrite ++;
            }
            Arrays.asList(byteBuffers).forEach(buffer -> buffer.clear());
            System.out.println("byteRead:"+byteRead+",byteWrite"+byteWrite+",messageLength"+messageLength);
        }


    }

}
