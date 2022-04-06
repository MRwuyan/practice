package com.roc.bio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel01 {
    public static void main(String[] args) throws IOException {
        String str = "hello ddd";
        FileOutputStream fileOutputStream = new FileOutputStream("d:\\ddd.txt");
        //获取channel
        FileChannel channel = fileOutputStream.getChannel();
        //创建一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();
        channel.write(byteBuffer);
        channel.close();
        fileOutputStream.close();




    }
}
