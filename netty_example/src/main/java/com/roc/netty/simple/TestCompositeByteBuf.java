package com.roc.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;

import static com.roc.netty.util.ByteBufUtil.log;

/**
 * @author pore
 * @date 2024年08月15日 11:18
 * @description todo 类描述
 */
public class TestCompositeByteBuf {
    public static void main(String[] args) {
        ByteBuf buffer1 = ByteBufAllocator.DEFAULT.buffer(5).writeBytes(new byte[]{1, 2, 3, 4, 5});
        ByteBuf buffer2 = ByteBufAllocator.DEFAULT.buffer(5).writeBytes(new byte[]{1, 2, 3, 4, 5});

        CompositeByteBuf byteBufs = ByteBufAllocator.DEFAULT.compositeBuffer();

        byteBufs.addComponents(true, buffer1, buffer2);
        log(byteBufs);
        System.out.println("修改原buffer");
        buffer1.setByte(1, 9);
        log(buffer1);
        log(byteBufs);
    }
}
