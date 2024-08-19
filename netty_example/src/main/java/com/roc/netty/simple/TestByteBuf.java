package com.roc.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.nio.ByteBuffer;

import static com.roc.netty.util.ByteBufUtil.log;

/**
 * @author pore
 * @date 2024年08月15日 8:50
 * @description bytebuf demo
 */
public class TestByteBuf {

    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        log(buf);
        buf.writeBytes("hello world".getBytes());
        log(buf);

        ByteBuffer nioBuffer = ByteBuffer.allocate(10);
        nioBuffer.put("hello worldsssssssssss".getBytes());//超过容量会报错,而不会扩容,byteBuf就会自动扩容
    }
}
