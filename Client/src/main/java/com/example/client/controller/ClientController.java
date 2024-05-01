package com.example.client.controller;

import com.example.client.Variables;
import com.example.client.material.ListOfMaterial;
import com.example.client.material.ListOfMaterialTemp;
import com.example.client.video.ListOfVideo;
import com.example.client.students.Students;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import com.google.gson.*;
/**
 * Класс контроллер для взаимодействия с основной формой FX
 */
public class ClientController implements Initializable {
    //Будем вести лог событий
    Logger  logger = Logger.getLogger(ClientController.class);
    //Будем преобразовать JSON в классы
    Gson gson = new Gson();
    //Создаем экземпляр класса RestTemplate
    RestTemplate restTemplate = new RestTemplate();
    //Добавим переменную, в которой укажем, зашёл ли пользователь под правами администратора или нет
    public static String authenticated = "NOT_AUTHENTICATED";
    //_____________________________________________________________________________________________________________//
    //Элементы вкладки "Учебные материалы"
    @FXML
    private Button updateList = new Button();
    @FXML
    private Button downloadMaterial = new Button();
    @FXML
    private TableView tableViewListOfMaterial = new TableView();
    private ObservableList<ListOfMaterial> listOfMaterialData = FXCollections.<ListOfMaterial>observableArrayList();
    @FXML
    TableColumn<ListOfMaterial, String> id_material = new TableColumn<ListOfMaterial, String>("№");
    @FXML
    TableColumn<ListOfMaterial, String> materialName = new TableColumn<ListOfMaterial, String>("Наименование");
    //_____________________________________________________________________________________________________________//
    //Элементы вкладки "Видеоуроки"
    @FXML
    private Button updateListVideo = new Button();
    @FXML
    private Button linkInVideo = new Button();
    @FXML
    private TableView tableViewListOfVideo = new TableView();
    private ObservableList<ListOfVideo> listOfVideoData = FXCollections.<ListOfVideo>observableArrayList();
    @FXML
    TableColumn<ListOfVideo, String> id_video = new TableColumn<ListOfVideo, String>("№");
    @FXML
    TableColumn<ListOfVideo, String> videoName = new TableColumn<ListOfVideo, String>("Наименование");
    @FXML
    TableColumn<ListOfVideo, String> videoLink = new TableColumn<ListOfVideo, String>("Ссылка");
    //_____________________________________________________________________________________________________________//
    //Элементы вкладки "Администратор"
    @FXML
    private TextField loginAdministrator = new TextField();
    @FXML
    private PasswordField passwordAdministrator = new PasswordField();
    @FXML
    private Button createAdministrator = new Button();
    @FXML
    private TextField linkInVideoYoutube = new TextField();
    @FXML
    private Button createVideo = new Button();
    @FXML
    private Button createFileMaterial = new Button();
    //Открыть диалоговое окно, чтобы выбрать файл для отправки на сервер
    private FileChooser fileChooser = new FileChooser();
    @FXML
    private TableView tableViewStudents = new TableView();
    @FXML
    private TableColumn<Students, String> id_student = new TableColumn<Students, String>("№");
    @FXML
    private TableColumn<Students, String> surname = new TableColumn<Students, String>("Фамилия");
    @FXML
    private TableColumn<Students, String> name = new TableColumn<Students, String>("Имя");
    @FXML
    private Button upDateListStudents = new Button();
    @FXML
    private Button dismiss = new Button();
    @FXML
    private Tab adminsTab = new Tab();
    @FXML
    private TextField createMaterialName = new TextField();
    //_____________________________________________________________________________________________________________//
    /**
     * При инициализации проверяем,
     * зашёл ли пользователь под админом или нет
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (authenticated.equals("AUTHENTICATED_ADMIN")) {
            adminsTab.setDisable(false);
        } else {
            adminsTab.setDisable(true);
        }
    }
    /**
     * При нажатии кнопки "Добавить материал" выбираем
     * pdf - файл и отправляем его на сервер
     */
    @FXML
    private void createPdfFile() throws IOException {
        if (!createMaterialName.getText().isEmpty()) {
            String textCreateMaterialName = createMaterialName.getText();
            String url_upload = "http://" + Variables.ip_server + ":" + Variables.port_server + "/upload/" + textCreateMaterialName;
            fileChooser.setTitle("Выберите файл");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                logger.info("Выбран файл: " + file.getAbsolutePath());
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                Path path = Paths.get(file.getAbsolutePath());
                byte[] pdfContents = Files.readAllBytes(path);
                HttpEntity<byte[]> entity = new HttpEntity<>(pdfContents, headers);
                ResponseEntity<String> response = restTemplate.exchange(url_upload, HttpMethod.POST, entity, String.class);
                logger.info(response.getBody());
            }
        }
    }
    /**
     * При нажатии кнопки "Обновить список"
     * отправляем на сервер GET - запрос на получение массива списка
     * и заполняем таблицу FX
     */
    @FXML
    private void getUpdateList() {
        String url_materials = "http://" + Variables.ip_server + ":" + Variables.port_server + "/materials";
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(url_materials, HttpMethod.GET, null, String.class);
            logger.info(response.getBody());
            listOfMaterialData.clear();
            JsonParser jsonParser = new JsonParser();
            try {
                JsonArray jsonArray = jsonParser.parse(response.getBody()).getAsJsonArray();
                for (JsonElement jsonElement: jsonArray) {
                    ListOfMaterialTemp listOfMaterialTemp = gson.fromJson(jsonElement, ListOfMaterialTemp.class);
                    ListOfMaterial listOfMaterial = new ListOfMaterial(String.valueOf(listOfMaterialTemp.getId()), listOfMaterialTemp.getMaterialName());
                    listOfMaterialData.add(listOfMaterial);
                    id_material.setCellValueFactory(cellData -> cellData.getValue().idProperty());
                    materialName.setCellValueFactory(cellData -> cellData.getValue().materialNameProperty());
                }
            } catch (JsonSyntaxException e) {
                logger.error(e);
            }
        } catch (RuntimeException e) {
            logger.error(e);
        }
    }
}