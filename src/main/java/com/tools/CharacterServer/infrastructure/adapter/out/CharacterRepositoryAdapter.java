package com.tools.CharacterServer.infrastructure.adapter.out;

import com.tools.CharacterServer.application.port.out.CharacterRepositoryPort;
import com.tools.Common.db.HibernateUtil;
import com.tools.Common.db.entity.Game;
import com.tools.Common.exception.PacketException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CharacterRepositoryAdapter implements CharacterRepositoryPort {
    @Override
    public Game findByUserId(String userId) {
        return null;
    }

    @Override
    public Game insert(Game game) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.save(game);
            tx.commit();
            return game;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw new PacketException("캐릭터 생성 실패!");
        } finally {
            session.close();
        }
    }
}
