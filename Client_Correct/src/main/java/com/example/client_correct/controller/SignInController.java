package com.example.client_correct.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
/**
 * Контроллер для входа в систему
 */
public class SignInController {
    private Logger logger = Logger.getLogger(SignInController.class);
    @FXML
    private TextField textFieldLogin = new TextField();
    @FXML
    private PasswordField passwordFieldPassword = new PasswordField();
    @FXML
    private Button buttonSignIn = new Button();
    @FXML
    private CheckBox checkBoxRegistration = new CheckBox();
    @FXML
    private void setButtonSignIn() {
        if (checkBoxRegistration.isSelected()) {
            logger.info("Чекбокс нажат");
        } else {
            logger.info("Чекбокс не нажат");
        }
    }
}