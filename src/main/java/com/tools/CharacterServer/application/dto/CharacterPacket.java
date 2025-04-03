package com.tools.CharacterServer.application.dto;

import com.tools.Common.packet.InPacket;
import com.tools.Common.packet.OutPacket;
import com.tools.Common.packet.enums.PacketHeader;
import com.tools.Common.packet.enums.PacketOpcode;
import com.tools.Common.packet.enums.ResponseCode;
import io.netty.buffer.ByteBuf;

public class CharacterPacket {

    public static class CharacterInPacket extends InPacket{

        public CharacterInPacket(ByteBuf buffer) {
            super(buffer);
        }

    }

    public static class CharacterOutPacket extends OutPacket{

        public CharacterOutPacket(ResponseCode responseCode, PacketOpcode packetOpcode, PacketHeader packetHeader) {
            super(responseCode, packetOpcode, packetHeader);
        }

    }

}
