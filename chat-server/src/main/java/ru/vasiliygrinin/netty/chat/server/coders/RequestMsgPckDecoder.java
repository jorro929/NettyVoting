package ru.vasiliygrinin.netty.chat.server.coders;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import ru.vasiliygrinin.netty.chat.server.messags.Param;
import ru.vasiliygrinin.netty.chat.server.messags.RequestMessagePackage;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class RequestMsgPckDecoder extends ReplayingDecoder<RequestMessagePackage> {

    private final Charset charset = Charset.forName("UTF-8");

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {

        RequestMessagePackage msg = new RequestMessagePackage();
        msg.setIdHandlers(in.readInt());
        int bodyLen = in.readInt();

        String bodyRequest = in.readCharSequence(bodyLen, charset).toString();

        String[] request = bodyRequest.split(";");

        for (int i = 0; i < request.length; i++) {
            if(i == 0){
                msg.setCommandName(request[i]);
            }else{
                msg.addParam(new Param(request[i]));
            }
        }
        out.add(msg);


    }
}
