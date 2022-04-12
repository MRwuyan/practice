package com.roc.nio;

import java.nio.ByteBuffer;

/**
 *
 */
public class NIOFileChannel06 {
    public static void main(String[] args) {
        ByteBuffer allocate = ByteBuffer.allocate(512);
        allocate.putInt(1);
        allocate.putChar('j');
        allocate.putDouble(1.01D);
        allocate.flip();
        //设置只读buffer,
        ByteBuffer byteBuffer = allocate.asReadOnlyBuffer();
        System.out.println(byteBuffer.getClass());
        //这里会抛出ReadOnlyBufferException异常
        byteBuffer.putDouble(1D);
    }
}
