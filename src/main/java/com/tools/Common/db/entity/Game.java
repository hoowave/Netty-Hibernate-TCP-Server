package com.tools.Common.db.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Account account;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private int level;

    @Column(nullable = false)
    private int exp;

    @Column(nullable = false)
    private int playerX;

    @Column(nullable = false)
    private int playerY;

    @Override
    public String toString() {
        return "Game{id=" + id +
                ", user_id=" + (account != null ? account.getUserId() : "null") +
                ", nickname='" + nickname + '\'' +
                ", level=" + level +
                ", exp=" + exp +
                ", playerX=" + playerX +
                ", playerY=" + playerY +
                '}';
    }

    public Game(){ }

    public Game(Account user, String nickname, int level, int exp, int playerX, int playerY) {
        this.account = user;
        this.nickname = nickname;
        this.level = level;
        this.exp = exp;
        this.playerX = playerX;
        this.playerY = playerY;
    }


    public String getUserId() {
        return account.getUserId();
    }

}
