package com.example.client_correct.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
/**
 * Класс для загрузки теста в форму
 */
public class TestController {
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
}
