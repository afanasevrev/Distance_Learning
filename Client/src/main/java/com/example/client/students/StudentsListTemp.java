package com.example.client.students;

import lombok.Getter;
import lombok.Setter;
/**
 * Класс для получения списка студентов от сервера
 */
@Setter
@Getter
public class StudentsListTemp {
    private String id;
    private String surname;
    private String name;
    public StudentsListTemp() {}
    public StudentsListTemp(String id, String surname, String name) {
        this.id = id;
        this.surname = surname;
        this.name = name;
    }
}
