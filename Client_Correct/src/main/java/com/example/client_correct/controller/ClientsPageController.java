package com.example.client_correct.controller;

import com.example.client_correct.ClientCorrectApplication;
import com.example.client_correct.material.ListOfMaterial;
import com.example.client_correct.video.ListOfVideo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * Класс контроллер для взаимодействия с формой "clients_page.fxml"
 */
public class ClientsPageController implements Initializable {
    private Stage stage = new Stage();
    //-----------------------------------------------------------------------------//
    //Элементы вкладки "Учебные материалы"
    private String valueOfMaterial;
    @FXML
    private TableView<ListOfMaterial> tableViewMaterials = new TableView<ListOfMaterial>();
    private ObservableList<ListOfMaterial> listOfMaterialsData = FXCollections.<ListOfMaterial>observableArrayList();
    @FXML
    private TableColumn<ListOfMaterial, String> tableColumnMaterialId = new TableColumn<ListOfMaterial, String>("№");
    @FXML
    private TableColumn<ListOfMaterial, String> tableColumnMaterialName = new TableColumn<ListOfMaterial, String>("Наименование");
    @FXML
    private Button buttonUpdateList = new Button();
    @FXML
    private Button buttonDownloadMaterial = new Button();
    @FXML
    private Button buttonGetTest = new Button();
    /**
     * Реализуем кнопку "Пройти тест"
     */
    @FXML
    private void setButtonGetTest() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientCorrectApplication.class.getResource("test.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Тест");
        stage.setScene(scene);
        stage.show();
    }
    //-----------------------------------------------------------------------------//
    //Элементы вкладки "Видеоуроки"
    private String valueOfVideo;
    @FXML
    private TableView<ListOfVideo> tableViewListOfVideo = new TableView<ListOfVideo>();
    private ObservableList<ListOfVideo> listOfVideosData = FXCollections.<ListOfVideo>observableArrayList();
    @FXML
    private TableColumn<ListOfVideo, String> tableColumnVideoId = new TableColumn<ListOfVideo, String>("№");
    @FXML
    private TableColumn<ListOfVideo, String> tableColumnVideoName = new TableColumn<ListOfVideo, String>("Наименование");
    @FXML
    private TableColumn<ListOfVideo, String> tableColumnVideoLink = new TableColumn<ListOfVideo, String>("Ссылка");
    @FXML
    private Button buttonUpdateListOfVideo = new Button();
    @FXML
    private Button buttonLinkInVideo = new Button();
    //-----------------------------------------------------------------------------//
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
