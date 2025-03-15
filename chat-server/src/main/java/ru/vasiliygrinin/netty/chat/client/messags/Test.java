package ru.vasiliygrinin.netty.chat.client.messags;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) throws Exception {
        RequestMessagePackage messagePackage = new RequestMessagePackage();

        messagePackage.setCommandName("create");
        messagePackage.addParam(new Param("t=sander"));
        StringBuilder sb = new StringBuilder();

        sb.append(messagePackage.getCommandName() + ";");

        for (Param param: messagePackage.getParams()) {
            sb.append(param.getNameParam() + "=" + param.getValue() + ";");
        }

        String bodyRequest = sb.toString();

        String[] request = bodyRequest.split(";");
        RequestMessagePackage msg = new RequestMessagePackage();


        for (int i = 0; i < request.length; i++) {
            if(i == 0){
                msg.setCommandName(request[i]);
            }else{
                msg.addParam(new Param(request[i]));
            }
        }
        System.out.println(msg);
    }
}
