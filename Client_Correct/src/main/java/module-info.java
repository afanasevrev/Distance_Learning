module com.example.client_correct {
    requires javafx.controls;
    requires javafx.fxml;
    requires log4j;
    requires static lombok;
    requires spring.web;
    requires spring.core;
    requires com.google.gson;
    opens com.example.client_correct to javafx.fxml;
    opens com.example.client_correct.material to com.google.gson;
    exports com.example.client_correct;
    exports com.example.client_correct.controller;
    exports com.example.client_correct.test;
    exports com.example.client_correct.students;
    exports com.example.client_correct.authorization;
    exports com.example.client_correct.video;
    exports com.example.client_correct.material;
    opens com.example.client_correct.controller to javafx.fxml;

}