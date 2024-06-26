package com.example.Server.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
/**
 * Класс для взаимодействия с БД для учеников
 */
@Setter
@Getter
@Entity
@Table(name = "students")
public class Students {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "first_name")
    private String first_name;
    @Column(name = "middle_name")
    private String middle_name;
    @Column(name = "last_name")
    private String last_name;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "birth")
    private String birth;
    @Column(name = "email")
    private String email;
    @Column(name = "category")
    private int category;
    @Column(name = "type")
    private String type;
    @Column(name = "telephone_number")
    private String telephone_number;
    @Column(name = "pass")
    private String pass;
    public Students() {}
    public Students(String first_name, String middle_name, String last_name, String login, String password, String birth, String email, int category, String type, String telephone_number, String pass) {
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.login = login;
        this.password = password;
        this.birth = birth;
        this.email = email;
        this.category = category;
        this.type = type;
        this.telephone_number = telephone_number;
        this.pass = pass;
    }
}
