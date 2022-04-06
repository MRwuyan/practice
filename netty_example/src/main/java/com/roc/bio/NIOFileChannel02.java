package com.roc.bio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel02 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("ddd.txt");
        //获取channel
        FileChannel inputChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("ddd1.txt");
        //获取channel
        FileChannel outputChannel = fileOutputStream.getChannel();


        //创建一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        while (true) {
            byteBuffer.clear();
            int read = inputChannel.read(byteBuffer);
            if (read==-1) {
                break;
            }
            byteBuffer.flip();
            outputChannel.write(byteBuffer);
        }


        fileInputStream.close();
        fileOutputStream.close();
    }
}
