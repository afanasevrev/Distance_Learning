package com.example.client_correct.students;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StudentsData {
    public StringProperty id;
    public StringProperty first_name;
    public StringProperty middle_name;
    public StringProperty last_name;
    public StringProperty login;
    public StringProperty password;
    public StringProperty birth;
    public StringProperty email;
    public StringProperty category;
    public StringProperty type;
    public StringProperty telephone_number;
    public StringProperty pass;
    public StudentsData(String id, String first_name, String middle_name, String last_name, String login, String password, String birth, String email, String category, String type, String telephone_number, String pass) {
        this.id = new SimpleStringProperty(this, "id", id);
        this.first_name = new SimpleStringProperty(this, "first_name", first_name);
        this.middle_name = new SimpleStringProperty(this, "middle_name", middle_name);
        this.last_name = new SimpleStringProperty(this, "last_name", last_name);
        this.login = new SimpleStringProperty(this, "login", login);
        this.password = new SimpleStringProperty(this, "password", password);
        this.birth = new SimpleStringProperty(this, "birth", birth);
        this.email = new SimpleStringProperty(this, "email", email);
        this.category = new SimpleStringProperty(this, "category", category);
        this.type = new SimpleStringProperty(this, "type", type);
        this.telephone_number = new SimpleStringProperty(this, "telephone_number", telephone_number);
        this.pass = new SimpleStringProperty(this, "pass", pass);
    }

    public String getId() {
        return id.get();
    }
    public StringProperty idProperty() {
        return id;
    }
    public void setId(String id) {
        this.id.set(id);
    }
    public String getFirst_name() {
        return first_name.get();
    }
    public StringProperty first_nameProperty() {
        return first_name;
    }
    public void setFirst_name(String first_name) {
        this.first_name.set(first_name);
    }
    public String getMiddle_name() {
        return middle_name.get();
    }
    public StringProperty middle_nameProperty() {
        return middle_name;
    }
    public void setMiddle_name(String middle_name) {
        this.middle_name.set(middle_name);
    }
    public String getLast_name() {
        return last_name.get();
    }
    public StringProperty last_nameProperty() {
        return last_name;
    }
    public void setLast_name(String last_name) {
        this.last_name.set(last_name);
    }
    public String getLogin() {
        return login.get();
    }
    public StringProperty loginProperty() {
        return login;
    }
    public void setLogin(String login) {
        this.login.set(login);
    }
    public String getPassword() {
        return password.get();
    }
    public StringProperty passwordProperty() {
        return password;
    }
    public void setPassword(String password) {
        this.password.set(password);
    }
    public String getBirth() {
        return birth.get();
    }
    public StringProperty birthProperty() {
        return birth;
    }
    public void setBirth(String birth) {
        this.birth.set(birth);
    }
    public String getEmail() {
        return email.get();
    }
    public StringProperty emailProperty() {
        return email;
    }
    public void setEmail(String email) {
        this.email.set(email);
    }
    public String getCategory() {
        return category.get();
    }
    public StringProperty categoryProperty() {
        return category;
    }
    public void setCategory(String category) {
        this.category.set(category);
    }
    public String getType() {
        return type.get();
    }
    public StringProperty typeProperty() {
        return type;
    }
    public void setType(String type) {
        this.type.set(type);
    }
    public String getTelephone_number() {
        return telephone_number.get();
    }
    public StringProperty telephone_numberProperty() {
        return telephone_number;
    }
    public void setTelephone_number(String telephone_number) {
        this.telephone_number.set(telephone_number);
    }
    public String getPass() {
        return pass.get();
    }
    public StringProperty passProperty() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass.set(pass);
    }
}
