package com.tools;

import com.tools.CharacterServer.CharacterServer;
import com.tools.LoginServer.LoginServer;

public class Main {
    public static void main(String[] args) throws Exception {
        new LoginServer().run();
        new CharacterServer().run();
    }
}