package ru.vasiliygrinin.netty.chat.server;

import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.Setter;
import ru.vasiliygrinin.netty.chat.server.commands.*;
import ru.vasiliygrinin.netty.chat.server.dao.VotesDirector;
import ru.vasiliygrinin.netty.chat.server.messags.RequestMessagePackage;
import ru.vasiliygrinin.netty.chat.server.messags.ResponseMessagePackage;

import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
public class CommandManager {

    public static final int ID_HANDLERS = 1;

    private static final Set<String> ACTIVE_USERS = new HashSet<>();

    private String userName;

    private VotesDirector votesDirector;

    public CommandManager(VotesDirector votesDirector){
        this.votesDirector = votesDirector;
    }

    public ResponseMessagePackage doCommand(ChannelHandlerContext ctx, RequestMessagePackage message) {

        if (userName == null) {
            return registration(message);
        }

        if (message.getIdHandlers() == 0) {
            return newCommand(ctx, message);
        } else {
            return new ResponseMessagePackage(0, "I make it");
        }
    }

    private ResponseMessagePackage newCommand(ChannelHandlerContext ctx, RequestMessagePackage message) {
        String userCommand = message.getCommandName();

        switch (userCommand) {
            case "create topic":
                votesDirector.createTopic(message.getParams().getFirst().getValue());
//                break;

            case "create vote":
                return new ResponseMessagePackage(0, "I make it");
//                break;

            case "view":
                view();
//                break;

            case "vote":
                return new ResponseMessagePackage(0, "I make it");
//                break;

            case "delete":
                return new ResponseMessagePackage(0, "I make it");
//                break;

            case "exit":
                return new ResponseMessagePackage(0, "I make it");
//                break;
            case "login":
                return new ResponseMessagePackage(0, "You are login before");
//                break;
            default:
                return new ResponseMessagePackage(0, "Sorry, but i don't know this command");

        }
    }

    private ResponseMessagePackage registration(RequestMessagePackage message) {

        if (message.getCommandName().equals("login") && message.getParams().size() == 1 && message.getParams().getFirst().getNameParam().equals("u")) {

            userName = message.getParams().getFirst().getValue();

            if (!ACTIVE_USERS.contains(userName)) {
                ACTIVE_USERS.add(userName);
                return new ResponseMessagePackage(0, "Welcome to Netty server, " + userName);
            }

        }
        return new ResponseMessagePackage(0, "Sorry, we are log in! Use command <login -u=*username*> ");

    }

    private ResponseMessagePackage view() {


        return null;
    }
}






