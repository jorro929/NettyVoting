package ru.vasiliygrinin.netty.chat.client.message;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vasiliygrinin.netty.chat.client.messags.Param;
import ru.vasiliygrinin.netty.chat.client.messags.RequestMessagePackage;
import ru.vasiliygrinin.netty.chat.client.messags.RequestMessagePackageBuilder;

public class MsgBuilderText{

    @Test
    void MsgBuilderTest0() throws Exception {
        RequestMessagePackage msg = new RequestMessagePackage(0, "create");


        String str = "create";
        RequestMessagePackageBuilder builder = new RequestMessagePackageBuilder(0, str);
        Assertions.assertEquals(msg, builder.getRequestMessagePackage());
    }

    @Test
    void MsgBuilderTest1() throws Exception {
        RequestMessagePackage msg = new RequestMessagePackage(0, "create");
        msg.addParam(new Param("t", "sander"));

        String str = "create -t=sander";
        RequestMessagePackageBuilder builder = new RequestMessagePackageBuilder(0, str);
        Assertions.assertEquals(msg, builder.getRequestMessagePackage());
    }

    @Test
    void MsgBuilderTest2() throws Exception {
        RequestMessagePackage msg = new RequestMessagePackage(0, "create");
        msg.addParam(new Param("t", "sander"));
        msg.addParam(new Param("v", "all"));

        String str = "create -t=sander -v=all";
        RequestMessagePackageBuilder builder = new RequestMessagePackageBuilder(0, str);
        Assertions.assertEquals(msg, builder.getRequestMessagePackage());
    }
    @Test
    void MsgBuilderTestExcep() throws Exception {
        String str = " -s=momo";
        Assertions.assertThrows(Exception.class,() -> new RequestMessagePackageBuilder(0, str));
    }

    @Test
    void MsgBuilderTestMethodGet() throws Exception {

        RequestMessagePackage msg = new RequestMessagePackage(0, "create");
        msg.addParam(new Param("t", "sander"));
        msg.addParam(new Param("v", "all"));
        String str = "create -t=sander -v=all";

        RequestMessagePackageBuilder builder = new RequestMessagePackageBuilder();
        Assertions.assertEquals(msg, builder.getRequestMessagePackage(0,str));
    }
}
