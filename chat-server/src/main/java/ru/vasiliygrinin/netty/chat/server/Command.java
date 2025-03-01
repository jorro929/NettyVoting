package ru.vasiliygrinin.netty.chat.server;

public enum Command {

    CREATE_TOPIC("create topic"),
    CREATE_VOTE("create command"),
    VIEW("view"),
    VOTE("vote"),
    DELETE("delete"),
    EXIT("exit");

    private String command;

    Command(String command) {
        this.command = command;
    }


    @Override
    public String toString() {
        return command;
    }
}
