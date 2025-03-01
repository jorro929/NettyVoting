package ru.vasiliygrinin.netty.chat.server.dao;

import java.io.File;

public interface DAOManager {

    void save(File file);

    VotesDirector load(File file);

}
