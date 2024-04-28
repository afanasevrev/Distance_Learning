package com.example.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Класс для главного окна приложения
 * реализуем его по паттерну Singleton
 */
public class MainApplication {
    private static MainApplication Instance;
    private MainApplication(){}
    public synchronized static MainApplication getInstance() {
        if (Instance == null) {
            Instance = new MainApplication();
        }
        return Instance;
    }
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Distance learning!");
        stage.setScene(scene);
        stage.show();
    }
}
