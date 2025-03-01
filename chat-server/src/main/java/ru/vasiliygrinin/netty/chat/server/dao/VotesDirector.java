package ru.vasiliygrinin.netty.chat.server.dao;

import ru.vasiliygrinin.netty.chat.server.votes.Vote;

import java.util.List;
import java.util.Map;

public interface VotesDirector {

    String createTopic(String topic);


    Vote createVote(int id, String topic, String name, String author, String about, List<String> answers);

    String view();

    List<Vote> view(String topic);

    Vote view(int id, String topic, String vote);

    Vote view(String topic, Vote vote);

    void delete(String topic, String vote, String user);

    void delete(String topic, Vote vote, String user);

}
