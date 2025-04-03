package com.tools.LoginServer.infrastructure.adapter.out;

import com.tools.Common.db.HibernateUtil;
import com.tools.Common.db.entity.Account;
import com.tools.Common.db.entity.Game;
import com.tools.Common.exception.PacketException;
import com.tools.LoginServer.application.port.out.LoginRepositoryPort;
import jakarta.persistence.Query;
import org.hibernate.Session;

public class LoginRepositoryAdapter implements LoginRepositoryPort {
    @Override
    public Account findByUserId(String userId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = session.createQuery("FROM Account WHERE user_id = :userId", Account.class);
            query.setParameter("userId", userId);
            Account account = (Account) query.getResultList().stream().findFirst().orElse(null);
            return account;
        } catch (Exception e) {
            throw new PacketException("로그인 실패!");
        } finally {
            session.close();
        }
    }
}
