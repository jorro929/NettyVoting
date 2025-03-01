package ru.vasiliygrinin.netty.chat.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vasiliygrinin.netty.chat.server.messags.Param;

public class ParamTest {

    @Test
    public void paramTestConstructor1(){
        Param param = new Param("t", "sander");
        Assertions.assertEquals("<t> = sander", param.toString());
    }

    @Test
    public void paramTestConstructor2() throws Exception {
        Param param = new Param("t=sander");
        Assertions.assertEquals("<t> = sander", param.toString());
    }

    @Test
    public void paramTestConstructor3() throws Exception {
        Assertions.assertThrows(Exception.class,() -> new Param("t=sander=dfh"));
    }
}
