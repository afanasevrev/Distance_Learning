module com.example.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires log4j;
    requires spring.web;
    requires com.google.gson;
    requires spring.core;

    opens com.example.client to javafx.fxml;
    exports com.example.client;
    exports com.example.client.controller;
    opens com.example.client.controller to javafx.fxml;
    exports com.example.client.material;
    opens com.example.client.material to javafx.fxml;
    exports com.example.client.video;
    opens com.example.client.video to javafx.fxml;
    exports com.example.client.students;
    opens com.example.client.students to javafx.fxml;
}