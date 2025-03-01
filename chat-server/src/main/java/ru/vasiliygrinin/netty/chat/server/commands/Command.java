package ru.vasiliygrinin.netty.chat.server.commands;

import io.netty.channel.ChannelHandlerContext;
import ru.vasiliygrinin.netty.chat.server.messags.RequestMessagePackage;
import ru.vasiliygrinin.netty.chat.server.messags.ResponseMessagePackage;

public interface Command {

    ResponseMessagePackage doCommand(ChannelHandlerContext ctx, RequestMessagePackage message);

}
