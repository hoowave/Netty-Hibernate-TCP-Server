package com.tools.Common.session;

import com.tools.Common.db.entity.Game;
import com.tools.Common.exception.PacketException;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ClientSession {
    private static final ClientSession INSTANCE = new ClientSession();
    private Map<UUID, Game> session;

    private ClientSession() {
        this.session = new ConcurrentHashMap<>();
    }

    public static ClientSession getInstance() {
        return INSTANCE;
    }

    public void setLogin(UUID sessionUUID) {
        System.out.println(sessionUUID);
        session.put(sessionUUID, new Game());
    }

    public void setCharacter(UUID sessionUUID, Game game) {
        if (session.containsKey(sessionUUID)) {
            session.put(sessionUUID, game);
        } else {
            throw new PacketException("로그인되지 않은 유저입니다: " + sessionUUID);
        }
    }

    public Game getCharacter(String userId) {
        return session.get(userId);
    }

    public void logout(String userId) {
        session.remove(userId);
    }

}
