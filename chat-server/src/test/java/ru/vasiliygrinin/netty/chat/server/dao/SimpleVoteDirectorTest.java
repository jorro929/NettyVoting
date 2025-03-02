package ru.vasiliygrinin.netty.chat.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vasiliygrinin.netty.chat.server.votes.Vote;
import ru.vasiliygrinin.netty.chat.server.votes.VoteBuilder;

import java.util.ArrayList;
import java.util.List;

public class SimpleVoteDirectorTest {

    @Test
    void view(){
        List<String> strings = new ArrayList<>();
        strings.add("yes");
        strings.add("no");

        Vote vote = new Vote("goga?", "owner", "about", strings);
        VotesDirector director = new SimpleVoteDirector();
        director.createTopic("toto");
        director.createVote("toto", "goga?", "owner", "about", strings);


        Assertions.assertEquals(true, director.view("toto", "goga?").equals(vote));
    }

    @Test
    void view2(){
        VoteBuilder voteBuilder = new VoteBuilder();
        voteBuilder.setTopic("toto");
        voteBuilder.setName("goga?");
        voteBuilder.setOwner("owner");
        voteBuilder.setAbout("about");
        voteBuilder.addAnswer("yes");
        voteBuilder.addAnswer("no");
        VotesDirector director = new SimpleVoteDirector();
        director.createTopic("toto");
        director.createVote(
                voteBuilder.getTopic(),
                voteBuilder.getName(),
                voteBuilder.getOwner(),
                voteBuilder.getAbout(),
                voteBuilder.getAnswers());

        Assertions.assertEquals(true, director.view("toto", "goga?").equals(voteBuilder.getVote()));
    }

}
