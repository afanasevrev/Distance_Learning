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
            if (getAuth.equals("AUTHENTICATED_ADMIN") || getAuth.equals("AUTHENTICATED_STUDENT")) {
                ClientController.authenticated = getAuth;
                try {
                    mainApplication.start(stage);
                } catch (IOException e) {
                    logger.error(e);
                }
                Stage stage = (Stage) signIn.getScene().getWindow();
                stage.close();
            } else {
                logs.setText("");
                logs.setText("Неверный логин или пароль");
            }
        }
    }
    /**
     * Метод отправляет запрос на сервер
     * запрос на аутентификацию и получает ответ
     * @param login - логин
     * @param password - пароль
     * @return AUTHENTICATED_ADMIN, AUTHENTICATED_STUDENT, NOT_AUTHENTICATED
     */
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
    /**
     * Метод используется для регистрации студента
     */
    @FXML
    private void registrationStudent() {
        if (!name.getText().isEmpty() && !surname.getText().isEmpty() && !patronymic.getText().isEmpty() && !login.getText().isEmpty() && !password.getText().isEmpty())
        {
            String getRegistration = registration(name.getText(), surname.getText(), patronymic.getText(), login.getText(), password.getText());
            if (getRegistration.equals("REGISTERED_STUDENT")) {
                logs.setText("");
                logs.setText("Ученик успешно зарегистрирован");
            } else {
                logs.setText("");
                logs.setText("Ученик с таким логином уже существует, задайте другой");
            }
        } else {
            logs.setText("");
            logs.setText("Заполните все поля для регистрации");
        }
    }
    /**
     * Метод отправляет на сервер запрос на регистрацию нового пользователя
     * и получает соответствующий ответ
     * @param surname - фамилия
     * @param name - имя
     * @param patronymic - отчество
     * @param login - логин
     * @param password - пароль
     * @return REGISTERED_STUDENT, NOT_REGISTERED
     */
    private String registration(String surname, String name, String patronymic, String login, String password) {
        String url_registration = this.url + "/registration/" + surname + "&" + name + "&" + patronymic + "&" + login + "&" + password;
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(url_registration, HttpMethod.GET, null, String.class);
            logger.info(response.getBody());
        } catch (RuntimeException e) {
            logger.error(e);
        }
        return response.getBody();
    }
}
