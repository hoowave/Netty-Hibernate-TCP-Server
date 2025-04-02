package com.tools.LoginServer.application.service;

import com.tools.Common.packet.enums.PacketHeader;
import com.tools.Common.packet.enums.PacketOpcode;
import com.tools.Common.utils.JwtUtil;
import com.tools.LoginServer.application.dto.*;
import com.tools.LoginServer.application.port.in.LoginPort;

public class LoginService implements LoginPort {
    private final JwtUtil jwtUtil;

    public LoginService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public PingPacket.PingOutPacket ping(PingPacket.PingInPacket pingInPacket) {
        var pingOutPacket = new PingPacket.PingOutPacket(pingInPacket.getResponseCode(), PacketOpcode.SUCCESS, PacketHeader.PING_RESPONSE);
        return pingOutPacket;
    }

    @Override
    public LoginPacket.LoginOutPacket login(LoginPacket.LoginInPacket loginInPacket) {
        String userId = jwtUtil.validateToken(loginInPacket.readToken());
        System.out.println(userId);
        var loginOutPacket = new LoginPacket.LoginOutPacket(loginInPacket.getResponseCode(), PacketOpcode.SUCCESS, PacketHeader.LOGIN_RESPONSE);
        return loginOutPacket;
    }
}
