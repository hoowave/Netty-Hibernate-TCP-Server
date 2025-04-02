package com.tools.Common.packet;

import com.tools.Common.packet.enums.PacketHeader;
import com.tools.Common.packet.enums.PacketOpcode;
import com.tools.Common.packet.enums.ResponseCode;
import io.netty.buffer.ByteBuf;

public class InPacket {
    protected PacketOpcode packetOpcode;
    protected PacketHeader packetHeader;
    protected ResponseCode responseCode;
    protected byte[] body;

    public InPacket(ByteBuf buffer) {
        int readableBytes = buffer.readableBytes();
        this.packetOpcode = PacketOpcode.fromValue(buffer.readShort());
        buffer.skipBytes(1);
        this.packetHeader = PacketHeader.fromValue(buffer.readShort());
        buffer.skipBytes(1);
        this.responseCode = ResponseCode.fromValue(buffer.readShort());
        buffer.skipBytes(1);
        byte[] body = new byte[readableBytes - 9];
        buffer.readBytes(body);
        this.body = body;
    }

    public InPacket(InPacket inPacket){
        this.packetOpcode = inPacket.getPacketOpcode();
        this.packetHeader = inPacket.getPacketHeader();
        this.responseCode = inPacket.getResponseCode();
        this.body = inPacket.getBody();
    }

    public PacketOpcode getPacketOpcode() {
        return packetOpcode;
    }

    public PacketHeader getPacketHeader() {
        return packetHeader;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public byte[] getBody() {
        return body;
    }
}
