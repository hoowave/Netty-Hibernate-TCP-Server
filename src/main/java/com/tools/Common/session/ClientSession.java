package com.tools.Common.session;

import com.tools.Common.db.entity.Account;
import com.tools.Common.exception.PacketException;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ClientSession {
    private static final ClientSession INSTANCE = new ClientSession();
    private Map<String, Account> loginSession;
    private Map<String, PlayerState> playerSession;

    private ClientSession() {
        this.loginSession = new ConcurrentHashMap<>();
        this.playerSession = new ConcurrentHashMap<>();
    }

    public static ClientSession getInstance() {
        return INSTANCE;
    }

    public Account getLoginSession(String uuid) {
        return loginSession.get(uuid);
    }

    public Map<String, PlayerState> getPlayerSession() {
        return playerSession;
    }

    public String setLogin(Account account) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        System.out.println(uuid);
        loginSession.put(uuid, account);
        return uuid;
    }

    public void setGame(String sessionUUID, PlayerState playerState) {
        if (loginSession.containsKey(sessionUUID)) {
            playerSession.put(sessionUUID, playerState);
        } else {
            throw new PacketException("로그인되지 않은 유저입니다: " + sessionUUID);
        }
    }

    public void logout(String uuid) {
        loginSession.remove(uuid);
        playerSession.remove(uuid);
    }

}
