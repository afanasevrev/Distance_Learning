package com.example.client_correct.controller;

import com.example.client_correct.students.Students;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AdminsPageController {
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

}
