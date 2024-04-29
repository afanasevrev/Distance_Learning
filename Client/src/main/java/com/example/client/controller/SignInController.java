package com.example.client.controller;

import com.example.client.MainApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import org.apache.log4j.Logger;
/**
 * Класс контроллер предназначен для входа в систему и регистрации
 */
public class SignInController {
    //Создаем экземпляр класса MainApplication
    private MainApplication mainApplication = MainApplication.getInstance();
    //Добавляем Logger;
    Logger logger = Logger.getLogger(MainApplication.class);
    //Добавляем Stage
    Stage stage = new Stage();
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
    /**
     * При нажатии кнопки "Войти в систему" проходим
     * атуенти
     */
    @FXML
    private void signInApplication() {
        try {
            mainApplication.start(stage);
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
