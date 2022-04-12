package com.roc.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * MappedByteBuffer 可让文件直接再内存中(堆外内存)修改,操作系统不需要拷贝一次
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("ddd.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        /**
         * 参数1:使用的读写模式
         * 参数2:表示从起始位置从0的位置开始映射到内存
         * 参数3:映射到内存的大小(不是索引位置),即将ddd.txt的多少个自己映射到内存
         *
         */
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        map.put(0, (byte) 'H');
        channel.close();
    }
}
