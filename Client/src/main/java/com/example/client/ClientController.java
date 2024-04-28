package com.example.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    TableColumn<ListOfMaterial, String> id = new TableColumn<ListOfMaterial, String>("№");
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
    
}