package com.example.client_correct.controller;

import com.example.client_correct.ClientCorrectApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.stage.Stage;
import org.apache.log4j.Logger;
/**
 * Контроллер для входа в систему
 */
public class SignInController {
    private Stage stage = new Stage();
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
    private void setButtonSignIn() throws IOException {
        if (checkBoxRegistration.isSelected()) {
            FXMLLoader fxmlLoader = new FXMLLoader(ClientCorrectApplication.class.getResource("registration.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 300, 500);
            stage.setTitle("Регистрация");
            stage.setScene(scene);
            stage.show();
        } else {
            logger.info("Чекбокс не нажат");
        }
    }
}