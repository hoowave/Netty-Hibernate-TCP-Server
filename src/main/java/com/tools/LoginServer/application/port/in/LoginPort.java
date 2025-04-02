package com.tools.LoginServer.application.port.in;

import com.tools.LoginServer.application.dto.*;

public interface LoginPort {
    PingPacket.PingOutPacket ping(PingPacket.PingInPacket pingInPacket);

    LoginPacket.LoginOutPacket login(LoginPacket.LoginInPacket loginInPacket);
}
