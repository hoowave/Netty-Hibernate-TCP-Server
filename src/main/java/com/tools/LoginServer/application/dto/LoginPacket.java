package com.tools.LoginServer.application.dto;

import com.tools.Common.packet.InPacket;
import com.tools.Common.packet.OutPacket;
import com.tools.Common.packet.enums.PacketHeader;
import com.tools.Common.packet.enums.PacketOpcode;
import com.tools.Common.packet.enums.ResponseCode;

public class LoginPacket {

    public static class LoginInPacket extends InPacket {

        public LoginInPacket(InPacket inPacket) {
            super(inPacket);
        }

        public String readToken() {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < this.body.length; i++) {
                if (body[i] == 32) continue;
                sb.append((char) this.body[i]);
            }
            return sb.toString();
        }

    }

    public static class LoginOutPacket extends OutPacket {

        public LoginOutPacket(ResponseCode responseCode, PacketOpcode packetOpcode, PacketHeader packetHeader) {
            super(responseCode, packetOpcode, packetHeader);
        }

    }

}
