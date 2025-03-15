package ru.vasiliygrinin.netty.chat.client.coders;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import ru.vasiliygrinin.netty.chat.client.messags.RequestMessagePackage;
import ru.vasiliygrinin.netty.chat.client.messags.Param;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Arrays;

public class RequestMsgPckEncoder extends MessageToByteEncoder<RequestMessagePackage> {

    private final Charset charset = Charset.forName("UTF-8");

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RequestMessagePackage messagePackage, ByteBuf byteBuf) throws Exception {

        byteBuf.writeInt(messagePackage.getIdHandlers());

        String bodyRequest = makeBodyRequest(messagePackage);
        byteBuf.writeInt(bodyRequest.length());
        byteBuf.writeCharSequence(bodyRequest, charset);

    }

    private String makeBodyRequest(RequestMessagePackage msg){
        StringBuilder sb = new StringBuilder();

        sb.append(msg.getCommandName() + ";");

        for (Param param: msg.getParams()) {
            sb.append(param.getNameParam() + "=" + param.getValue() + ";");
        }
        return  sb.toString();
    }
}
