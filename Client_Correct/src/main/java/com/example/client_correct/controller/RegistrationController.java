package com.example.client_correct.controller;

import com.example.client_correct.ClientCorrectApplication;
import com.example.client_correct.students.Students;
import com.example.client_correct.variables.Variables;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * Форма для регистрации ученика в системе
 */
public class RegistrationController implements Initializable {
    Logger logger = Logger.getLogger(RegisteredController.class);
    Stage stage = new Stage();
    //Создаем экземпляр класса RestTemplate
    private RestTemplate restTemplate = new RestTemplate();
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
    private TextField textFieldTelephoneNumber = new TextField();
    @FXML
    private Button buttonRegistration = new Button();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBoxCategory.getItems().addAll("4", "5", "6");
        comboBoxType.getItems().addAll("Пистолет", "Помповое", "Гладкоствольное");
    }
    /**
     * Реализация кнопки "Зарегистрироваться"
     */
    @FXML
    private void setButtonRegistration() throws IOException {
        if (filledIn()) {
            String url_registration = "http://" + Variables.ip_server + ":" + Variables.port_server + "/registration";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Students student = new Students();
            student.setFirst_name(textFieldFirstName.getText());
            student.setMiddle_name(textFieldMiddleName.getText());
            student.setLast_name(textFieldLastName.getText());
            student.setLogin(textFieldLogin.getText());
            student.setPassword(passwordFieldPassword.getText());
            student.setBirth(datePickerBirth.getValue().toString());
            student.setTelephone_number(textFieldTelephoneNumber.getText());
            student.setEmail(textFieldEmail.getText());
            student.setCategory(Integer.parseInt(comboBoxCategory.getValue()));
            student.setType(comboBoxType.getValue());
            student.setPass("Не сдал");
            HttpEntity<Students> request = new HttpEntity<>(student, headers);
            ResponseEntity<String> response = restTemplate.exchange(url_registration, HttpMethod.POST, request, String.class);
            if (response.getBody().equals("NOT_REGISTERED")) {
                logger.info("Студент с таким логином уже зарегистрирован");
            } else if (response.getBody().equals("REGISTERED_STUDENT")) {
                FXMLLoader fxmlLoader = new FXMLLoader(ClientCorrectApplication.class.getResource("registered.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 200, 100);
                stage.setTitle("Регистрация");
                stage.setScene(scene);
                stage.show();
                Stage stage1 = (Stage) buttonRegistration.getScene().getWindow();
                stage1.close();
            }
        } else {
            logger.info("Заполните все поля");
        }
    }
    /**
     * Метод проверяет, заполнены ли все поля для регистрации студента
     * @return true или false
     */
    private boolean filledIn() {
        if(!textFieldFirstName.getText().isEmpty()
        && !textFieldMiddleName.getText().isEmpty()
        && !textFieldLastName.getText().isEmpty()
        && !textFieldLogin.getText().isEmpty()
        && !passwordFieldPassword.getText().isEmpty()
        && !datePickerBirth.getValue().toString().isEmpty()
        && !textFieldEmail.getText().isEmpty()
        && !comboBoxCategory.getValue().isEmpty()
        && !comboBoxType.getValue().isEmpty()
        && !textFieldTelephoneNumber.getText().isEmpty())
        {
            return true;
        } else {
            return false;
        }
    }
}
