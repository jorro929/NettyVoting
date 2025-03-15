package ru.vasiliygrinin.netty.chat.client;


import ru.vasiliygrinin.netty.chat.client.messags.RequestMessagePackageBuilder;

import java.util.Scanner;

public class ClientApp {
    private Network network;




    public void run(String host, int port, Scanner scanner) throws Exception {
        network = new Network(host, port, scanner);
        while (!network.isConnect()) {
            System.out.println(network.isConnect());
            Thread.sleep(200);
        }
        while (network.isConnect()) {
        }
        scanner.close();
    }
}
