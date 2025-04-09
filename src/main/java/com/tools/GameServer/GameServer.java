package com.tools.GameServer;

import com.tools.Common.session.ClientSession;
import com.tools.Common.utils.JsonUtil;
import com.tools.GameServer.infrastructure.adapter.in.GameServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameServer {
    private int port = 14003;
    private Channel channel;
    private final Set<InetSocketAddress> clientAddresses = ConcurrentHashMap.newKeySet();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public void run() throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(bossGroup)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new GameServerHandler(clientAddresses));
        ChannelFuture channelFuture = bootstrap.bind(port).sync();
        this.channel = channelFuture.channel();
//        startBroadcast();
        System.out.println("게임 서버 오픈 : " + port);
    }

    private void startBroadcast() {
        scheduler.scheduleAtFixedRate(() -> {
            String jsonMessage = JsonUtil.toJson(ClientSession.getInstance().getPlayerSession());
            ByteBuf buf = Unpooled.copiedBuffer(jsonMessage, CharsetUtil.UTF_8);
            for (InetSocketAddress address : clientAddresses) {
                DatagramPacket packet = new DatagramPacket(buf.retainedDuplicate(), address);
                channel.writeAndFlush(packet);
            }
//            System.out.println("브로드캐스트 전송: " + jsonMessage);
        }, 0, 1, TimeUnit.SECONDS);
    }
}
