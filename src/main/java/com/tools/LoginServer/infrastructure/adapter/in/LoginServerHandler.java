package com.tools.LoginServer.infrastructure.adapter.in;

import com.tools.Common.exception.PacketException;
import com.tools.Common.packet.InPacket;
import com.tools.Common.packet.OutPacket;
import com.tools.LoginServer.application.dto.LoginPacket;
import com.tools.LoginServer.application.dto.PingPacket;
import com.tools.LoginServer.application.port.in.LoginPort;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class LoginServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private final LoginPort loginPort;

    public LoginServerHandler(LoginPort loginPort) {
        this.loginPort = loginPort;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        var inPacket = new InPacket(msg);
        OutPacket outPacket;
        switch (inPacket.getPacketOpcode()) {
            case PING:
                var pingInPacket = new PingPacket.PingInPacket(inPacket);
                outPacket = loginPort.ping(pingInPacket);
                break;
            case LOGIN:
                var loginInPacket = new LoginPacket.LoginInPacket(inPacket);
                outPacket = loginPort.login(loginInPacket);
                break;
            default:
                throw new PacketException("Unknown login opcode: " + inPacket.getPacketOpcode());
        }
        channelOut(ctx, outPacket);
    }

    private void channelOut(ChannelHandlerContext ctx, OutPacket outPacket) {
        switch (outPacket.getResponseCode()) {
            case PACKET:
                ctx.writeAndFlush(outPacket.getByteBuf());
                break;
            case JSON:
                ctx.writeAndFlush(Unpooled.copiedBuffer(outPacket.getJsonString(), CharsetUtil.UTF_8));
                break;
            default:
                throw new PacketException("Unknown response code: " + outPacket.getResponseCode());
        }
    }
}
