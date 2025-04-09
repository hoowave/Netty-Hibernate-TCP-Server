package com.tools.CharacterServer.application.service;

import com.tools.CharacterServer.application.dto.*;
import com.tools.CharacterServer.application.port.in.CharacterPort;
import com.tools.CharacterServer.application.port.out.CharacterRepositoryPort;
import com.tools.Common.db.entity.Game;
import com.tools.Common.packet.enums.PacketHeader;
import com.tools.Common.packet.enums.PacketOpcode;
import com.tools.Common.session.ClientSession;
import com.tools.Common.session.PlayerState;

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
        var account = ClientSession.getInstance().getLoginSession(decodeUuid);
        var gameList = characterRepository.selectGameList(account.getUserId());
        gameList.forEach(game -> {
            System.out.println(game.toString());
        });
        var characterListOutPacket = new CharacterListPacket.CharacterListOutPacket(characterListInPacket, PacketOpcode.SUCCESS, PacketHeader.CHARACTER_LIST_RESPONSE, gameList);
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

    @Override
    public CharacterDeletePacket.CharacterDeleteOutPacket delete(CharacterDeletePacket.CharacterDeleteInPacket characterDeleteInPacket) {
        String decodeUuid = characterDeleteInPacket.getDecodedUuid();
        String decodeNickname = characterDeleteInPacket.getDecodedNickname();
        var account = ClientSession.getInstance().getLoginSession(decodeUuid);
        characterRepository.delete(account.getId(), decodeNickname);
        var characterDeleteOutPacket = new CharacterDeletePacket.CharacterDeleteOutPacket(characterDeleteInPacket, PacketOpcode.SUCCESS, PacketHeader.CHARACTER_REMOVE_RESPONSE);
        return characterDeleteOutPacket;
    }

    @Override
    public CharacterSelectPacket.CharacterSelectOutPacket select(CharacterSelectPacket.CharacterSelectInPacket characterSelectInPacket) {
        String decodeUuid = characterSelectInPacket.getDecodedUuid();
        String decodeNickname = characterSelectInPacket.getDecodedNickname();
        var account = ClientSession.getInstance().getLoginSession(decodeUuid);
        var game = characterRepository.findGame(account.getId(), decodeNickname);
        var playerState = new PlayerState(game);
        ClientSession.getInstance().setGame(decodeUuid, playerState);
        var characterSelectOutPacket = new CharacterSelectPacket.CharacterSelectOutPacket(characterSelectInPacket, PacketOpcode.SUCCESS, PacketHeader.CHARACTER_SELECT_RESPONSE);
        return characterSelectOutPacket;
    }
}
