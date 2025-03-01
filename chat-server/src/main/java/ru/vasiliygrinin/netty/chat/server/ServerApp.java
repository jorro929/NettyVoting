package ru.vasiliygrinin.netty.chat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import ru.vasiliygrinin.netty.chat.server.coders.RequestMsgPckDecoder;
import ru.vasiliygrinin.netty.chat.server.coders.ResponseMsgPckEncoder;
import ru.vasiliygrinin.netty.chat.server.votes.Vote;


import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerApp {
    private static final int PORT = 8189;

    private static final ConcurrentMap<String, List<Vote>> topics = new ConcurrentHashMap<>();

    public static void main(String[] args) {

        try (
                EventLoopGroup bossGroup = new NioEventLoopGroup(1);
                EventLoopGroup workerGroup = new NioEventLoopGroup();
                ){
            ServerBootstrap b  = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new RequestMsgPckDecoder(), new ResponseMsgPckEncoder(), new MainHandler(topics));
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = b.bind(PORT).sync();
            future.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
