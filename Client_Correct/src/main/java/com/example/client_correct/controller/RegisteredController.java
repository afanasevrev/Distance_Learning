package com.example.client_correct.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class RegisteredController {
    @FXML
    private Label labelIsRegistered = new Label();
    @FXML
    private Button buttonOkGoRegistration = new Button();
    @FXML
    private void setButtonOkGoRegistration() {
        Stage stage = (Stage) buttonOkGoRegistration.getScene().getWindow();
        stage.close();
    }
}
