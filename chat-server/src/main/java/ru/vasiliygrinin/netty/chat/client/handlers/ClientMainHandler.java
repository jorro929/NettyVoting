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

    public ClientMainHandler(Scanner scanner){
        this.scanner = scanner;
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        builder = new RequestMessagePackageBuilder();
        ctx.writeAndFlush(sendMessage(0));

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ResponseMessagePackage response) {
        if (response.getIdHandlers() == 400) {
            ctx.channel().close();
        }

        System.out.println(response.getMessage());

        ctx.writeAndFlush(sendMessage(response.getIdHandlers()));

    }

    private RequestMessagePackage sendMessage(int idHandlers){
        String text = "";
        RequestMessagePackage message = null;

        while (!builder.isComplete()) {

            System.out.println(scanner.hasNextLine());

            text = scanner.nextLine();

            try {
                message = builder.getRequestMessagePackage(idHandlers, text);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        builder.clear();

        return message;
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        System.out.println("session is over");
        scanner.close();
    }
}
