package ru.vasiliygrinin.netty.chat.server.votes;

import lombok.EqualsAndHashCode;
import lombok.ToString;


import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@EqualsAndHashCode(of = "name, owner")
@ToString
public final class Vote implements Serializable {
    @Serial
    private static final long serialVersionUID = 39485602811L;

    private final String name;

    private final String owner;

    private final String about;

    private final Map<String, Integer> answers;

    private Set<String> participants;

    public Vote(String name, String owner, String about, List<String> answers) {
        this.name = name;
        this.owner = owner;
        this.about = about;
        this.answers = new HashMap<>();
        participants = new HashSet<>();
        addAnswers(answers);
    }

    private void addAnswers(List<String> answers) {
        for (int i = 0; i < answers.size(); i++) {
            this.answers.put(answers.get(i), 0);
        }
    }



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


    public List<String> getListParticipant() {
        List<String> list = new ArrayList<>();
        list.addAll(participants);
        return list;
    }


    public List<String> getAnswers() {

        List<String> list = new ArrayList<>();
        for (Map.Entry<String, Integer> entry: answers.entrySet()){
            list.add(entry.getKey());
        }

        return list;
    }


    public Map<String, Integer> getResult() {
        Map<String, Integer> map = new HashMap<>();
        for (Map.Entry<String, Integer> entry: answers.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }


    public String getName() {
        return name;
    }

    public String getAuthor() {
        return owner;
    }


    public String getAbout() {
        return about;
    }
}
