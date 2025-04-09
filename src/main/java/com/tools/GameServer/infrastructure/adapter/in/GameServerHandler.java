package com.tools.GameServer.infrastructure.adapter.in;

import com.tools.Common.exception.PacketException;
import com.tools.Common.packet.InPacket;
import com.tools.Common.packet.OutPacket;
import com.tools.Common.session.ClientSession;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.Set;

public class GameServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private final Set<InetSocketAddress> clientAddresses;

    public GameServerHandler(Set<InetSocketAddress> clientAddresses) {
        this.clientAddresses = clientAddresses;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        var inPacket = new InPacket(packet.content());
        switch (inPacket.getPacketOpcode()) {
            case PING:
                InetSocketAddress sender = packet.sender();
                clientAddresses.add(sender);
                break;
            case GAME:
                break;
            default:
                throw new PacketException("invalid packet opcode" + inPacket.getPacketOpcode());
        }
    }
}
