package ru.vasiliygrinin.netty.chat.server.dao;

import ru.vasiliygrinin.netty.chat.server.votes.Vote;

import java.util.List;

public interface VotesDirector {

    boolean createTopic(String topic);


    Vote createVote(String topic, String name, String author, String about, List<String> answers);

    String view();

    List<Vote> view(String topic);

    Vote view(String topic, String vote);

    Vote view(String topic, Vote vote);

    boolean delete(String topic, String vote);

    boolean delete(String topic, Vote vote);

    boolean containsTopic(String topic);

}
