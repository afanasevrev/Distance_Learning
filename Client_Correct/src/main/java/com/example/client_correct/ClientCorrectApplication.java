package com.example.client_correct;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientCorrectApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientCorrectApplication.class.getResource("sign_in.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 305, 265);
        stage.setTitle("Distance Learning");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}