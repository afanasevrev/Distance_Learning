package com.example.client.controller;

import com.example.client.Direction;
import com.example.client.material.ListOfMaterial;
import com.example.client.video.ListOfVideo;
import com.example.client.students.Students;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * Класс контроллер для взаимодействия с основной формой FX
 */
public class ClientController implements Initializable {
    //Добавим переменную, в которой укажем, зашёл ли пользователь под правами администратора или нет
    public static String authenticated = "NOT_AUTHENTICATED";
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
    //Открыть диалоговое окно, чтобы выбрать файл для отправки на сервер
    private FileChooser fileChooser = new FileChooser();
    @FXML
    private TableView tableViewStudents = new TableView();
    @FXML
    private TableColumn<Students, String> id_student = new TableColumn<Students, String>("№");
    @FXML
    private TableColumn<Students, String> surname = new TableColumn<Students, String>("Фамилия");
    @FXML
    private TableColumn<Students, String> name = new TableColumn<Students, String>("Имя");
    @FXML
    private Button upDateListStudents = new Button();
    @FXML
    private Button dismiss = new Button();
    @FXML
    private Tab adminsTab = new Tab();
    //_____________________________________________________________________________________________________________//
    /**
     * При инициализации проверяем,
     * зашёл ли пользователь под админом или нет
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (authenticated.equals("AUTHENTICATED_ADMIN")) {
            adminsTab.setDisable(false);
        } else {
            adminsTab.setDisable(true);
        }
    }
}