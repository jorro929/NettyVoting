package ru.vasiliygrinin.netty.chat.server.votes;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import ru.vasiliygrinin.netty.chat.server.abst.AbstractVote;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@EqualsAndHashCode(of = "name, owner")
public class Vote implements AbstractVote, Serializable {
    @Serial
    private static final long serialVersionUID = 39485602811L;

    private final String name;

    private final String owner;

    private final String about;

    private Map<String, Integer> answers;

    private Set<String> participants;

    public Vote(String name, String owner, String about, List<String> answers) {
        this.name = name;
        this.owner = owner;
        this.about = about;
        this.answers = new HashMap<>();
        addAnswers(answers);
    }

    private void addAnswers(List<String> answers) {
        for (int i = 0; i < answers.size(); i++) {
            this.answers.put(answers.get(i), 0);
        }
    }


    @Override
    public boolean vote(String answer, String user) {
        if (participants.contains(user)){
            return false;
        } else if (!answers.containsKey(answer)) {
            return false;
        }else {
            answers.put(answer, answers.get(answer) + 1);
            participants.add(user);
            return true;
        }
    }

    @Override
    public List<String> getListParticipant() {
        List<String> list = new ArrayList<>();
        list.addAll(participants);
        return list;
    }

    @Override
    public List<String> getAnswers() {

        List<String> list = new ArrayList<>();
        for (Map.Entry<String, Integer> entry: answers.entrySet()){
            list.add(entry.getKey());
        }

        return list;
    }

    @Override
    public Map<String, Integer> getResult() {
        Map<String, Integer> map = new HashMap<>();
        for (Map.Entry<String, Integer> entry: answers.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getAuthors() {
        return owner;
    }

    @Override
    public String getAbout() {
        return about;
    }
}
