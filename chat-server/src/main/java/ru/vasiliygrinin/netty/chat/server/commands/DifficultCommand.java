package ru.vasiliygrinin.netty.chat.server.commands;

import io.netty.channel.ChannelHandlerContext;
import ru.vasiliygrinin.netty.chat.server.dao.VotesDirector;
import ru.vasiliygrinin.netty.chat.server.messags.RequestMessagePackage;
import ru.vasiliygrinin.netty.chat.server.messags.ResponseMessagePackage;

public abstract class DifficultCommand {

    protected int step = 1;
    protected int countSteps;

    protected VotesDirector votesDirector;

    public abstract ResponseMessagePackage doCommand(RequestMessagePackage message);

    protected void nextStep(){
        if(step == countSteps){
            step = 1;
        }
        step++;
    }
}
