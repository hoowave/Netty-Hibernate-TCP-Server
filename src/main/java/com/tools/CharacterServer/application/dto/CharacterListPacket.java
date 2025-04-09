package com.tools.CharacterServer.application.dto;

import com.tools.Common.db.entity.Game;
import com.tools.Common.packet.InPacket;
import com.tools.Common.packet.OutPacket;
import com.tools.Common.packet.enums.PacketHeader;
import com.tools.Common.packet.enums.PacketOpcode;
import com.tools.Common.packet.enums.ResponseCode;
import com.tools.Common.utils.JsonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;
import java.util.List;

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

        public ByteBuf encodeGame(String gameJson) {
            byte[] bytes = gameJson.getBytes(StandardCharsets.UTF_8);
            StringBuilder hexString = new StringBuilder();
            for (byte b : bytes) {
                hexString.append(String.format("%02X ", b));
            }
            byte[] hexBytes = hexString.toString().trim().getBytes(StandardCharsets.UTF_8);
            ByteBuf buffer = Unpooled.buffer(hexBytes.length);
            buffer.writeBytes(hexBytes);
            return buffer;
        }

        public String getDecodedUuid() {
            return decodedUuid;
        }

    }

    public static class CharacterListOutPacket extends OutPacket {

        public CharacterListOutPacket(CharacterListInPacket characterListInPacket, PacketOpcode packetOpcode, PacketHeader packetHeader, List<Game> gameList) {
            super(characterListInPacket.getResponseCode(), packetOpcode, packetHeader);
            if(characterListInPacket.getResponseCode() == ResponseCode.PACKET){
                ByteBuf body = Unpooled.buffer();
                body.writeBytes(characterListInPacket.encodeGame(JsonUtil.toJson(gameList)));
                super.setByteBuf(body);
            }
            if(characterListInPacket.getResponseCode() == ResponseCode.JSON){
                super.setJsonMap(JsonUtil.toJson(gameList));
            }
        }

    }

}
