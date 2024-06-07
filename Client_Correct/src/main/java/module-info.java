module com.example.client_correct {
    requires javafx.controls;
    requires javafx.fxml;
    requires log4j;
    requires static lombok;
    requires spring.web;
    requires spring.core;


    opens com.example.client_correct to javafx.fxml;
    exports com.example.client_correct;
    exports com.example.client_correct.controller;
    exports com.example.client_correct.test;
    opens com.example.client_correct.controller to javafx.fxml;
}