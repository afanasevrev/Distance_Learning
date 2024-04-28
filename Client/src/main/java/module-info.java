module com.example.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;

    opens com.example.client to javafx.fxml;
    exports com.example.client;
}