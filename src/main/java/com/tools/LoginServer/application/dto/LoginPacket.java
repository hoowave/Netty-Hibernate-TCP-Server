package com.tools.LoginServer.application.dto;

import com.tools.Common.packet.InPacket;
import com.tools.Common.packet.OutPacket;
import com.tools.Common.packet.enums.PacketHeader;
import com.tools.Common.packet.enums.PacketOpcode;
import com.tools.Common.packet.enums.ResponseCode;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class LoginPacket {

    public static class LoginInPacket extends InPacket {
        private String decodedToken;
        private ByteBuf encodedToken;

        public LoginInPacket(InPacket inPacket) {
            super(inPacket);
            this.decodedToken = decodeToken(inPacket.getBody());
            this.encodedToken = encodeToken(this.decodedToken);
        }

        public String decodeToken(byte[] token) {
            String hexData = new String(token, StandardCharsets.UTF_8).trim();
            String[] hexArray = hexData.split(" ");
            StringBuilder sb = new StringBuilder();
            for (String hex : hexArray) {
                int decimal = Integer.parseInt(hex, 16);
                sb.append((char) decimal);
            }
            return sb.toString().trim();
        }

        public ByteBuf encodeToken(String token) {
            byte[] bytes = token.getBytes(StandardCharsets.UTF_8);
            StringBuilder hexString = new StringBuilder();
            for (byte b : bytes) {
                hexString.append(String.format("%02X ", b));
            }
            byte[] hexBytes = hexString.toString().trim().getBytes(StandardCharsets.UTF_8);
            ByteBuf buffer = Unpooled.buffer(hexBytes.length);
            buffer.writeBytes(hexBytes);
            return buffer;
        }

        public String getDecodedToken() {
            return decodedToken;
        }

        public ByteBuf getEncodedToken() {
            return encodedToken;
        }


    }

    public static class LoginOutPacket extends OutPacket {

        public LoginOutPacket(LoginInPacket loginInPacket, PacketOpcode packetOpcode, PacketHeader packetHeader) {
            super(loginInPacket.getResponseCode(), packetOpcode, packetHeader);
            if(loginInPacket.getResponseCode() == ResponseCode.PACKET){
                super.setByteBuf(loginInPacket.getEncodedToken());
            }
            if(loginInPacket.getResponseCode() == ResponseCode.JSON){
                super.setJsonMap(loginInPacket.getDecodedToken());
            }
        }

    }

}
