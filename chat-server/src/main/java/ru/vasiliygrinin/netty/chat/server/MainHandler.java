package ru.vasiliygrinin.netty.chat.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.vasiliygrinin.netty.chat.server.messags.RequestMessagePackage;
import ru.vasiliygrinin.netty.chat.server.messags.ResponseMessagePackage;

import java.util.ArrayList;
import java.util.List;


public class MainHandler extends SimpleChannelInboundHandler<RequestMessagePackage> {

    private static final List<Channel> channels = new ArrayList<>();
    public static final int ID_HANDLERS = 0;

    private RequestHandler requestHandler;



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

//        requestHandler = new RequestHandler();
        System.out.println("Клиент подключился: " + ctx);
//        System.out.println("Клинету присвоено имя: " + requestHandler.getClientName());
        channels.add(ctx.channel());

    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessagePackage requestMessagePackage) throws Exception {

        System.out.println(requestMessagePackage);



        ctx.writeAndFlush(new ResponseMessagePackage(ID_HANDLERS, "please, get me command!"));
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


}
