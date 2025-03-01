package ru.vasiliygrinin.netty.chat.server.coders;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import ru.vasiliygrinin.netty.chat.server.messags.ResponseMessagePackage;

import java.nio.charset.Charset;

public class ResponseMsgPckEncoder extends MessageToByteEncoder<ResponseMessagePackage> {

    private final Charset charset = Charset.forName("UTF-8");


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext,
                          ResponseMessagePackage response, ByteBuf byteBuf) throws Exception {

        byteBuf.writeInt(response.getIdHandlers());

        int bodyLen = response.getMessage().length();
        byteBuf.writeInt(bodyLen);
        byteBuf.writeCharSequence(response.getMessage(), charset);
    }
}
