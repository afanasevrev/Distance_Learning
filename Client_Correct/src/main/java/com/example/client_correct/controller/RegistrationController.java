package com.example.client_correct.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {
    @FXML
    private TextField textFieldFirstName = new TextField();
    @FXML
    private TextField textFieldMiddleName = new TextField();
    @FXML
    private TextField textFieldLastName = new TextField();
    @FXML
    private TextField textFieldLogin = new TextField();
    @FXML
    private PasswordField passwordFieldPassword = new PasswordField();
    @FXML
    private DatePicker datePickerBirth = new DatePicker();
    @FXML
    private TextField textFieldEmail = new TextField();
    @FXML
    private ComboBox<String> comboBoxCategory = new ComboBox<>();
    @FXML
    private ComboBox<String> comboBoxType = new ComboBox<>();
    @FXML
    private Button buttonRegistration = new Button();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBoxCategory.getItems().addAll("4", "5", "6");
        comboBoxType.getItems().addAll("Пистолет", "Помповое", "Гладкоствольное");
    }
}
