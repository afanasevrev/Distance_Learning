module com.example.client_correct {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.client_correct to javafx.fxml;
    exports com.example.client_correct;
}