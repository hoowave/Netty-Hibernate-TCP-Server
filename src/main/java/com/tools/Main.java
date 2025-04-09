package com.tools;

import com.tools.CharacterServer.CharacterServer;
import com.tools.Common.db.HibernateUtil;
import com.tools.GameServer.GameServer;
import com.tools.LoginServer.LoginServer;

public class Main {
    public static void main(String[] args) throws Exception {
        HibernateUtil.getSessionFactory().openSession();
        new LoginServer().run();
        new CharacterServer().run();
        new GameServer().run();
    }
}