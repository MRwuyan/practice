package com.roc.asm.protocol;

import com.roc.message.LoginRequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

public class TestMessageCodec {

    public static void main(String[] args) throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0),
                new LoggingHandler(),
                new MessageCodecSharable()
        );
        //encode
        LoginRequestMessage loginRequestMessage = new LoginRequestMessage("zhangshan", "123", "张三");
        channel.writeOutbound(loginRequestMessage);
        //decode
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null,loginRequestMessage,buf);

        channel.writeInbound(buf);


    }
}
