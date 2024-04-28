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
    private String name;
    private String surname;
    public StudentsTemp(){}
    public StudentsTemp(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }
}
