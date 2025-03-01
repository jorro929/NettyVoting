package ru.vasiliygrinin.netty.chat.server.messags;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.text.Format;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public final class Param {

    private final String nameParam;

    private final String value;


    public Param(String param) throws Exception {
        if(!param.contains("=") ||
                param.substring(param.indexOf("=") + 1).contains( "=")){
            throw new Exception("the format does not match the format <param>=<value>");
        }else{
            String[] strings = param.split("=");
            nameParam = strings[0];
            value = strings[1];
        }
    }

    @Override
    public String toString() {
        return String.format("<%s> = %s", nameParam, value);
    }
}
