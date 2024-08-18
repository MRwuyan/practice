package com.roc.asm.protocol;


import com.roc.asm.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class MessageCodecSharable extends MessageToMessageCodec<ByteBuf, Message> {


    @Override
    protected void encode(ChannelHandlerContext ctx, Message message, List<Object> list) throws Exception {
        ByteBuf byteBuf = ctx.alloc().buffer();
        //1.4字节的魔数
        byteBuf.writeBytes(new byte[]{1, 2, 3, 4});
        //2.1字节的版本
        byteBuf.writeByte(1);
        //3.1字节的序列化方式jdk0,json 1
        byteBuf.writeByte(0);
        //4.1字节的指令类型
        byteBuf.writeByte(message.getMessageType());
        //5.4个字节序列
        byteBuf.writeInt(message.getSequenceId());
        //无意义对齐填充
        byteBuf.writeByte(0xff);
        //6.消息长度
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(message);
        byte[] byteArray = bos.toByteArray();
        int length = byteArray.length;
        byteBuf.writeInt(length);
        //7.后去内容的字节数组
        byteBuf.writeBytes(byteArray);
        list.add(byteBuf);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int magicNum = byteBuf.readInt();
        byte version = byteBuf.readByte();
        byte serializerType = byteBuf.readByte();
        byte messageType = byteBuf.readByte();
        int sequenceId = byteBuf.readInt();
        byte b = byteBuf.readByte();
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes, 0, length);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
        Message message = (Message) ois.readObject();
        log.info("{},{},{},{},{},{}",magicNum,version,serializerType,messageType,sequenceId,length);
        log.info("{}",message);
        list.add(message);
    }
}
