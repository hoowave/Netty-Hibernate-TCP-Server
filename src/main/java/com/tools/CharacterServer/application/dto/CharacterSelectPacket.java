package com.tools.CharacterServer.application.dto;

import com.tools.Common.packet.InPacket;
import com.tools.Common.packet.OutPacket;
import com.tools.Common.packet.enums.PacketHeader;
import com.tools.Common.packet.enums.PacketOpcode;

import java.nio.charset.StandardCharsets;

public class CharacterSelectPacket {

    public static class CharacterSelectInPacket extends InPacket {
        private String decodedUuid;
        private String decodedNickname;

        public CharacterSelectInPacket(InPacket inPacket){
            super(inPacket);
            String[] bodyList = decodeBody(inPacket.getBody());
            this.decodedUuid = bodyList[0];
            this.decodedNickname = bodyList[1];
        }

        public String[] decodeBody(byte[] body){
            String hexData = new String(body, StandardCharsets.UTF_8).trim();
            String[] hexArray = hexData.split(" ");
            StringBuilder sb = new StringBuilder();
            for (String hex : hexArray) {
                int decimal = Integer.parseInt(hex, 16);
                sb.append((char) decimal);
            }
            String decode = sb.toString().trim();
            return decode.split("/");
        }

        public String getDecodedUuid() {
            return decodedUuid;
        }

        public String getDecodedNickname() {
            return decodedNickname;
        }

    }

    public static class CharacterSelectOutPacket extends OutPacket {
        public CharacterSelectOutPacket(CharacterSelectInPacket characterSelectInPacket, PacketOpcode packetOpcode, PacketHeader packetHeader) {
            super(characterSelectInPacket.getResponseCode(), packetOpcode, packetHeader);
        }
    }

}
