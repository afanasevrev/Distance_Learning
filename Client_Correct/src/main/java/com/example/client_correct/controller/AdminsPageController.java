package com.example.client_correct.controller;

import com.example.client_correct.students.Students;
import com.example.client_correct.students.StudentsData;
import com.example.client_correct.test.TestSecurity;
import com.example.client_correct.variables.Variables;
import com.example.client_correct.video.ListOfVideoTemp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
/**
 * Класс контроллер для взаимодействия с формой "admins_page.fxml"
 */
public class AdminsPageController implements Initializable {
    private String valueOfStudent;
    private FileChooser fileChooser = new FileChooser();
    private Logger logger = Logger.getLogger(AdminsPageController.class);
    //Создаем экземпляр класса RestTemplate
    private RestTemplate restTemplate = new RestTemplate();
    @FXML
    private TextField textFieldSetLogin = new TextField();
    @FXML
    private PasswordField passwordFieldSetPassword = new PasswordField();
    //---------------------------------------------------------------------------------//
    @FXML
    private Button buttonSetAdmin = new Button();
    /**
     * Реализация кнопки "Добавить администратора"
     */
    @FXML
    private void setButtonSetAdmin() {
        if (!textFieldSetLogin.getText().isEmpty() && !passwordFieldSetPassword.getText().isEmpty()) {
            String loginAdmin = textFieldSetLogin.getText();
            String passwordAdmin = passwordFieldSetPassword.getText();
            String url_getAdmin = "http://" + Variables.ip_server + ":" + Variables.port_server + "/setAdministrator/" + loginAdmin + "&" +passwordAdmin;
            ResponseEntity<String> response = null;
            try {
                response = restTemplate.exchange(url_getAdmin, HttpMethod.GET, null, String.class);
                String text = response.getBody();
                if (text.equals("REGISTERED_ADMIN")) {
                    logger.info("Новый администратор успешно добавлен!");
                } else if (text.equals("NOT_REGISTERED")) {
                    logger.info("Администратор с таким логином уже существует!");
                } else {
                    logger.error("Непредвиденная ошибка");
                }
                textFieldSetLogin.setText("");
                passwordFieldSetPassword.setText("");
            } catch (RuntimeException e) {
                logger.error(e);
            }
        }
    }
    //---------------------------------------------------------------------------------//
    @FXML
    private TextField textFieldVideoName = new TextField();
    @FXML
    private TextField textFieldLinkInVideo = new TextField();
    @FXML
    private Button buttonSetLinkInVideo = new Button();
    /**
     * Реализация кнопки "Отправить ссылку на видео"
     */
    @FXML
    private void setButtonSetLinkInVideo() {
        if (!textFieldVideoName.getText().isEmpty() && !textFieldLinkInVideo.getText().isEmpty()) {
            String videoName = textFieldVideoName.getText();
            String linkInVideo = textFieldLinkInVideo.getText();
            String url_create_video = "http://" + Variables.ip_server + ":" + Variables.port_server + "/createVideo";
            ResponseEntity<String> response = null;
            ListOfVideoTemp listOfVideoTemp = new ListOfVideoTemp(videoName, linkInVideo);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<ListOfVideoTemp> entity = new HttpEntity<>(listOfVideoTemp, headers);
            try {
                response = restTemplate.exchange(url_create_video, HttpMethod.POST, entity, String.class);
                logger.info(response.getBody());
                textFieldVideoName.setText("");
                textFieldLinkInVideo.setText("");
            } catch (RuntimeException e) {
                logger.error(e);
            }
        }
    }
    //---------------------------------------------------------------------------------//
    @FXML
    private TextField textFieldMaterialName = new TextField();
    @FXML
    private ComboBox<String> comboBoxCategory = new ComboBox<String>();
    @FXML
    private Button buttonSetMaterial = new Button();
    /**
     * Реализация кнопки "Добавить материал на сервер"
     */
    @FXML
    private void setButtonSetMaterial() throws IOException {
        if (!textFieldMaterialName.getText().isEmpty() && !comboBoxCategory.getValue().isEmpty() && comboBoxCategory.getValue() != null) {
            String textCreateMaterialName = textFieldMaterialName.getText();
            String categoryName = "category4";
            if (comboBoxCategory.getValue().equals("4")) categoryName = "category4";
            else if (comboBoxCategory.getValue().equals("5")) categoryName = "category5";
            else if (comboBoxCategory.getValue().equals("6")) categoryName = "category6";
            String url_upload = "http://" + Variables.ip_server + ":" + Variables.port_server + "/upload/" + categoryName + "/" + textCreateMaterialName;
            fileChooser.setTitle("Выберите файл");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                logger.info("Выбран файл: " + file.getAbsolutePath());
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                Path path = Paths.get(file.getAbsolutePath());
                byte[] pdfContents = Files.readAllBytes(path);
                HttpEntity<byte[]> entity = new HttpEntity<>(pdfContents, headers);
                ResponseEntity<String> response = restTemplate.exchange(url_upload, HttpMethod.POST, entity, String.class);
                logger.info(response.getBody());
            }
        }
    }
    //---------------------------------------------------------------------------------//
    @FXML
    private TextField textFieldMaterialArmName = new TextField();
    @FXML
    private ComboBox<String> comboBoxType =new ComboBox<String>();
    @FXML
    private Button buttonSetMaterialArm = new Button();
    /**
     * Реализация кнопки "Добавить материал по виду оружия на сервер
     */
    @FXML
    private void setButtonSetMaterialArm() throws IOException {
        if (!textFieldMaterialArmName.getText().isEmpty() && !comboBoxType.getValue().isEmpty() && comboBoxType.getValue() != null) {
            String textCreateMaterialName = textFieldMaterialArmName.getText();
            String typeName = "pistols";
            if (comboBoxType.getValue().equals("Пистолет")) typeName = "pistols";
            else if (comboBoxType.getValue().equals("Помповое")) typeName = "pumps";
            else if (comboBoxType.getValue().equals("Гладкоствольное")) typeName = "smoothBore";
            String url_upload = "http://" + Variables.ip_server + ":" + Variables.port_server + "/upload/" + typeName + "/" + textCreateMaterialName;
            fileChooser.setTitle("Выберите файл");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                logger.info("Выбран файл: " + file.getAbsolutePath());
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                Path path = Paths.get(file.getAbsolutePath());
                byte[] pdfContents = Files.readAllBytes(path);
                HttpEntity<byte[]> entity = new HttpEntity<>(pdfContents, headers);
                ResponseEntity<String> response = restTemplate.exchange(url_upload, HttpMethod.POST, entity, String.class);
                logger.info(response.getBody());
            }
        }
    }
    //---------------------------------------------------------------------------------//
    @FXML
    private TableView<StudentsData> tableViewStudents = new TableView<StudentsData>();
    private ObservableList<StudentsData> studentsData = FXCollections.<StudentsData>observableArrayList();
    @FXML
    private TableColumn<StudentsData, String> tableColumnStudentId = new TableColumn<StudentsData, String>("№");
    @FXML
    private TableColumn<StudentsData, String> tableColumnStudentFirstName = new TableColumn<StudentsData, String>("Фамилия");
    @FXML
    private TableColumn<StudentsData, String> tableColumnStudentMiddleName = new TableColumn<StudentsData, String>("Имя");
    @FXML
    private TableColumn<StudentsData, String> tableColumnStudentLastName = new TableColumn<StudentsData, String>("Отчество");
    @FXML
    private TableColumn<StudentsData, String> tableColumnStudentTelephoneNumber = new TableColumn<StudentsData, String>("Тел.");
    @FXML
    private TableColumn<StudentsData, String> tableColumnStudentEmail = new TableColumn<StudentsData, String>("Email");
    @FXML
    private TableColumn<StudentsData, String> tableColumnStudentCategory = new TableColumn<StudentsData, String>("Разряд");
    @FXML
    private TableColumn<StudentsData, String> tableColumnStudentType = new TableColumn<StudentsData, String>("Вид оружия");
    @FXML
    private TableColumn<StudentsData, String> tableColumnStudentTest = new TableColumn<StudentsData, String>("Тест");
    @FXML
    private Button buttonUpdateStudentsList = new Button();
    /**
     * Реализация кнопки "Обновить"
     */
    @FXML
    private void setButtonUpdateStudentsList() {
        String url_getStudentsList = "http://" + Variables.ip_server + ":" + Variables.port_server + "/students";
        studentsData.clear();
        ResponseEntity<List<Students>> response = restTemplate.exchange(url_getStudentsList, HttpMethod.GET, null, new ParameterizedTypeReference<List<Students>>() {});
        for (Students student: response.getBody()) {
            StudentsData studentData = new StudentsData(String.valueOf(student.getId()), student.getFirst_name(), student.getMiddle_name(), student.getLast_name(), student.getLogin(), student.getPassword(), student.getBirth(), student.getEmail(), String.valueOf(student.getCategory()), student.getType(), student.getTelephone_number(), student.getPass());
            studentsData.add(studentData);
        }
    }
    @FXML
    private Button buttonDismiss = new Button();
    /**
     * Реализация кнопки "Отчислить"
     */
    @FXML
    private void setButtonDismiss() {
        if (valueOfStudent != null && !valueOfStudent.isEmpty()) {
            String studentId = valueOfStudent;
            String url_student_dismiss = "http://" + Variables.ip_server + ":" + Variables.port_server + "/deleteStudent/" + studentId;
            ResponseEntity<String> response = null;
            try {
                response = restTemplate.exchange(url_student_dismiss, HttpMethod.GET, null, String.class);
                String text = response.getBody();
                logger.info(text);
            } catch (RuntimeException e) {
                logger.error(e);
            }
        }
    }
    //---------------------------------------------------------------------------------//
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBoxCategory.getItems().addAll("4", "5", "6");
        comboBoxType.getItems().addAll("Пистолет", "Помповое", "Гладкоствольное");
        //Обновляем таблицу для учеников
        tableViewStudents.setItems(studentsData);
        tableColumnStudentId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        tableColumnStudentFirstName.setCellValueFactory(cellData -> cellData.getValue().first_nameProperty());
        tableColumnStudentMiddleName.setCellValueFactory(cellData -> cellData.getValue().middle_nameProperty());
        tableColumnStudentLastName.setCellValueFactory(cellData -> cellData.getValue().last_nameProperty());
        tableColumnStudentTelephoneNumber.setCellValueFactory(cellData -> cellData.getValue().telephone_numberProperty());
        tableColumnStudentEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        tableColumnStudentCategory.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
        tableColumnStudentType.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        tableColumnStudentTest.setCellValueFactory(cellData -> cellData.getValue().passProperty());
        //Фиксируем строку в таблице для учеников
        tableViewStudents.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            try {
                valueOfStudent = newSelection.getId();
            } catch (NullPointerException e) {
                valueOfStudent = null;
            }
        });
    }
}
