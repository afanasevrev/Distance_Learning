package com.example.Server.authorization;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Login {
    private String login;
    private String password;
    private int category;
    private String type;
    public Login() {}
    public Login(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public Login(int category, String type) {
        this.category = category;
        this.type = type;
    }
    public Login(String login, String password, int category, String type) {
        this.login = login;
        this.password = password;
        this.category = category;
        this.type = type;
    }
}
