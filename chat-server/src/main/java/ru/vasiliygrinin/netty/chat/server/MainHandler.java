package ru.vasiliygrinin.netty.chat.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.vasiliygrinin.netty.chat.server.messags.RequestMessagePackage;
import ru.vasiliygrinin.netty.chat.server.messags.ResponseMessagePackage;
import ru.vasiliygrinin.netty.chat.server.votes.Vote;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;


public class MainHandler extends SimpleChannelInboundHandler<RequestMessagePackage> {

    public static final int ID_HANDLERS = 0;

    private CommandManager commandManager;

    public MainHandler(ConcurrentMap<String, List<Vote>> map) {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        commandManager = new CommandManager();
        System.out.println("Клиент подключился: " + ctx);


    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessagePackage message) throws Exception {

        System.out.println(message);

        if(message.getIdHandlers() != 0){
//            TODO
        }else{

            ctx.writeAndFlush(commandManager.doCommand(ctx, message));
        }


    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


}
