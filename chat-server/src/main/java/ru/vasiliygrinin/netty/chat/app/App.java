package ru.vasiliygrinin.netty.chat.app;

import ru.vasiliygrinin.netty.chat.client.ClientApp;
import ru.vasiliygrinin.netty.chat.server.ServerApp;
import ru.vasiliygrinin.netty.chat.server.dao.SimpleDAO;

import java.util.Scanner;

public class App {

    static Scanner  scanner;
    public static void main(String[] args) throws Exception {
        AppMode appMode = null;
        scanner = new Scanner(System.in);


        try  {

            System.out.println("""
                    Welcome to NettyVotingApp!
                    Please, select to mode:
                    1.Client
                    2.Server
                    3.Exit""");
            String answer;

            while (appMode == null) {
                System.out.println(scanner.hasNextLine());
                answer = scanner.nextLine();
                switch (answer.toLowerCase()) {
                    case "1":
                    case "client":
                    case "1.client":
                        appMode = AppMode.CLIENT;
                        break;
                    case "2":
                    case "server":
                    case "2.server":
                        appMode = AppMode.SERVER;

                        break;
                    case "3":
                    case "exit":
                    case "3.exit":
                        System.exit(0);
                        break;
                }

            }

            switch (appMode) {
                case CLIENT -> runClient();
                case SERVER -> runServer();
                default -> throw new RuntimeException();
            }
        }
        finally {
        }


    }


    public static void runClient() throws Exception {
        try {
            System.out.println("please, enter the ip address: ");

            String host = scanner.nextLine();

            new ClientApp().run(host, 8189, scanner);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void runServer() {
        new ServerApp(8189, new SimpleDAO(), scanner);
    }
}
