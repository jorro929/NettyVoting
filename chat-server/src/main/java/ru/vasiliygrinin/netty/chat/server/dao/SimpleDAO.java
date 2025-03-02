package ru.vasiliygrinin.netty.chat.server.dao;

import java.io.*;
import java.util.Objects;

public class SimpleDAO implements DAOManager {

    private VotesDirector votesDirector;


    @Override
    public void save(File file) throws IOException {

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));) {
            outputStream.writeObject(votesDirector);
        }

    }

    @Override
    public VotesDirector create() {
        votesDirector = new SimpleVoteDirector();
        return votesDirector;
    }

    @Override
    public VotesDirector load(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));) {
            votesDirector = (VotesDirector) inputStream.readObject();
        }
        return votesDirector;
    }
}
