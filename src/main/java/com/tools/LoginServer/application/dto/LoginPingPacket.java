package com.tools.LoginServer.application.dto;

import com.tools.Common.packet.InPacket;
import com.tools.Common.packet.OutPacket;
import com.tools.Common.packet.enums.PacketHeader;
import com.tools.Common.packet.enums.PacketOpcode;
import com.tools.Common.packet.enums.ResponseCode;

public class LoginPingPacket {

    public static class LoginPingInPacket extends InPacket {
        public LoginPingInPacket(InPacket inPacket) {
            super(inPacket);
        }
    }

    public static class LoginPingOutPacket extends OutPacket {
        public LoginPingOutPacket(ResponseCode responseCode, PacketOpcode packetOpcode, PacketHeader packetHeader) {
            super(responseCode, packetOpcode, packetHeader);
        }
    }

}
