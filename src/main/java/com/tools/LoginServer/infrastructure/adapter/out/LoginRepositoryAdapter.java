package com.tools.LoginServer.infrastructure.adapter.out;

import com.tools.LoginServer.application.port.out.LoginRepositoryPort;

public class LoginRepositoryAdapter implements LoginRepositoryPort {
    @Override
    public void test() {
        System.out.println("Login Repository Test");
    }
}
