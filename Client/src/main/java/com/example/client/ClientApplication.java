package com.example.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.BasicConfigurator;
import java.io.IOException;
/**
 * Точка входа в приложение
 */
public class ClientApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("sign_in.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Distance learning!");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        BasicConfigurator.configure();
        launch();
    }
}