package com.tools.LoginServer.application.service;

import com.tools.Common.db.entity.Account;
import com.tools.Common.packet.enums.PacketHeader;
import com.tools.Common.packet.enums.PacketOpcode;
import com.tools.Common.session.ClientSession;
import com.tools.Common.utils.JwtUtil;
import com.tools.LoginServer.application.dto.*;
import com.tools.LoginServer.application.port.in.LoginPort;
import com.tools.LoginServer.application.port.out.LoginRepositoryPort;

import java.util.UUID;

public class LoginService implements LoginPort {
    private final LoginRepositoryPort loginRepositoryPort;
    private final JwtUtil jwtUtil;

    public LoginService(LoginRepositoryPort loginRepositoryPort, JwtUtil jwtUtil) {
        this.loginRepositoryPort = loginRepositoryPort;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public PingPacket.PingOutPacket ping(PingPacket.PingInPacket pingInPacket) {
        var pingOutPacket = new PingPacket.PingOutPacket(pingInPacket.getResponseCode(), PacketOpcode.SUCCESS, PacketHeader.PING_RESPONSE);
        return pingOutPacket;
    }

    @Override
    public LoginPacket.LoginOutPacket login(LoginPacket.LoginInPacket loginInPacket) {
        String decodeToken = loginInPacket.getDecodedToken();
        String userId = jwtUtil.validateToken(decodeToken);
        loginRepositoryPort.findByUserId(userId);
        ClientSession.getInstance().setLogin(UUID.randomUUID());
        var loginOutPacket = new LoginPacket.LoginOutPacket(loginInPacket, PacketOpcode.SUCCESS, PacketHeader.LOGIN_RESPONSE);
        return loginOutPacket;
    }
}
