package com.example.client_correct.controller;

import com.example.client_correct.students.Students;
import com.example.client_correct.students.StudentsData;
import com.example.client_correct.test.TestSecurity;
import com.example.client_correct.variables.Variables;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.apache.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
/**
 * Класс контроллер для взаимодействия с формой "admins_page.fxml"
 */
public class AdminsPageController implements Initializable {
    private Logger logger = Logger.getLogger(AdminsPageController.class);
    //Создаем экземпляр класса RestTemplate
    private RestTemplate restTemplate = new RestTemplate();
    @FXML
    private TextField textFieldSetLogin = new TextField();
    @FXML
    private PasswordField passwordFieldSetPassword = new PasswordField();
    @FXML
    private Button buttonSetAdmin = new Button();
    //---------------------------------------------------------------------------------//
    @FXML
    private TextField textFieldVideoName = new TextField();
    @FXML
    private TextField textFieldLinkInVideo = new TextField();
    @FXML
    private Button buttonSetLinkInVideo = new Button();
    //---------------------------------------------------------------------------------//
    @FXML
    private TextField textFieldMaterialName = new TextField();
    @FXML
    private ComboBox<String> comboBoxCategory = new ComboBox<String>();
    @FXML
    private Button buttonSetMaterial = new Button();
    //---------------------------------------------------------------------------------//
    @FXML
    private TextField textFieldMaterialArmName = new TextField();
    @FXML
    private ComboBox<String> comboBoxType =new ComboBox<String>();
    @FXML
    private Button buttonSetMaterialArm = new Button();
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
            
        }
    }
    @FXML
    private Button buttonDismiss = new Button();
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
    }
}
