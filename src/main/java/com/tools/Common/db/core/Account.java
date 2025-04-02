package com.tools.Common.db.core;

import com.tools.Common.db.core.enums.AccountRole;
import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
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

    public Account(String name, String userId, String password, String email, String phone, AccountRole role) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        this.name = name;
        this.user_id = userId;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.created_At = format.format(new Date());
    }

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
}