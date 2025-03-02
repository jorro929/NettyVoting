package ru.vasiliygrinin.netty.chat.client.handlers;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.vasiliygrinin.netty.chat.client.messags.RequestMessagePackage;
import ru.vasiliygrinin.netty.chat.client.messags.RequestMessagePackageBuilder;
import ru.vasiliygrinin.netty.chat.client.messags.ResponseMessagePackage;

import java.util.Scanner;

public class ClientMainHandler extends SimpleChannelInboundHandler<ResponseMessagePackage> {

    private RequestMessagePackageBuilder builder;

    private Scanner scanner;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        builder = new RequestMessagePackageBuilder();
        scanner = new Scanner(System.in);

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ResponseMessagePackage response) {
        if (response.getIdHandlers() == 400) {
            ctx.channel().close();
        }

        System.out.println(response.getMessage());
        String text;
        RequestMessagePackage message = null;

        while (!builder.isComplete()) {
            text = scanner.nextLine();
            try {
                message = builder.getRequestMessagePackage(response.getIdHandlers(), text);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        builder.clear();
        ctx.writeAndFlush(message);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        scanner.close();
    }
}
