package ru.vasiliygrinin.netty.chat.client.coders;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import ru.vasiliygrinin.netty.chat.client.messags.ResponseMessagePackage;

import java.nio.charset.Charset;
import java.util.List;

public class ResponseMsgPckDecoder extends ReplayingDecoder<ResponseMessagePackage> {

    private final Charset charset = Charset.forName("UTF-8");

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> out) throws Exception {

        ResponseMessagePackage response = new ResponseMessagePackage();
//        System.out.println("10");
        response.setIdHandlers(byteBuf.readInt());
//        System.out.println("20: " + response.getIdHandlers());
        int bodyLen = byteBuf.readInt();
//        System.out.println("30: " + bodyLen);
//        String str = byteBuf.readCharSequence(bodyLen, charset).toString();
//        System.out.println("40: " + str);
        response.setMessage(byteBuf.readCharSequence(bodyLen, charset).toString());
//        System.out.println("50: " + response);
        out.add(response);

    }
}
