package ru.vasiliygrinin.netty.chat.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.vasiliygrinin.netty.chat.server.dao.VotesDirector;
import ru.vasiliygrinin.netty.chat.server.messags.RequestMessagePackage;
import ru.vasiliygrinin.netty.chat.server.messags.ResponseMessagePackage;
import ru.vasiliygrinin.netty.chat.server.votes.Vote;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;


public class MainHandler extends SimpleChannelInboundHandler<RequestMessagePackage> {


    private CommandManager commandManager;
    private VotesDirector votesDirector;

    public MainHandler(VotesDirector votesDirector) {
        this.votesDirector = votesDirector;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        commandManager = new CommandManager(votesDirector);
        System.out.println("Клиент подключился: " + ctx);

    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessagePackage message) throws Exception {

        System.out.println(message);

        ResponseMessagePackage response = commandManager.doCommand(ctx, message);

        if(response.getIdHandlers() == 400){
            ctx.writeAndFlush(commandManager.doCommand(ctx, message));
            ctx.channel().close();
        }

        ctx.writeAndFlush(response);



    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


}
