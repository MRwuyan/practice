package com.roc.bio;

import java.nio.ByteBuffer;

/**
 * buffer支持类型化的put和get,put放入什么,get时就应该拿出相应的类型
 * 否则可能会抛出异常BufferUnderflowException
 */
public class NIOFileChannel05 {
    public static void main(String[] args) {
        ByteBuffer allocate = ByteBuffer.allocate(512);
        allocate.putInt(1);
        allocate.putChar('j');
        allocate.putDouble(1.01D);
        allocate.flip();
        System.out.println(allocate.getInt());
        System.out.println(allocate.getInt());
        System.out.println(allocate.getLong());
    }
}
