package com.tools;

import com.tools.CharacterServer.CharacterServer;
import com.tools.Common.db.HibernateUtil;
import com.tools.LoginServer.LoginServer;
import org.hibernate.Session;

public class Main {
    public static void main(String[] args) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.getTransaction().commit();
        }
        new LoginServer().run();
        new CharacterServer().run();
    }
}