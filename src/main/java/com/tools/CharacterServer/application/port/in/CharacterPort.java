package com.tools.CharacterServer.application.port.in;

import com.tools.CharacterServer.application.dto.*;

public interface CharacterPort {
    CharacterPingPacket.CharacterPingOutPacket ping(CharacterPingPacket.CharacterPingInPacket characterPingInPacket);
    CharacterListPacket.CharacterListOutPacket list(CharacterListPacket.CharacterListInPacket characterListInPacket);
    CharacterInsertPacket.CharacterInsertOutPacket insert(CharacterInsertPacket.CharacterInsertInPacket characterInPacket);
    CharacterDeletePacket.CharacterDeleteOutPacket delete(CharacterDeletePacket.CharacterDeleteInPacket characterDeleteInPacket);
    CharacterSelectPacket.CharacterSelectOutPacket select(CharacterSelectPacket.CharacterSelectInPacket characterSelectInPacket);
}
