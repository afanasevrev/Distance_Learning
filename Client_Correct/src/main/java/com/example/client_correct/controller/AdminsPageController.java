package com.example.client_correct.controller;

import com.example.client_correct.students.Students;
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
    private ObservableList<Students> studentsData = FXCollections.<Students>observableArrayList();
    @FXML
    private TableColumn<Students, String> tableColumnStudentId = new TableColumn<Students, String>("№");
    @FXML
    private TableColumn<Students, String> tableColumnStudentFirstName = new TableColumn<Students, String>("Фамилия");
    @FXML
    private TableColumn<Students, String> tableColumnStudentMiddleName = new TableColumn<Students, String>("Имя");
    @FXML
    private TableColumn<Students, String> tableColumnStudentLastName = new TableColumn<Students, String>("Отчество");
    @FXML
    private TableColumn<Students, String> tableColumnStudentTelephoneNumber = new TableColumn<Students, String>("Тел.");
    @FXML
    private TableColumn<Students, String> tableColumnStudentEmail = new TableColumn<Students, String>("Email");
    @FXML
    private TableColumn<Students, String> tableColumnStudentCategory = new TableColumn<Students, String>("Разряд");
    @FXML
    private TableColumn<Students, String> tableColumnStudentType = new TableColumn<Students, String>("Вид оружия");
    @FXML
    private TableColumn<Students, String> tableColumnStudentTest = new TableColumn<Students, String>("Тест");
    @FXML
    private Button buttonUpdateStudentsList = new Button();
    @FXML
    private Button buttonDismiss = new Button();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }
}
