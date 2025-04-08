package com.tools.CharacterServer.application.port.in;

import com.tools.CharacterServer.application.dto.CharacterInsertPacket;
import com.tools.CharacterServer.application.dto.CharacterListPacket;
import com.tools.CharacterServer.application.dto.CharacterPingPacket;

public interface CharacterPort {
    CharacterPingPacket.CharacterPingOutPacket ping(CharacterPingPacket.CharacterPingInPacket characterPingInPacket);
    CharacterListPacket.CharacterListOutPacket list(CharacterListPacket.CharacterListInPacket characterListInPacket);
    CharacterInsertPacket.CharacterInsertOutPacket insert(CharacterInsertPacket.CharacterInsertInPacket characterInPacket);
}
