package com.tools.CharacterServer.infrastructure.adapter.out;

import com.tools.CharacterServer.application.port.out.CharacterRepositoryPort;
import com.tools.Common.db.HibernateUtil;
import com.tools.Common.db.entity.Account;
import com.tools.Common.db.entity.Game;
import com.tools.Common.exception.PacketException;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CharacterRepositoryAdapter implements CharacterRepositoryPort {
    @Override
    public List<Game> selectGameList(String userId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = session.createQuery("SELECT a FROM Account a LEFT JOIN FETCH a.gameList WHERE a.user_id = :userId", Account.class);
            query.setParameter("userId", userId);
            Account account = (Account) query.getSingleResult();
            return account.getGameList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new PacketException("캐릭터 목록 로딩 실패!");
        } finally {
            session.close();
        }
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

    @Override
    public void delete(Long accountId, String nickname) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM Game g WHERE g.account.id = :accountId AND g.nickname = :nickname");
            query.setParameter("accountId", accountId);
            query.setParameter("nickname", nickname);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw new PacketException("캐릭터 삭제 실패!");
        } finally {
            session.close();
        }
    }

    @Override
    public Game findGame(Long accountId, String nickname) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = session.createQuery("SELECT g FROM Game g JOIN FETCH g.account WHERE g.account.id = :accountId AND g.nickname = :nickname", Game.class);
            query.setParameter("accountId", accountId);
            query.setParameter("nickname", nickname);
            Game game = (Game) query.getSingleResult();
            return game;
        } catch (Exception e) {
            e.printStackTrace();
            throw new PacketException("캐릭터 목록 로딩 실패!");
        } finally {
            session.close();
        }
    }
}
