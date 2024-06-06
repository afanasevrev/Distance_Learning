module com.example.client_correct {
    requires javafx.controls;
    requires javafx.fxml;
    requires log4j;
    requires lombok;


    opens com.example.client_correct to javafx.fxml;
    exports com.example.client_correct;
    exports com.example.client_correct.controller;
    opens com.example.client_correct.controller to javafx.fxml;
}