package com.tools.Common.session;

import com.tools.Common.db.entity.Account;
import com.tools.Common.db.entity.Game;
import com.tools.Common.exception.PacketException;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ClientSession {
    private static final ClientSession INSTANCE = new ClientSession();
    private Map<String, Account> loginSession;
    private Map<String, Game> gameSession;

    private ClientSession() {
        this.loginSession = new ConcurrentHashMap<>();
        this.gameSession = new ConcurrentHashMap<>();
    }

    public static ClientSession getInstance() {
        return INSTANCE;
    }

    public Account getLoginSession(String uuid) {
        return loginSession.get(uuid);
    }

    public void setLogin(Account account) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        System.out.println(uuid);
        loginSession.put(uuid, account);
    }

    public void setCharacter(String sessionUUID, Game game) {
        if (loginSession.containsKey(sessionUUID)) {
            gameSession.put(sessionUUID, game);
        } else {
            throw new PacketException("로그인되지 않은 유저입니다: " + sessionUUID);
        }
    }

    public void logout(String uuid) {
        loginSession.remove(uuid);
        gameSession.remove(uuid);
    }

}
