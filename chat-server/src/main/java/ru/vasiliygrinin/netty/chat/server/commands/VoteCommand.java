package ru.vasiliygrinin.netty.chat.server.commands;

import ru.vasiliygrinin.netty.chat.server.dao.VotesDirector;
import ru.vasiliygrinin.netty.chat.server.messags.Param;
import ru.vasiliygrinin.netty.chat.server.messags.RequestMessagePackage;
import ru.vasiliygrinin.netty.chat.server.messags.ResponseMessagePackage;
import ru.vasiliygrinin.netty.chat.server.votes.Vote;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class VoteCommand extends DifficultCommand{

    private String userName;

    private Vote vote;

    public VoteCommand(VotesDirector votesDirector, String userName){
        this.votesDirector = votesDirector;
        this.userName = userName;
        countSteps = 2;
    }
    @Override
    public ResponseMessagePackage doCommand(RequestMessagePackage message) {
        ResponseMessagePackage response = null;
        switch (step) {
            case 1:
                response = voteStep1(message.getParams(), userName);
                break;
            case 2:
                response = voteStep2(message.getCommandName());
                break;

        }
        return response;
    }


    private ResponseMessagePackage voteStep1(List<Param> params, String userName){
        if (params.size() != 2 ) {
            return new ResponseMessagePackage(0, "Sorry, but i don't know vote command with this params");
        }
        Param tParam = params.stream().filter(param -> param.getNameParam().equals("t")).findFirst().get();

        Param vParam = params.stream().filter(param -> param.getNameParam().equals("v")).findFirst().get();

        if (Objects.isNull(tParam) || Objects.isNull(vParam)) {
            return new ResponseMessagePackage(0, "Sorry, but i don't know vote command with this params");
        }
        vote = votesDirector.view(tParam.getValue(), vParam.getValue());
        if (vote.getListParticipant().contains(userName)) {
            return new ResponseMessagePackage(0, "Sorry, but you can only vote in one vote once.");
        }

        StringBuilder sb = new StringBuilder();

        if (Objects.isNull(vote)) return new ResponseMessagePackage(0, "There is no such vote");

        sb.append("About votes: " + vote.getAbout() + "\n");


        for (Map.Entry<String, Integer> entry : vote.getResult().entrySet()) {
            sb.append(entry.getKey() + ". Count of those who chose: " + entry.getValue() + "\n");
        }

        nextStep();
        return new ResponseMessagePackage(1, sb.toString());
    }


    private ResponseMessagePackage voteStep2(String commandName) {

        if(!vote.getAnswers().contains(commandName)) {
            return new ResponseMessagePackage(1, "Sorry, but there is not your answer. Please, repeat");
        }

        vote.vote(commandName, userName);
        return new ResponseMessagePackage(0, "You voted successfully!");
    }
}
