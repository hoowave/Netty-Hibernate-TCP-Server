package com.tools.CharacterServer.application.port.out;

import com.tools.Common.db.entity.Game;

import java.util.List;

public interface CharacterRepositoryPort {
    List<Game> selectGameList(String userId);

    Game insert(Game game);

    void delete(Long accountId, String nickname);

    Game findGame(Long accountId, String nickname);
}
