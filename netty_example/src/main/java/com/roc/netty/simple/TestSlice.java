package com.roc.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import static com.roc.netty.util.ByteBufUtil.log;

/**
 * @author pore
 * @date 2024年08月15日 10:58
 * @description todo 类描述
 */
public class TestSlice {

    public static void main(String[] args)
    {
        String str = "0123456789";
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(10);

        buffer.writeBytes(str.getBytes());
        log(buffer);

        ByteBuf slice1 = buffer.slice(0, 5);
        ByteBuf slice2 = buffer.slice(5, 1);


        slice1.retain();
        slice2.retain();
        System.out.println("=====================yuan==release");
//        buffer.release();

        log(slice1);
        log(slice2);
        System.out.println("====================切片===release");
        slice1.release();
        slice2.release();
        log(buffer);
    }
}
