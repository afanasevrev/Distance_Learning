package com.example.client_correct.controller;

import com.example.client_correct.students.Students;
import com.example.client_correct.students.StudentsData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * Класс контроллер для взаимодействия с формой "admins_page.fxml"
 */
public class AdminsPageController implements Initializable {
    @FXML
    private TextField textFieldSetLogin = new TextField();
    @FXML
    private PasswordField passwordFieldSetPassword = new PasswordField();
    @FXML
    private Button buttonSetAdmin = new Button();
    @FXML
    private TextField textFieldVideoName = new TextField();
    @FXML
    private TextField textFieldLinkInVideo = new TextField();
    @FXML
    private Button buttonSetLinkInVideo = new Button();
    @FXML
    private TextField textFieldMaterialName = new TextField();
    @FXML
    private ComboBox<String> comboBoxCategory = new ComboBox<String>();
    @FXML
    private Button buttonSetMaterial = new Button();
    @FXML
    private TextField textFieldMaterialArmName = new TextField();
    @FXML
    private ComboBox<String> comboBoxType =new ComboBox<String>();
    @FXML
    private Button buttonSetMaterialArm = new Button();
    @FXML
    private TableView<Students> tableViewStudents = new TableView<Students>();
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
    @FXML
    private Button buttonDismiss = new Button();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBoxCategory.getItems().addAll("4", "5", "6");
        comboBoxType.getItems().addAll("Пистолет", "Помповое", "Гладкоствольное");
    }
}
