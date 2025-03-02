package ru.vasiliygrinin.netty.chat.server.commands;

import io.netty.channel.ChannelHandlerContext;
import ru.vasiliygrinin.netty.chat.server.dao.VotesDirector;
import ru.vasiliygrinin.netty.chat.server.messags.Param;
import ru.vasiliygrinin.netty.chat.server.messags.RequestMessagePackage;
import ru.vasiliygrinin.netty.chat.server.messags.ResponseMessagePackage;
import ru.vasiliygrinin.netty.chat.server.votes.Vote;
import ru.vasiliygrinin.netty.chat.server.votes.VoteBuilder;

import java.util.List;

public class CreateVoteCommand extends DifficultCommand {


    private final VoteBuilder voteBuilder;

    private final String userName;


    public CreateVoteCommand(VotesDirector votesDirector, String userName) {
        step = 1;
        countSteps = 5;
        this.votesDirector = votesDirector;
        this.userName = userName;
        voteBuilder = new VoteBuilder();
    }

    @Override
    public ResponseMessagePackage doCommand(RequestMessagePackage message) {

        ResponseMessagePackage response = null;
        switch (step) {
            case 1:
                response = createVoteStep1(message.getParams(), userName);
                break;
            case 2:
                response = createVoteStep2(message.getCommandName());
                break;
            case 3:
                response = createVoteStep3(message.getCommandName());
                break;
            case 4:
                response = createVoteStep4(message.getCommandName());
                break;
            case 5:
                response = createVoteStep5(message.getCommandName());
                break;
        }
        return response;
    }

    private ResponseMessagePackage createVoteStep1(List<Param> params, String userName) {
        if (params.size() != 1 || !params.getFirst().getNameParam().equals("t")) {
            System.out.println(params);
            return new ResponseMessagePackage(0, "Sorry, but i don't know create command with this params");
        }
        Param tparam = params.getFirst();
        voteBuilder.clear();
        if (votesDirector.containsTopic(tparam.getValue())) {
            voteBuilder.setTopic(tparam.getValue());
            voteBuilder.setOwner(userName);
            nextStep();
            return new ResponseMessagePackage(1, "please, get name to vote");

        } else {
            return new ResponseMessagePackage(0, "Sorry, but topic: " + tparam.getValue() + " was not create!");
        }


    }

    private ResponseMessagePackage createVoteStep2(String message) {


        for (Vote createdVote : votesDirector.view(voteBuilder.getTopic())) {
            if(createdVote.getName().equals(message)){
                return new ResponseMessagePackage(1, "Sorry, but vote: " + message +
                        " was created earlier in the topic( " + voteBuilder.getTopic() + ") please, repeat!");
            }

        }
        voteBuilder.setName(message);
        nextStep();
        return new ResponseMessagePackage( 1, "please, get description to vote");


    }

    private ResponseMessagePackage createVoteStep3(String message) {

        voteBuilder.setAbout(message);
        nextStep();
        return new ResponseMessagePackage(1, "please, get count answers to vote");


    }

    private ResponseMessagePackage createVoteStep4(String message) {

        for (char c : message.toCharArray()) {
            if (!Character.isDigit(c)) return new ResponseMessagePackage(1, "please, get number");
        }
        voteBuilder.setCountAnswer(Integer.parseInt(message));
        System.out.println(voteBuilder.getCountAnswer());
        nextStep();
        return new ResponseMessagePackage(1, "please, get answer");

    }

    private ResponseMessagePackage createVoteStep5(String message) {

        if (voteBuilder.getAnswers().contains(message)) {
            return new ResponseMessagePackage(1, "please, get other answer");
        }
        voteBuilder.addAnswer(message);
        System.out.println(voteBuilder.getCountAnswer());
        System.out.println(voteBuilder.getAnswers().size());
        if (voteBuilder.getAnswers().size() == voteBuilder.getCountAnswer()) {
            votesDirector.createVote(
                    voteBuilder.getTopic(),
                    voteBuilder.getName(),
                    voteBuilder.getOwner(),
                    voteBuilder.getAbout(),
                    voteBuilder.getAnswers());
            nextStep();
            return new ResponseMessagePackage(0, "Vote is create");
        }
        return new ResponseMessagePackage(1, "please, get next answer");

    }
}
