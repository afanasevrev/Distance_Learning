package com.example.client.controller;

import com.example.client.Direction;
import com.example.client.MainApplication;
import com.example.client.Variables;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
/**
 * Класс контроллер предназначен для входа в систему и регистрации
 */
public class SignInController {
    //Указываем url сервера, к которому будем подключаться
    private String url = "http://" + Variables.ip_server + ":" + Variables.port_server;
    //Создаем экземпляр класса RestTemplate, чтобы отправить запросы на сервер
    private RestTemplate restTemplate = new RestTemplate();
    //Создаем экземпляр класса MainApplication
    private MainApplication mainApplication = MainApplication.getInstance();
    //Добавляем Logger;
    Logger logger = Logger.getLogger(MainApplication.class);
    //Добавляем Stage
    Stage stage = new Stage();
    @FXML
    private Tab adminsTab = new Tab();
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
     * аутентификацию, если все нормально, то открываем главное
     * окно приложения
     */
    @FXML
    private void signInApplication() {
        if (!login.getText().isEmpty() && !password.getText().isEmpty()) {
            String getAuth = authentication(login.getText(), password.getText());
            if(getAuth.equals(Direction.AUTHENTICATED_ADMIN)) {
                try {
                    mainApplication.start(stage);
                } catch (IOException e) {
                    logger.error(e);
                }
            } else if (getAuth.equals(Direction.AUTHENTICATED_STUDENT)) {
                try {
                    mainApplication.start(stage);
                } catch (IOException e) {
                    logger.error(e);
                }
            } else {
                logs.setText("Неверный логин или пароль");
            }
        }
    }
    private String authentication(String login, String password) {
        String url_authentication = this.url + "/authenticate/" + login + "&" + password;
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(url_authentication, HttpMethod.GET, null, String.class);
            logger.info(response.getBody());
        } catch (RuntimeException e) {
            logger.error(e);
        }
        return response.getBody();
    }
}
