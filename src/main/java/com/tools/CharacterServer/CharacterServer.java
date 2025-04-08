package com.tools.CharacterServer;

import com.tools.CharacterServer.application.port.in.CharacterPort;
import com.tools.CharacterServer.application.port.out.CharacterRepositoryPort;
import com.tools.CharacterServer.application.service.CharacterService;
import com.tools.CharacterServer.infrastructure.adapter.in.CharacterServerHandler;
import com.tools.CharacterServer.infrastructure.adapter.out.CharacterRepositoryAdapter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class CharacterServer {
    private final CharacterRepositoryPort characterRepositoryPort = new CharacterRepositoryAdapter();
    private final CharacterPort characterPort = new CharacterService(characterRepositoryPort);

    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    private int port = 14002;


    public void run() throws InterruptedException {
        ServerBootstrap characterServerBootstrap = new ServerBootstrap();
        characterServerBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new CharacterServerHandler(characterPort));
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        ChannelFuture characterServerFuture = characterServerBootstrap.bind(port).sync();
        System.out.println("캐릭터 서버 오픈 : " + port);
    }
}
