package ru.vasiliygrinin.netty.chat.client;


import ru.vasiliygrinin.netty.chat.client.messags.RequestMessagePackageBuilder;

public class ClientApp {
    private Network network;

    private RequestMessagePackageBuilder builder;


    public static void main(String[] args) throws Exception {
        ClientApp clientApp = new ClientApp();
        clientApp.run();


    }

    public void run() throws Exception {

            network = new Network();
            builder = new RequestMessagePackageBuilder();
            while (!network.isConnect()){
                System.out.println(network.isConnect());
                Thread.sleep(200);
            }
            network.sendMessage(builder.getRequestMessagePackage(0, "hello"));
    }
}
