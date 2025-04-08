package com.tools.Common.db.entity;

import com.tools.Common.db.entity.enums.AccountRole;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String user_id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountRole role;

    @Column(nullable = false)
    private String created_At;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Game> gameList = new ArrayList<>();

    public Account() {}

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", user_id='" + user_id + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                ", created_At='" + created_At + '\'' +
                '}';
    }

    public String getUserId(){
        return user_id;
    }

    public List<Game> getGameList(){
        return gameList;
    }
}