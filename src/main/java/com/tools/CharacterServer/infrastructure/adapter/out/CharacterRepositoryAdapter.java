package com.tools.CharacterServer.infrastructure.adapter.out;

import com.tools.CharacterServer.application.port.out.CharacterRepositoryPort;
import com.tools.Common.db.entity.Game;

public class CharacterRepositoryAdapter implements CharacterRepositoryPort {
    @Override
    public Game findByUserId(String userId) {
        return null;
    }
}
