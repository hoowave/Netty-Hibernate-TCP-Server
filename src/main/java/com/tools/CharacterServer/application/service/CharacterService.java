package com.tools.CharacterServer.application.service;

import com.tools.CharacterServer.application.dto.CharacterInsertPacket;
import com.tools.CharacterServer.application.dto.CharacterListPacket;
import com.tools.CharacterServer.application.dto.CharacterPingPacket;
import com.tools.CharacterServer.application.port.in.CharacterPort;
import com.tools.CharacterServer.application.port.out.CharacterRepositoryPort;
import com.tools.Common.db.entity.Game;
import com.tools.Common.packet.enums.PacketHeader;
import com.tools.Common.packet.enums.PacketOpcode;
import com.tools.Common.session.ClientSession;

public class CharacterService implements CharacterPort {
    private final CharacterRepositoryPort characterRepository;

    public CharacterService(CharacterRepositoryPort characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Override
    public CharacterPingPacket.CharacterPingOutPacket ping(CharacterPingPacket.CharacterPingInPacket characterPingInPacket) {
        var characterPingOutPacket = new CharacterPingPacket.CharacterPingOutPacket(characterPingInPacket.getResponseCode(), PacketOpcode.SUCCESS, PacketHeader.PING_RESPONSE);
        return characterPingOutPacket;
    }

    @Override
    public CharacterListPacket.CharacterListOutPacket list(CharacterListPacket.CharacterListInPacket characterListInPacket) {
        String decodeUuid = characterListInPacket.getDecodedUuid();
        System.out.println(decodeUuid);
        var characterListOutPacket = new CharacterListPacket.CharacterListOutPacket(characterListInPacket, PacketOpcode.SUCCESS, PacketHeader.CHARACTER_LIST_RESPONSE);
        return characterListOutPacket;
    }

    @Override
    public CharacterInsertPacket.CharacterInsertOutPacket insert(CharacterInsertPacket.CharacterInsertInPacket characterInsertInPacket) {
        String decodeUuid = characterInsertInPacket.getDecodedUuid();
        String decodeNickname = characterInsertInPacket.getDecodedNickname();
        var account = ClientSession.getInstance().getLoginSession(decodeUuid);
        var game = new Game(account, decodeNickname, 1, 0, 0, 0);
        characterRepository.insert(game);
        var characterInsertOutPacket = new CharacterInsertPacket.CharacterInsertOutPacket(characterInsertInPacket, PacketOpcode.SUCCESS, PacketHeader.CHARACTER_ADD_RESPONSE);
        return characterInsertOutPacket;
    }
}
