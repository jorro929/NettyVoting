package ru.vasiliygrinin.netty.chat.server.dao;

import org.w3c.dom.ls.LSInput;
import ru.vasiliygrinin.netty.chat.server.votes.Vote;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class SimpleVoteDirector implements VotesDirector, Serializable {

    @Serial
    private static final long serialVersionUID = 3234634602811L;

    ConcurrentMap<String, List<Vote>> topicToVoteMap;

    public SimpleVoteDirector(){
        topicToVoteMap = new ConcurrentHashMap<>();
    }


    @Override
    public boolean createTopic(String topic) {
        if(topicToVoteMap.containsKey(topic)) return false;
        topicToVoteMap.putIfAbsent(topic, new ArrayList<Vote>());

        return true;
    }


    @Override
    public Vote createVote(String topic, String name, String author, String about, List<String> answers) {
        if(!topicToVoteMap.containsKey(topic)) return null;
        for (Vote oldVote: topicToVoteMap.get(topic)){
            if(oldVote.getName().equals(name)) return null;
        }

        Vote vote = new Vote(name, author, about, answers);
        topicToVoteMap.get(topic).add(vote);
        return vote;
    }

    @Override
    public String view() {
        StringBuilder builder = new StringBuilder();
        for(ConcurrentMap.Entry<String, List<Vote>> entry: topicToVoteMap.entrySet()){
            builder.append("<" + entry.getKey() + "(votes in topic = <" + entry.getValue().size()+ ">)>\n");
        }
        return builder.toString();
    }

    @Override
    public List<Vote> view(String topic) {
        if(!topicToVoteMap.containsKey(topic))return null;
        List <Vote> votes = new ArrayList<>();
        for (Vote vote: topicToVoteMap.get(topic)){
            votes.add(vote);
        }
        return votes;
    }

    @Override
    public Vote view(String topic, String vote) {
        if(!containsVote(topic, vote))return null;

        return topicToVoteMap.get(topic).stream().filter(vote1 -> vote1.getName().equals(vote)).findFirst().get();
    }

    @Override
    public Vote view(String topic, Vote vote) {

        if(!containsVote(topic, vote))return null;

        return topicToVoteMap.get(topic).get(topicToVoteMap.get(topic).indexOf(vote));

    }

    @Override
    public boolean delete(String topic, String vote) {
        if(!containsVote(topic, vote)) return false;

        List<Vote> votes = topicToVoteMap.get(topic);

        Vote vote1 = votes.stream().filter(entryVote -> entryVote.getName().equals(vote)).findFirst().get();
        votes.remove(vote1);
        return true;

    }

    @Override
    public boolean delete(String topic, Vote vote) {
        if(!containsVote(topic, vote)) return false;
        topicToVoteMap.get(topic).remove(vote);
        return true;

    }

    @Override
    public boolean containsTopic(String topic) {
        return topicToVoteMap.containsKey(topic);
    }

    private boolean containsVote(String topic, String name){

        if(!topicToVoteMap.containsKey(topic)) return false;
        for (Vote vote: topicToVoteMap.get(topic)){
            if(vote.getName().equals(name)) return true;
        }
        return false;
    }

    private boolean containsVote(String topic, Vote vote){

        if(!topicToVoteMap.containsKey(topic)) return false;
        for (Vote OldVote: topicToVoteMap.get(topic)){
            if(OldVote.equals(vote)) return true;
        }
        return false;
    }
}
