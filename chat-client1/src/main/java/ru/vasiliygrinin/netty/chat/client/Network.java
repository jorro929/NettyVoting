package ru.vasiliygrinin.netty.chat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.dns.TcpDnsQueryDecoder;
import io.netty.handler.codec.dns.TcpDnsQueryEncoder;
import io.netty.handler.codec.dns.TcpDnsResponseDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import ru.vasiliygrinin.netty.chat.client.coders.RequestMsgPckEncoder;
import ru.vasiliygrinin.netty.chat.client.coders.ResponseMsgPckDecoder;
import ru.vasiliygrinin.netty.chat.client.handlers.ClientMainHandler;
import ru.vasiliygrinin.netty.chat.client.messags.RequestMessagePackage;
import ru.vasiliygrinin.netty.chat.client.messags.ResponseMessagePackage;

import java.util.Objects;
import java.util.Scanner;

public class Network {
    private SocketChannel channel;

    private static final String HOST = "localhost";
    private static final int PORT = 8189;

    public Network() {
        new Thread(() -> {

            try (
                    EventLoopGroup workerGroup = new NioEventLoopGroup();
            ) {
                Bootstrap b = new Bootstrap();
                b.group(workerGroup)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                channel = socketChannel;
                                socketChannel.pipeline().addLast(new RequestMsgPckEncoder(), new ResponseMsgPckDecoder(),new ClientMainHandler());

                            }
                        });

                ChannelFuture future = b.connect(HOST, PORT).sync();
                future.channel().closeFuture().sync();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
    }

    public void sendMessage(RequestMessagePackage msg) {
        channel.writeAndFlush(msg);
    }

    public boolean isConnect(){
        return !Objects.isNull(channel);
    }
}
