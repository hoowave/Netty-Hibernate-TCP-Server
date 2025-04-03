package com.tools.LoginServer;

import com.tools.Common.utils.JwtUtil;
import com.tools.LoginServer.application.port.in.LoginPort;
import com.tools.LoginServer.application.port.out.LoginRepositoryPort;
import com.tools.LoginServer.application.service.LoginService;
import com.tools.LoginServer.infrastructure.adapter.in.LoginServerHandler;
import com.tools.LoginServer.infrastructure.adapter.out.LoginRepositoryAdapter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class LoginServer {
    private final JwtUtil jwtUtil = new JwtUtil();
    private final LoginRepositoryPort loginRepositoryPort = new LoginRepositoryAdapter();
    private final LoginPort loginPort = new LoginService(loginRepositoryPort, jwtUtil);

    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    private int port = 14001;

    public void run() throws InterruptedException {
        ServerBootstrap loginServerBootstrap = new ServerBootstrap();
        loginServerBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new LoginServerHandler(loginPort));
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        ChannelFuture loginServerFuture = loginServerBootstrap.bind(port).sync();
        System.out.println("로그인 서버 오픈 : " + port);
    }
}
