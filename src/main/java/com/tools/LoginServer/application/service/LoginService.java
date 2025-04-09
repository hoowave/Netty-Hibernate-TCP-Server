package com.tools.LoginServer.application.service;

import com.tools.Common.packet.enums.PacketHeader;
import com.tools.Common.packet.enums.PacketOpcode;
import com.tools.Common.session.ClientSession;
import com.tools.Common.utils.JwtUtil;
import com.tools.LoginServer.application.dto.*;
import com.tools.LoginServer.application.port.in.LoginPort;
import com.tools.LoginServer.application.port.out.LoginRepositoryPort;

public class LoginService implements LoginPort {
    private final LoginRepositoryPort loginRepositoryPort;
    private final JwtUtil jwtUtil;

    public LoginService(LoginRepositoryPort loginRepositoryPort, JwtUtil jwtUtil) {
        this.loginRepositoryPort = loginRepositoryPort;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LoginPingPacket.LoginPingOutPacket ping(LoginPingPacket.LoginPingInPacket loginPingInPacket) {
        var loginPingOutPacket = new LoginPingPacket.LoginPingOutPacket(loginPingInPacket.getResponseCode(), PacketOpcode.SUCCESS, PacketHeader.PING_RESPONSE);
        return loginPingOutPacket;
    }

    @Override
    public LoginPacket.LoginOutPacket login(LoginPacket.LoginInPacket loginInPacket) {
        String decodeToken = loginInPacket.getDecodedToken();
        String userId = jwtUtil.validateToken(decodeToken);
        var account = loginRepositoryPort.findByUserId(userId);
        String uuid = ClientSession.getInstance().setLogin(account);
        var loginOutPacket = new LoginPacket.LoginOutPacket(loginInPacket, PacketOpcode.SUCCESS, PacketHeader.LOGIN_RESPONSE, uuid);
        return loginOutPacket;
    }
}
