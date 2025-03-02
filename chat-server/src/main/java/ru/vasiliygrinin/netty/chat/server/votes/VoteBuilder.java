package ru.vasiliygrinin.netty.chat.server.votes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class VoteBuilder {

    private String name;

    private String owner;

    private String topic;

    private String about;

    private List<String> answers;

    private int countAnswer;

    public VoteBuilder() {
        answers = new ArrayList<>();
    }

    public boolean addAnswer(String answer){
        if(!answers.contains(answer)){
            answers.add(answer);
            return true;
        }
        return false;
    }

    public void clear(){
        name=null;
        owner=null;
        about=null;
        answers.clear();
    }

    public Vote getVote(){
        return new Vote(name,owner,about, answers);
    }
}
