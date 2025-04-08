package com.tools.LoginServer.application.port.in;

import com.tools.LoginServer.application.dto.*;

public interface LoginPort {
    LoginPingPacket.LoginPingOutPacket ping(LoginPingPacket.LoginPingInPacket loginPingInPacket);

    LoginPacket.LoginOutPacket login(LoginPacket.LoginInPacket loginInPacket);
}
