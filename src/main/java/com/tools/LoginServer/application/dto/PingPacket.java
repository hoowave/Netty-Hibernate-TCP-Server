package com.tools.LoginServer.application.dto;

import com.tools.Common.packet.InPacket;
import com.tools.Common.packet.OutPacket;
import com.tools.Common.packet.enums.PacketHeader;
import com.tools.Common.packet.enums.PacketOpcode;
import com.tools.Common.packet.enums.ResponseCode;

public class PingPacket {

    public static class PingInPacket extends InPacket{
        public PingInPacket(InPacket inPacket) {
            super(inPacket);
        }
    }

    public static class PingOutPacket extends OutPacket {
        public PingOutPacket(ResponseCode responseCode, PacketOpcode packetOpcode, PacketHeader packetHeader) {
            super(responseCode, packetOpcode, packetHeader);
        }
    }

}
