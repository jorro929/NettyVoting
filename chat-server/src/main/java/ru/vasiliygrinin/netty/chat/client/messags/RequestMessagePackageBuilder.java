package ru.vasiliygrinin.netty.chat.client.messags;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RequestMessagePackageBuilder {

    private RequestMessagePackage msg;

    public RequestMessagePackageBuilder(int idHandlers, String text) throws Exception {
        msg = new RequestMessagePackage();
        msg.setIdHandlers(idHandlers);
        textToMsg(text.trim());

    }

    public RequestMessagePackageBuilder() {
        msg = new RequestMessagePackage();
    }

    public void addIdHandlers(int idHandlers) {
        msg.setIdHandlers(idHandlers);
    }

    public void addCommand(String command) {
        msg.setCommandName(command);
    }

    public void addParam(Param param) {
        msg.addParam(param);
    }


    public List<Param> getParam() {
        List<Param> params = new ArrayList<>();
        for (Param param : msg.getParams()) {
            params.add(param);
        }
        return params;
    }

    public RequestMessagePackage getRequestMessagePackage() throws Exception {
        if (isComplete()) {
            RequestMessagePackage msg1 = new RequestMessagePackage();
            msg1.setIdHandlers(msg.getIdHandlers());
            msg1.setCommandName(msg.getCommandName());
            for (Param param : getParam()) {
                msg1.addParam(param);
            }
            msg1.setCommandName(msg1.getCommandName());
            return msg1;
        }

        throw new Exception("Message is not complete");

    }
    public RequestMessagePackage getRequestMessagePackage(int idHandler, String text) throws Exception {

        clear();
        msg.setIdHandlers(idHandler);
        textToMsg(text);
        return getRequestMessagePackage();
    }



    public void clear(){
        msg.setCommandName(null);
        msg.setIdHandlers(0);
        for (Param param: msg.getParams()){
            msg.removeParam(param);
        }
    }


    private void textToMsg(String text) throws Exception {


        if(text.startsWith("-")){
            throw new Exception("The format massage does not match the format <command> -<param>=<value>");
        }


        String[] strings = text.split(" -");

        for (int i = 0; i < strings.length; i++) {
            if (i == 0) {
                msg.setCommandName(strings[i]);
            } else {
                msg.addParam(new Param(strings[i]));
            }
        }


    }


    public boolean isComplete() {
        return !(Objects.isNull(msg.getCommandName()) || msg.getCommandName().isEmpty());
    }

}
