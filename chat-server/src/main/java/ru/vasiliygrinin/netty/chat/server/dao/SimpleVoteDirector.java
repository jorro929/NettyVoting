package ru.vasiliygrinin.netty.chat.server.dao;

import ru.vasiliygrinin.netty.chat.server.votes.Vote;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SimpleVoteDirector implements VotesDirector{

    ConcurrentMap<String, List<Vote>> topicToVoteMap;

    public SimpleVoteDirector(){
        topicToVoteMap = new ConcurrentHashMap<>();
    }


    @Override
    public String createTopic(String topic) {
        topicToVoteMap.putIfAbsent(topic, new ArrayList<Vote>());
        return topic;
    }


    @Override
    public Vote createVote(int id, String topic, String name, String author, String about, List<String> answers) {
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
    public Vote view(int id, String topic, String vote) {
        if(!contains(topic, vote))return null;

        return topicToVoteMap.get(topic).stream().filter(vote1 -> vote1.getName() == vote).findFirst().get();
    }

    @Override
    public Vote view(String topic, Vote vote) {
        return null;
    }

    @Override
    public void delete(String topic, String vote, String user) {

    }

    @Override
    public void delete(String topic, Vote vote, String user) {

    }

    private boolean contains(String topic, String name){

        if(!topicToVoteMap.containsKey(topic)) return false;
        for (Vote vote: topicToVoteMap.get(topic)){
            if(vote.getName().equals(name)) return true;
        }
        return false;
    }

    private boolean contains(String topic, Vote vote){

        if(!topicToVoteMap.containsKey(topic)) return false;
        for (Vote OldVote: topicToVoteMap.get(topic)){
            if(OldVote.equals(vote)) return true;
        }
        return false;
    }
}
