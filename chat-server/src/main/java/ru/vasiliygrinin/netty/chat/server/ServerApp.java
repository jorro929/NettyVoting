package ru.vasiliygrinin.netty.chat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import ru.vasiliygrinin.netty.chat.server.coders.RequestMsgPckDecoder;
import ru.vasiliygrinin.netty.chat.server.coders.ResponseMsgPckEncoder;
import ru.vasiliygrinin.netty.chat.server.dao.DAOManager;
import ru.vasiliygrinin.netty.chat.server.dao.SimpleDAO;
import ru.vasiliygrinin.netty.chat.server.dao.SimpleVoteDirector;
import ru.vasiliygrinin.netty.chat.server.dao.VotesDirector;
import ru.vasiliygrinin.netty.chat.server.votes.Vote;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerApp {


    private DAOManager daoManager;
    Scanner scanner;


    public ServerApp(int port, DAOManager daoManager, Scanner scanner) {
        this.daoManager = daoManager;
        this.scanner = scanner;
        run(daoManager.create(), port);
    }

    public void run(VotesDirector vd, int port) {
        try (
                EventLoopGroup bossGroup = new NioEventLoopGroup(1);
                EventLoopGroup workerGroup = new NioEventLoopGroup();
        ) {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new RequestMsgPckDecoder(), new ResponseMsgPckEncoder(), new MainHandler(vd));
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = b.bind(port).sync();
            new Thread(() -> serverManagement(future)).start();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void serverManagement(ChannelFuture future) {
        boolean isActive = true;
        String command;
        System.out.println("Hello, Admin!");


        try {
            while (isActive) {
                System.out.println("Hello, Admin!");
                command = scanner.nextLine();
                System.out.println("Hello, Admin!");
                switch (command) {
                    case "exit":
                        future.channel().close();
                        isActive = false;
                        break;
                    case "save":
                        save();
                        break;
                    case "load":
                        future.channel().close();
                        isActive = false;
                        break;
                    default:
                        System.out.println("asdgfasggasdhdsdsdn\ndsgsdgsdgsdgsdgsdg\nssdfnhhhhhhhhh");
                        continue;
                }
            }
        }finally{
            System.out.println("goodbye, admin!");
            scanner.close();
        }

    }

    private void save(){
        try {
            String fileWay = scanner.nextLine();
            if(!fileWay.endsWith(".bin")){
                fileWay += ".bin";
            }
            daoManager.save(new File(fileWay));
            System.out.println("Successful!");
        } catch (IOException e) {
            System.err.println("please, get correct file");
            save();
        }
    }
}
