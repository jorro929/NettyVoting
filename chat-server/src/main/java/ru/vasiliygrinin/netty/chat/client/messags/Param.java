package ru.vasiliygrinin.netty.chat.client.messags;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

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
            nameParam = strings[0].trim();
            value = strings[1].trim();
        }
    }

    @Override
    public String toString() {
        return String.format("<%s> = %s", nameParam, value);
    }
}
