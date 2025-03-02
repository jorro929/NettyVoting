package ru.vasiliygrinin.netty.chat.server;

import io.netty.channel.ChannelHandlerContext;
import ru.vasiliygrinin.netty.chat.server.commands.DifficultCommand;
import ru.vasiliygrinin.netty.chat.server.commands.CreateVoteCommand;
import ru.vasiliygrinin.netty.chat.server.commands.VoteCommand;
import ru.vasiliygrinin.netty.chat.server.dao.VotesDirector;
import ru.vasiliygrinin.netty.chat.server.messags.Param;
import ru.vasiliygrinin.netty.chat.server.messags.RequestMessagePackage;
import ru.vasiliygrinin.netty.chat.server.messags.ResponseMessagePackage;
import ru.vasiliygrinin.netty.chat.server.votes.Vote;

import java.util.*;


public class CommandManager {

    private static final Set<String> ACTIVE_USERS = new HashSet<>();

    private String userName;

    private final VotesDirector votesDirector;

    private DifficultCommand difficultCommand;

    public CommandManager(VotesDirector votesDirector) {
        this.votesDirector = votesDirector;
    }

    public ResponseMessagePackage doCommand(ChannelHandlerContext ctx, RequestMessagePackage message) {

        if (userName == null) {
            return registration(message);
        }

        if (message.getIdHandlers() == 0) {
            return simpleCommand(ctx, message);
        } else {
            return complexCommand(message);
        }
    }

    private ResponseMessagePackage simpleCommand(ChannelHandlerContext ctx, RequestMessagePackage message) {
        String userCommand = message.getCommandName();

        List<Param> params = message.getParams();


        switch (userCommand) {
            case "create topic":
                return createTopic(params);


            case "create vote":
                difficultCommand = new CreateVoteCommand(votesDirector, userName);
                return difficultCommand.doCommand(message);


            case "view":
                return view(params);


            case "vote":
                difficultCommand = new VoteCommand(votesDirector, userName);
                return difficultCommand.doCommand(message);


            case "delete":

                return delete(params);


            case "exit":
                ACTIVE_USERS.remove(userName);
                return new ResponseMessagePackage(400, "GoodBye!");


            case "login":
                return new ResponseMessagePackage(0, "You are login before");

            default:
                return new ResponseMessagePackage(0, "Sorry, but i don't know this command");

        }
    }

    private ResponseMessagePackage complexCommand(RequestMessagePackage message) {

        switch (message.getIdHandlers()) {
            case 1:
                return difficultCommand.doCommand(message);
            default:
                return new ResponseMessagePackage(0, "Sorry, but i don't know this command");
        }

    }


    private ResponseMessagePackage registration(RequestMessagePackage message) {

        if (message.getCommandName().equals("login") && message.getParams().size() == 1 && message.getParams().getFirst().getNameParam().equals("u")) {
            String login = message.getParams().getFirst().getValue();
            if (!ACTIVE_USERS.contains(login)) {
                ACTIVE_USERS.add(login);
                userName = message.getParams().getFirst().getValue();
                return new ResponseMessagePackage(0, "Welcome to Netty server, " + userName);
            }
            return new ResponseMessagePackage(0, "Sorry, but this name is already in use");

        }
        return new ResponseMessagePackage(0, "Sorry, we are log in! Use command <login -u=*username*> ");

    }


    private ResponseMessagePackage createTopic(List<Param> params) {
        if (params.size() != 1 || !params.getFirst().getNameParam().equals("t")) {
            System.out.println(params);
            return new ResponseMessagePackage(0, "Sorry, but i don't know create command with this params");
        }
        if (votesDirector.createTopic(params.getFirst().getValue())) {
            return new ResponseMessagePackage(0, "Topic " + params.getFirst().getValue() + " create successful!");
        } else {
            return new ResponseMessagePackage(0, "Sorry, there is already such a topic");
        }
    }

    private ResponseMessagePackage view(List<Param> params) {

        if (params.size() == 0) {
            return viewZeroParam();
        } else if (params.size() == 1) {
            return viewOneParam(params);
        } else if (params.size() == 2) {
            return viewTwoParam(params);
        }

        return new ResponseMessagePackage(0, "Sorry, but i don't know view command with this params");
    }

    private ResponseMessagePackage viewZeroParam() {
        String view = votesDirector.view().trim();
        if(view.isEmpty()) return new ResponseMessagePackage(0, "No one has voted yet");
        return new ResponseMessagePackage(0, view);
    }

    private ResponseMessagePackage viewOneParam(List<Param> params) {
        StringBuilder sb = new StringBuilder();

        Param tParam = params.stream().filter(param -> param.getNameParam().equals("t")).findFirst().get();
        if (Objects.isNull(tParam)) {
            return new ResponseMessagePackage(0, "Sorry, but i don't know view command with this params");
        }
        List<Vote> votes = votesDirector.view(tParam.getValue());
        if (Objects.isNull(votes)) return new ResponseMessagePackage(0, "There is no such topic");
        sb.append("Votes in " + tParam + ":\n");
        for (Vote vote : votes) {
            sb.append(vote.getName() + '\n');
        }
        return new ResponseMessagePackage(0, sb.toString());
    }

    private ResponseMessagePackage viewTwoParam(List<Param> params) {
        StringBuilder sb = new StringBuilder();

        Param tParam = params.stream().filter(param -> param.getNameParam().equals("t")).findFirst().get();

        Param vParam = params.stream().filter(param -> param.getNameParam().equals("v")).findFirst().get();

        if (Objects.isNull(tParam) || Objects.isNull(vParam)) {
            return new ResponseMessagePackage(0, "Sorry, but i don't know view command with this params");
        }

        Vote vote = votesDirector.view(tParam.getValue(), vParam.getValue());
        if (Objects.isNull(vote)) return new ResponseMessagePackage(0, "There is no such vote");

        sb.append("About votes: " + vote.getAbout() + "\n");

        for (Map.Entry<String, Integer> entry : vote.getResult().entrySet()) {
            sb.append(entry.getKey() + ". Count of those who chose: " + entry.getValue() + "\n");
        }

        return new ResponseMessagePackage(0, sb.toString());
    }


    private ResponseMessagePackage delete(List<Param> params) {

        Param tParam = params.stream().filter(param -> param.getNameParam().equals("t")).findFirst().get();

        Param vParam = params.stream().filter(param -> param.getNameParam().equals("v")).findFirst().get();

        if (Objects.isNull(tParam) || Objects.isNull(vParam)) {
            return new ResponseMessagePackage(0, "Sorry, but i don't know delete command with this params");
        }
        Vote vote = votesDirector.view(tParam.getValue(), vParam.getValue());

        if(!vote.getAuthor().equals(userName)) return new ResponseMessagePackage(0, "Sorry, but only author can delete vote");

        System.out.println(votesDirector.delete(tParam.getValue(), vote));
        return new ResponseMessagePackage(0, "The vote was deleted");
    }



}






