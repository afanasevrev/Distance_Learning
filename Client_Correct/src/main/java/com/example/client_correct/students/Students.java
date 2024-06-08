package com.example.client_correct.students;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Students {
    private String first_name;
    private String middle_name;
    private String last_name;
    private String login;
    private String password;
    private String birth;
    private String email;
    private int category;
    private String type;
    private String telephone_number;
    public Students() {}
    public Students(String first_name, String middle_name, String last_name, String login, String password, String birth, String email, int category, String type, String telephone_number) {
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
    }
}
