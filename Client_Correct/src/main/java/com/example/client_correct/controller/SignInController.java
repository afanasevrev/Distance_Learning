package com.example.client_correct.controller;

import com.example.client_correct.ClientCorrectApplication;
import com.example.client_correct.authorization.Login;
import com.example.client_correct.variables.Variables;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.io.IOException;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
/**
 * Контроллер для входа в систему
 */
public class SignInController {
    //Создаем экземпляр класса RestTemplate
    private RestTemplate restTemplate = new RestTemplate();
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
            try {
                String url_login = "http://" + Variables.ip_server + ":" + Variables.port_server + "/authenticate";
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                Login login = new Login(textFieldLogin.getText(), passwordFieldPassword.getText());
                HttpEntity<Login> request = new HttpEntity<>(login, headers);
                ResponseEntity<Login> response = restTemplate.exchange(url_login, HttpMethod.POST, request, Login.class);
                if (response.getBody().getAuthentic_status().equals("AUTHENTICATED_ADMIN")) {
                    FXMLLoader fxmlLoader = new FXMLLoader(ClientCorrectApplication.class.getResource("admins_page.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 800, 700);
                    stage.setTitle("Администратор");
                    stage.setScene(scene);
                    stage.show();
                } else if (response.getBody().getAuthentic_status().equals("AUTHENTICATED_STUDENT")) {
                    FXMLLoader fxmlLoader = new FXMLLoader(ClientCorrectApplication.class.getResource("clients_page.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 800, 485);
                    stage.setTitle(response.getBody().getLogin());
                    stage.setScene(scene);
                    stage.show();
                    Variables.student_id = response.getBody().getStudent_id();
                    Variables.scores = 0;
                    Variables.type = response.getBody().getType();
                    Variables.category = response.getBody().getCategory();
                }
                Stage stage1 = (Stage) buttonSignIn.getScene().getWindow();
                stage1.close();
            } catch(RuntimeException e) {
                logger.error("Сервер не доступен");
            }
        }
    }
}