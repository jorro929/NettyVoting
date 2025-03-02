package ru.vasiliygrinin.netty.chat.server.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface DAOManager {

    void save(File file) throws IOException;

    VotesDirector create();

    VotesDirector load(File file) throws IOException, ClassNotFoundException;

}
