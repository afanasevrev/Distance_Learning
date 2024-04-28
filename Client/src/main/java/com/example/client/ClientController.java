package com.example.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

/**
 * Класс контроллер для взаимодействия с основной формой FX
 */
public class ClientController {
    //Элементы вкладки "Учебные материалы"
    @FXML
    private Button updateList = new Button();
    @FXML
    private Button downloadMaterial = new Button();
    @FXML
    private TableView tableViewListOfMaterial = new TableView();
    private ObservableList<ListOfMaterial> listOfMaterialData = FXCollections.<ListOfMaterial>observableArrayList();
    @FXML
    TableColumn<ListOfMaterial, String> id_material = new TableColumn<ListOfMaterial, String>("№");
    @FXML
    TableColumn<ListOfMaterial, String> materialName = new TableColumn<ListOfMaterial, String>("Наименование");
    //_____________________________________________________________________________________________________________//
    //_____________________________________________________________________________________________________________//
    //Элементы вкладки "Видеоуроки"
    @FXML
    private Button updateListVideo = new Button();
    @FXML
    private Button linkInVideo = new Button();
    @FXML
    private TableView tableViewListOfVideo = new TableView();
    private ObservableList<ListOfVideo> listOfVideoData = FXCollections.<ListOfVideo>observableArrayList();
    @FXML
    TableColumn<ListOfVideo, String> id_video = new TableColumn<ListOfVideo, String>("№");
    @FXML
    TableColumn<ListOfVideo, String> videoName = new TableColumn<ListOfVideo, String>("Наименование");
    @FXML
    TableColumn<ListOfVideo, String> videoLink = new TableColumn<ListOfVideo, String>("Ссылка");
    //_____________________________________________________________________________________________________________//
    //_____________________________________________________________________________________________________________//
    //Элементы вкладки "Администратор"
    @FXML
    private TextField loginAdministrator = new TextField();
    @FXML
    private PasswordField passwordAdministrator = new PasswordField();
    @FXML
    private Button createAdministrator = new Button();
    @FXML
    private TextField linkInVideoYoutube = new TextField();
    @FXML
    private Button createVideo = new Button();
    @FXML
    private Button createFileMaterial = new Button();
    private FileChooser fileChooser = new FileChooser();
    @FXML
    private TableView tableViewStudents = new TableView();
    @FXML
    private TableColumn<>
}