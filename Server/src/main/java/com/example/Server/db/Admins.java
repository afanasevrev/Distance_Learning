package com.example.Server.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
/**
 * Класс для взаимодействия с БД для администраторов системы
 */
@Setter
@Getter
@Entity
@Table(name = "admins")
public class Admins {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    public Admins(){}
    public Admins(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
