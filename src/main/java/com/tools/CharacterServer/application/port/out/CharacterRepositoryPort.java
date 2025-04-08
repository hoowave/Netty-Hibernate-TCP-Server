package com.tools.CharacterServer.application.port.out;

import com.tools.Common.db.entity.Game;

public interface CharacterRepositoryPort {
    Game findByUserId(String userId);

    Game insert(Game game);
}
