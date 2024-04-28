package com.example.client;

import lombok.Getter;
import lombok.Setter;
/**
 * Класс для взаимодействия с сервером
 */
@Setter
@Getter
public class StudentsTemp {
    private int id;
    private String surname;
    private String name;
    private String patronymic;
    private String login;
    private String password;
    public StudentsTemp(){}
    public StudentsTemp(int id, String surname, String name) {
        this.id = id;
        this.surname = surname;
        this.name = name;
    }
    public StudentsTemp(String surname, String name, String patronymic, String login, String password) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.login = login;
        this.password = password;
    }
}
