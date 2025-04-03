package com.tools.Common.packet;

import com.tools.Common.packet.enums.PacketHeader;
import com.tools.Common.packet.enums.PacketOpcode;
import com.tools.Common.packet.enums.ResponseCode;
import com.tools.Common.utils.JsonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class OutPacket {

    protected ResponseCode responseCode;
    protected ByteBuf byteBuf;
    protected Map<String, Object> jsonMap;

    public OutPacket(ResponseCode responseCode, PacketOpcode packetOpcode, PacketHeader packetHeader) {
        this.responseCode = responseCode;
        if (responseCode == ResponseCode.PACKET) {
            this.byteBuf = Unpooled.buffer();
            this.byteBuf.writeShort(packetOpcode.getValue());
            this.byteBuf.writeShort(0);
            this.byteBuf.writeByte(0x20);
            this.byteBuf.writeShort(packetHeader.getValue());
            this.byteBuf.writeShort(0);
            this.byteBuf.writeByte(0x20);
        }
        if (responseCode == ResponseCode.JSON) {
            jsonMap = new HashMap<>();
            this.jsonMap.put("opcode", packetOpcode);
            this.jsonMap.put("header", packetHeader);
        }
    }

    public void setByteBuf(ByteBuf byteBuf) {
        this.byteBuf.writeBytes(byteBuf);
    }

    public void setJsonMap(Object body) {
        this.jsonMap.put("body", body);
    }

    public ByteBuf getByteBuf() {
        return byteBuf;
    }

    public String getJsonString() {
        return JsonUtil.toJson(jsonMap);
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }
}
