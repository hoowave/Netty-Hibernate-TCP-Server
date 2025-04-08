package com.tools.CharacterServer.application.dto;

import com.tools.Common.packet.InPacket;

public class CharacterPacket {

    public static class CharacterInPacket extends InPacket {

        public CharacterInPacket(InPacket inPacket) {
            super(inPacket);
        }

    }

}
