package com.tools.Common.session;

import com.tools.Common.db.entity.Game;

public class PlayerState {
    private Long id;
    private String userId;
    private String nickname;
    private int level;
    private int exp;
    private int playerX;
    private int playerY;

    public PlayerState(Game game){
        this.id = game.getId();
        this.userId = game.getUserId();
        this.nickname = game.getNickname();
        this.level = game.getLevel();
        this.exp = game.getExp();
        this.playerX = game.getPlayerX();
        this.playerY = game.getPlayerY();
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public int getLevel() {
        return level;
    }

    public int getExp() {
        return exp;
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    @Override
    public String toString() {
        return "Game{id=" + id +
                ", user_id=" + userId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", level=" + level +
                ", exp=" + exp +
                ", playerX=" + playerX +
                ", playerY=" + playerY +
                '}';
    }
}
