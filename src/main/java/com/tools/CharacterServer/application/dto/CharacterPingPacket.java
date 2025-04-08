package com.tools.CharacterServer.application.dto;

import com.tools.Common.packet.InPacket;
import com.tools.Common.packet.OutPacket;
import com.tools.Common.packet.enums.PacketHeader;
import com.tools.Common.packet.enums.PacketOpcode;
import com.tools.Common.packet.enums.ResponseCode;

public class CharacterPingPacket {

    public static class CharacterPingInPacket extends InPacket {
        public CharacterPingInPacket(InPacket inPacket) {
            super(inPacket);
        }
    }

    public static class CharacterPingOutPacket extends OutPacket {
        public CharacterPingOutPacket(ResponseCode responseCode, PacketOpcode packetOpcode, PacketHeader packetHeader) {
            super(responseCode, packetOpcode, packetHeader);
        }
    }
}
