package ru.vasiliygrinin.netty.chat.server.messags;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



@EqualsAndHashCode
@ToString
public class RequestMessagePackage implements Serializable {

    @Serial
    private static final long serialVersionUID = 123456789L;

    @Setter
    @Getter
    private int idHandlers;

    @Setter
    @Getter
    private String commandName;

    private final List<Param> params;

    public RequestMessagePackage() {
        params = new ArrayList<>();
    }

    public RequestMessagePackage(int idHandlers, String commandName) {
        this.idHandlers = idHandlers;
        this.commandName = commandName;
        params = new ArrayList<>();
    }

    public boolean addParam(Param param) {
        if (params.contains(param)) {
            return false;
        }
        params.add(param);
        return true;
    }

    public void removeParam(Param param) {
        params.remove(param);
    }

    public boolean containsParam(Param param) {
        return params.contains(param);
    }

    public List<Param> getParams() {
        List<Param> params = new ArrayList<>();
        params.addAll(this.params);
        return params;
    }
}
