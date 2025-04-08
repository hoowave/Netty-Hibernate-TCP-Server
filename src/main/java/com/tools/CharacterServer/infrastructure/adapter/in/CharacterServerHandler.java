package com.tools.CharacterServer.infrastructure.adapter.in;

import com.tools.CharacterServer.application.dto.CharacterInsertPacket;
import com.tools.CharacterServer.application.dto.CharacterListPacket;
import com.tools.CharacterServer.application.dto.CharacterPacket;
import com.tools.CharacterServer.application.dto.CharacterPingPacket;
import com.tools.CharacterServer.application.port.in.CharacterPort;
import com.tools.Common.exception.PacketException;
import com.tools.Common.packet.InPacket;
import com.tools.Common.packet.OutPacket;
import com.tools.Common.packet.enums.PacketHeader;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class CharacterServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private final CharacterPort characterPort;

    public CharacterServerHandler(CharacterPort characterPort) {
        this.characterPort = characterPort;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        var inPacket = new InPacket(msg);
        OutPacket outPacket = null;
        switch (inPacket.getPacketOpcode()) {
            case PING:
                var characterPingInPacket = new CharacterPingPacket.CharacterPingInPacket(inPacket);
                outPacket = characterPort.ping(characterPingInPacket);
                break;
            case CHARACTER:
                var characterInPacket = new CharacterPacket.CharacterInPacket(inPacket);
                if (characterInPacket.getPacketHeader() == PacketHeader.CHARACTER_LIST_REQUEST){
                    var characterListPacket = new CharacterListPacket.CharacterListInPacket(inPacket);
                    outPacket = characterPort.list(characterListPacket);
                }
                if (characterInPacket.getPacketHeader() == PacketHeader.CHARACTER_ADD_REQUEST){
                    var characterInsertPacket = new CharacterInsertPacket.CharacterInsertInPacket(inPacket);
                    outPacket = characterPort.insert(characterInsertPacket);
                }
                break;
            default:
                throw new PacketException("invalid packet opcode" + inPacket.getPacketOpcode());
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
