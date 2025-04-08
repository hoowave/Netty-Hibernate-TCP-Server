package com.tools.CharacterServer.application.dto;

import com.tools.Common.packet.InPacket;
import com.tools.Common.packet.OutPacket;
import com.tools.Common.packet.enums.PacketHeader;
import com.tools.Common.packet.enums.PacketOpcode;
import com.tools.Common.packet.enums.ResponseCode;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

public class CharacterListPacket {

    public static class CharacterListInPacket extends InPacket {
        private String decodedUuid;
        public CharacterListInPacket(InPacket inPacket) {
            super(inPacket);
            this.decodedUuid = decodeUuid(inPacket.getBody());
        }

        public String decodeUuid(byte[] uuid) {
            String hexData = new String(uuid, StandardCharsets.UTF_8).trim();
            String[] hexArray = hexData.split(" ");
            StringBuilder sb = new StringBuilder();
            for (String hex : hexArray) {
                int decimal = Integer.parseInt(hex, 16);
                sb.append((char) decimal);
            }
            return sb.toString().trim();
        }

        public String getDecodedUuid() {
            return decodedUuid;
        }

    }

    public static class CharacterListOutPacket extends OutPacket {

        public CharacterListOutPacket(CharacterListInPacket characterListInPacket, PacketOpcode packetOpcode, PacketHeader packetHeader) {
            super(characterListInPacket.getResponseCode(), packetOpcode, packetHeader);
            if(characterListInPacket.getResponseCode() == ResponseCode.PACKET){
                ByteBuf body = Unpooled.buffer();
                body.writeShort(0x4646);
                super.setByteBuf(body);
            }
            if(characterListInPacket.getResponseCode() == ResponseCode.JSON){
                super.setJsonMap("NONE");
            }
        }

    }

}
