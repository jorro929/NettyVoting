package ru.vasiliygrinin.netty.chat.server;

import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
public class RequestHandler {

    private String clientName;
    private static int newClientIndex = 1;
    private boolean isLogin;

    public RequestHandler(){

        clientName = "Client: " + newClientIndex;
        newClientIndex++;
        isLogin = false;

    }




    public String processTheCommand(String s){

        if(!isLogin){
            if(s.startsWith("login -u=") && s.length() > 9){
                login(s);
                isLogin = true;
                return "connect is successful";
            }else{
                return "Sorry, but you must log in";
            }
        }
        Command s1 = Command.CREATE_TOPIC;
        switch (s1){
            case CREATE_TOPIC:
        }


        String out = String.format("[%s]: %s", clientName, s);
        System.out.println(out);
        return out;
    }


    private void login(String s){
        clientName =s.substring(9);
    }

    private void createTopic(){

    }
}
