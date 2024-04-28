package com.example.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SignInController {
    @FXML
    private TextField login = new TextField();
    @FXML
    private PasswordField password = new PasswordField();
    @FXML
    private Button signIn = new Button();
    @FXML
    private TextField surname = new TextField();
    @FXML
    private TextField name = new TextField();
    @FXML
    private TextField patronymic = new TextField();
    @FXML
    private TextField loginForRegistration = new TextField();
    @FXML
    private TextField passwordForRegistration = new TextField();
    @FXML
    private Button registration = new Button();
    @FXML
    private TextArea logs = new TextArea();
    public SignInController(){}
}
