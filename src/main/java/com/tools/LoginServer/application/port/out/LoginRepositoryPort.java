package com.tools.LoginServer.application.port.out;

import com.tools.Common.db.core.Account;

public interface LoginRepositoryPort {
    Account findByUserId(String userId);
}
