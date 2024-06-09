package com.example.client_correct.controller;

import com.example.client_correct.ClientCorrectApplication;
import com.example.client_correct.material.ListOfMaterial;
import com.example.client_correct.material.ListOfMaterialTemp;
import com.example.client_correct.variables.Variables;
import com.example.client_correct.video.ListOfVideo;
import com.example.client_correct.video.VideoLinksTemp;
import com.google.gson.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * Класс контроллер для взаимодействия с формой "clients_page.fxml"
 */
public class ClientsPageController implements Initializable {
    private Logger logger = Logger.getLogger(ClientsPageController.class);
    //Создаем экземпляр класса RestTemplate
    private RestTemplate restTemplate = new RestTemplate();
    //Создаем экземпляр класса Gson
    private Gson gson = new Gson();
    private Stage stage = new Stage();
    //-----------------------------------------------------------------------------//
    //Элементы вкладки "Учебные материалы"
    private String valueOfMaterial;
    @FXML
    private TableView<ListOfMaterial> tableViewMaterials = new TableView<ListOfMaterial>();
    private ObservableList<ListOfMaterial> listOfMaterialsData = FXCollections.<ListOfMaterial>observableArrayList();
    @FXML
    private TableColumn<ListOfMaterial, String> tableColumnMaterialId = new TableColumn<ListOfMaterial, String>("№");
    @FXML
    private TableColumn<ListOfMaterial, String> tableColumnMaterialName = new TableColumn<ListOfMaterial, String>("Наименование");
    @FXML
    private Button buttonUpdateList = new Button();
    @FXML
    private void setButtonUpdateList() {
        String category = "category4";
        if (Variables.category == 4) category = "category4";
        else if(Variables.category == 5) category = "category5";
        else if(Variables.category == 6) category = "category6";
        String url_getListMaterials = "http://" + Variables.ip_server + ":" + Variables.port_server + "/materials/" + category;
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(url_getListMaterials, HttpMethod.GET, null, String.class);
            listOfMaterialsData.clear();
            JsonParser jsonParser = new JsonParser();
            try {
                JsonArray jsonArray = jsonParser.parse(response.getBody()).getAsJsonArray();
                for(JsonElement jsonElement: jsonArray) {
                    ListOfMaterialTemp listOfMaterialTemp = gson.fromJson(jsonElement, ListOfMaterialTemp.class);
                    ListOfMaterial listOfMaterial = new ListOfMaterial(listOfMaterialTemp.getId(), listOfMaterialTemp.getName());
                    listOfMaterialsData.add(listOfMaterial);
                }
            } catch (JsonSyntaxException e) {
                logger.error(e);
            }
        } catch (RuntimeException e) {
            logger.error(e);
        }
    }
    @FXML
    private Button buttonDownloadMaterial = new Button();
    /**
     * Реализация кнопки "скачать материал"
     */
    @FXML
    private void setButtonDownloadMaterial() {
        if (valueOfMaterial != null && !valueOfMaterial.isEmpty()) {
            String category = "category4";
            if (Variables.category == 4) category = "category4";
            else if (Variables.category == 5) category = "category5";
            else if (Variables.category == 6) category = "category6";
            //Определяем наш домашний каталог
            String userHome = System.getProperty("user.home");
            //Сгенерируем случайное название нашего файла
            String fileName = Variables.generateRandomFileName(20);
            String pdfId = valueOfMaterial;
            String url_download_material = "http://" + Variables.ip_server + ":" + Variables.port_server + "/getPdfFile/" + category + "/" + pdfId;
            ResponseEntity<byte[]> response = null;
            try {
                response = restTemplate.exchange(url_download_material, HttpMethod.GET, null, new ParameterizedTypeReference<byte[]>() {});
            } catch (RuntimeException e) {
                logger.error(e);
            }
            try {
                //Путь к файлу, который вы хотите создать
                String outputPath = userHome + "\\Downloads\\" + fileName + ".pdf";
                //Массив байтов, который необходимо записать в файл
                byte[] pdfBytes = response.getBody();
                //Создание потока вывода файла для записи данных в файл
                try (FileOutputStream fos = new FileOutputStream(outputPath)) {
                    fos.write(pdfBytes);
                }
                logger.info("PDF файл успешно создан: " + outputPath);
            } catch (IOException e) {
                logger.error("Ошибка при создании PDF файла: " + e.getMessage());
            }
        }
    }
    @FXML
    private Button buttonGetTest = new Button();
    /**
     * Реализуем кнопку "Пройти тест"
     */
    @FXML
    private void setButtonGetTest() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientCorrectApplication.class.getResource("test.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Тест");
        stage.setScene(scene);
        stage.show();
    }
    //-----------------------------------------------------------------------------//
    //Элементы вкладки "Видеоуроки"
    private String valueOfVideo;
    @FXML
    private TableView<ListOfVideo> tableViewListOfVideo = new TableView<ListOfVideo>();
    private ObservableList<ListOfVideo> listOfVideoData = FXCollections.<ListOfVideo>observableArrayList();
    @FXML
    private TableColumn<ListOfVideo, String> tableColumnVideoId = new TableColumn<ListOfVideo, String>("№");
    @FXML
    private TableColumn<ListOfVideo, String> tableColumnVideoName = new TableColumn<ListOfVideo, String>("Наименование");
    @FXML
    private TableColumn<ListOfVideo, String> tableColumnVideoLink = new TableColumn<ListOfVideo, String>("Ссылка");
    @FXML
    private Button buttonUpdateListOfVideo = new Button();
    /**
     * Реализация кнопки "Обновить список видео"
     */
    @FXML
    private void setButtonUpdateListOfVideo() {
        String url_getLinkVideos = "http://" + Variables.ip_server + ":" + Variables.port_server + "/videos";
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(url_getLinkVideos, HttpMethod.GET, null, String.class);
            listOfVideoData.clear();
            JsonParser jsonParser = new JsonParser();
            try {
                JsonArray jsonArray = jsonParser.parse(response.getBody()).getAsJsonArray();
                for(JsonElement jsonElement: jsonArray) {
                    VideoLinksTemp videoLinksTemp = gson.fromJson(jsonElement, VideoLinksTemp.class);
                    ListOfVideo listOfVideo = new ListOfVideo(videoLinksTemp.id, videoLinksTemp.name, videoLinksTemp.link);
                    listOfVideoData.add(listOfVideo);
                }
            } catch (JsonSyntaxException e) {
                logger.error(e);
            }
        } catch (RuntimeException e) {
            logger.error(e);
        }
    }
    @FXML
    private Button buttonLinkInVideo = new Button();
    /**
     * Реализуем кнопку "Перейти по ссылке" для видеоуроков
     */
    @FXML
    private void setButtonLinkInVideo() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            ProcessBuilder pb;
            if (os.contains("win")) {
                // Для Windows
                pb = new ProcessBuilder("cmd.exe", "/c", "start", valueOfVideo);
            } else if (os.contains("mac")) {
                // Для macOS
                pb = new ProcessBuilder("open", valueOfVideo);
            } else {
                // Для Linux или других ОС
                pb = new ProcessBuilder("xdg-open", valueOfVideo);
            }
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //-----------------------------------------------------------------------------//
    //Элементы вкладки "Материалы по оружию"

    //-----------------------------------------------------------------------------//
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Обновляем таблицу для видеоуроков
        tableViewListOfVideo.setItems(listOfVideoData);
        tableColumnVideoId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        tableColumnVideoName.setCellValueFactory(cellData -> cellData.getValue().videoNameProperty());
        tableColumnVideoLink.setCellValueFactory(cellData -> cellData.getValue().linkInVideoProperty());
        //Фиксируем строку в таблице для видеоматериалов
        tableViewListOfVideo.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            try {
                valueOfVideo = newSelection.getLinkInVideo();
            } catch (NullPointerException e) {
                valueOfVideo = null;
            }
        });
        tableViewMaterials.setItems(listOfMaterialsData);
        tableColumnMaterialId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        tableColumnMaterialName.setCellValueFactory(cellData -> cellData.getValue().materialNameProperty());
        //Фиксируем строку в таблице для учебных материалов
        tableViewMaterials.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            try {
                valueOfMaterial = newSelection.getId();
            } catch (NullPointerException e) {
                valueOfMaterial = null;
            }
        });
    }
}
