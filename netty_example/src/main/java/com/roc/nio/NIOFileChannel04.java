package com.roc.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel04 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("ddd.txt");
        //获取channel
        FileChannel inputChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("ddd2.txt");
        //获取channel
        FileChannel outputChannel = fileOutputStream.getChannel();
        outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        fileInputStream.close();
        fileOutputStream.close();
    }
}
