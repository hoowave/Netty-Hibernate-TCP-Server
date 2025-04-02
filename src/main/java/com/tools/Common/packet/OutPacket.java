package com.tools.Common.packet;

import com.tools.Common.packet.enums.PacketHeader;
import com.tools.Common.packet.enums.PacketOpcode;
import com.tools.Common.packet.enums.ResponseCode;
import com.tools.Common.utils.JsonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.HashMap;
import java.util.Map;

public class OutPacket {

    protected ResponseCode responseCode;
    protected ByteBuf byteBuf;
    protected Map<String, Object> jsonMap;

    public OutPacket(ResponseCode responseCode, PacketOpcode packetOpcode, PacketHeader packetHeader) {
        this.responseCode = responseCode;
        if (responseCode == ResponseCode.PACKET) {
            byteBuf = Unpooled.buffer();
            byteBuf.writeShort(packetOpcode.getValue());
            byteBuf.writeShort(0);
            byteBuf.writeByte(0x20);
            byteBuf.writeShort(packetHeader.getValue());
            byteBuf.writeShort(0);
        }
        if (responseCode == ResponseCode.JSON) {
            jsonMap = new HashMap<>();
            this.jsonMap.put("opcode", packetOpcode);
            this.jsonMap.put("header", packetHeader);
        }
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
