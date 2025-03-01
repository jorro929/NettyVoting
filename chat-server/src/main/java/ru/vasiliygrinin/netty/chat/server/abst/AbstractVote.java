package ru.vasiliygrinin.netty.chat.server.abst;

import java.util.List;
import java.util.Map;

public interface AbstractVote {

    boolean vote(String answer, String user);


    String getName();

    String getAbout();

    List<String> getListParticipant();

    List<String> getAnswers();

    Map<String, Integer> getResult();

    String getAuthors();
}
