package com.example.client_correct.controller;

import com.example.client_correct.material.ListOfMaterials;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
/**
 * Класс контроллер для взаимодействия с формой "clients_page.fxml"
 */
public class ClientsPageController {
    //-----------------------------------------------------------------------------//
    //Элементы вкладки "Учебные материалы"
    private String valueOfMaterial;
    @FXML
    private TableView<ListOfMaterials> tableViewMaterials = new TableView<ListOfMaterials>();
    private ObservableList<ListOfMaterials> listOfMaterialsData = FXCollections.<ListOfMaterials>observableArrayList();
    
}
