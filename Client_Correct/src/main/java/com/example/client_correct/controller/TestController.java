package com.example.client_correct.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Класс для загрузки теста в форму
 */
public class TestController implements Initializable {
    @FXML
    private Label question = new Label();
    @FXML
    private Label questionNumber = new Label();
    @FXML
    private RadioButton radioButtonReply_1 = new RadioButton();
    @FXML
    private RadioButton radioButtonReply_2 = new RadioButton();
    @FXML
    private RadioButton radioButtonReply_3 = new RadioButton();
    @FXML
    private Button buttonSetQuestion = new Button();
    @FXML
    private Button buttonStartTest = new Button();
    private ToggleGroup toggleGroup = new ToggleGroup();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        radioButtonReply_1.setToggleGroup(toggleGroup);
        radioButtonReply_2.setToggleGroup(toggleGroup);
        radioButtonReply_3.setToggleGroup(toggleGroup);
    }
}
