package com.example.client.controller;

import com.example.client.Variables;
import com.example.client.material.ListOfMaterial;
import com.example.client.material.ListOfMaterialTemp;
import com.example.client.students.StudentsListTemp;
import com.example.client.video.ListOfVideo;
import com.example.client.students.Students;
import com.example.client.video.ListOfVideoTemp;
import com.example.client.video.VideoLinksTemp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.io.File;
import java.io.FileOutputStream;
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
    private String valueOfMaterial;
    @FXML
    private Button updateList = new Button();
    @FXML
    private Button downloadMaterial = new Button();
    @FXML
    private TableView<ListOfMaterial> tableViewListOfMaterial = new TableView<ListOfMaterial>();
    private ObservableList<ListOfMaterial> listOfMaterialData = FXCollections.<ListOfMaterial>observableArrayList();
    @FXML
    private TableColumn<ListOfMaterial, String> id_material = new TableColumn<ListOfMaterial, String>("№");
    @FXML
    private TableColumn<ListOfMaterial, String> materialName = new TableColumn<ListOfMaterial, String>("Наименование");
    //_____________________________________________________________________________________________________________//
    //Элементы вкладки "Видеоуроки"
    private String valueOfVideo;
    @FXML
    private Button updateListVideo = new Button();
    @FXML
    private Button linkInVideo = new Button();
    @FXML
    private TableView<ListOfVideo> tableViewListOfVideo = new TableView<ListOfVideo>();
    private ObservableList<ListOfVideo> listOfVideoData = FXCollections.<ListOfVideo>observableArrayList();
    @FXML
    TableColumn<ListOfVideo, String> id_video = new TableColumn<ListOfVideo, String>("№");
    @FXML
    TableColumn<ListOfVideo, String> videoName = new TableColumn<ListOfVideo, String>("Наименование");
    @FXML
    TableColumn<ListOfVideo, String> videoLink = new TableColumn<ListOfVideo, String>("Ссылка");
    //_____________________________________________________________________________________________________________//
    //Элементы вкладки "Администратор"
    String valueOfStudent;
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
    private ObservableList<Students> listOfStudentsData = FXCollections.<Students>observableArrayList();
    @FXML
    private TableView<Students> tableViewStudents = new TableView<Students>();
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
    @FXML
    private TextField createVideoName = new TextField();
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
        //Обновляем таблицу для материалов
        tableViewListOfMaterial.setItems(listOfMaterialData);
        id_material.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        materialName.setCellValueFactory(cellData -> cellData.getValue().materialNameProperty());
        //Обновляем таблицу для видеоуроков
        tableViewListOfVideo.setItems(listOfVideoData);
        id_video.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        videoName.setCellValueFactory(cellData -> cellData.getValue().videoNameProperty());
        videoLink.setCellValueFactory(cellData -> cellData.getValue().linkInVideoProperty());
        //Обновляем таблицу для учеников
        tableViewStudents.setItems(listOfStudentsData);
        id_student.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        surname.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());
        //Фиксируем строку в таблице для учебных материалов
        tableViewListOfMaterial.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            try {
                valueOfMaterial = newSelection.getId();
            } catch (NullPointerException e) {
                valueOfMaterial = null;
            }
        });
        //Фиксируем строку в таблице для видеоматериалов
        tableViewListOfVideo.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            try {
                valueOfVideo = newSelection.getLinkInVideo();
            } catch (NullPointerException e) {
                valueOfVideo = null;
            }
        });
        //Фиксируем строку в таблице для учеников
        tableViewStudents.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
           try {
               valueOfStudent = newSelection.getId();
           } catch (NullPointerException e) {
               valueOfStudent = null;
           }
        });
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
            listOfMaterialData.clear();
            JsonParser jsonParser = new JsonParser();
            try {
                JsonArray jsonArray = jsonParser.parse(response.getBody()).getAsJsonArray();
                for (JsonElement jsonElement: jsonArray) {
                    ListOfMaterialTemp listOfMaterialTemp = gson.fromJson(jsonElement, ListOfMaterialTemp.class);
                    ListOfMaterial listOfMaterial = new ListOfMaterial(listOfMaterialTemp.id, listOfMaterialTemp.name);
                    listOfMaterialData.add(listOfMaterial);
                }
            } catch (JsonSyntaxException e) {
                logger.error(e);
            }
        } catch (RuntimeException e) {
            logger.error(e);
        }
    }
    /**
     * Метод отправляет ссылку на видеоурок на сервер
     */
    @FXML
    private void setCreateVideo() {
        if (!createVideoName.getText().isEmpty() && !linkInVideoYoutube.getText().isEmpty()) {
            String videoName = createVideoName.getText();
            String linkInVideo = linkInVideoYoutube.getText();
            String url_create_video = "http://" + Variables.ip_server + ":" + Variables.port_server + "/createVideo";
            ResponseEntity<String> response = null;
            ListOfVideoTemp listOfVideoTemp = new ListOfVideoTemp(videoName, linkInVideo);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<ListOfVideoTemp> entity = new HttpEntity<>(listOfVideoTemp, headers);
            try {
                response = restTemplate.exchange(url_create_video, HttpMethod.POST, entity, String.class);
                logger.info(response.getBody());
                createVideoName.setText("");
                linkInVideoYoutube.setText("");
            } catch (RuntimeException e) {
                logger.error(e);
            }
        }
    }
    /**
     * Метод отправляет GET - запрос на получение видеоуроков
     */
    @FXML
    private void getUpdateVideo() {
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
    /**
     * Реализация кнопки "Перейти по ссылке"
     * Для видеовкладки
     */
    @FXML
    private void followVideos() {
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
    /**
     * Метод отправляет GET - запрос на получение списка студентов
     */
    @FXML
    private void getUpdateStudents() {
        String url_getStudents = "http://" + Variables.ip_server + ":" + Variables.port_server + "/students";
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(url_getStudents, HttpMethod.GET, null, String.class);
            listOfStudentsData.clear();
            JsonParser jsonParser = new JsonParser();
            try {
                JsonArray jsonArray = jsonParser.parse(response.getBody()).getAsJsonArray();
                for(JsonElement jsonElement: jsonArray) {
                    StudentsListTemp studentsListTemp = gson.fromJson(jsonElement, StudentsListTemp.class);
                    Students students = new Students(studentsListTemp.id, studentsListTemp.surname, studentsListTemp.name);
                    listOfStudentsData.add(students);
                }
            } catch (JsonSyntaxException e) {
                logger.error(e);
            }
        } catch (RuntimeException e) {
            logger.error(e);
        }
    }
    /**
     * Метод добавляет администратора
     */
    @FXML
    private void setCreateAdministrator() {
        if (!loginAdministrator.getText().isEmpty() && !passwordAdministrator.getText().isEmpty()) {
            String loginAdmin = loginAdministrator.getText();
            String passwordAdmin = passwordAdministrator.getText();
            String url_getAdmin = "http://" + Variables.ip_server + ":" + Variables.port_server + "/setAdministrator/" + loginAdmin + "&" +passwordAdmin;
            ResponseEntity<String> response = null;
            try {
                response = restTemplate.exchange(url_getAdmin, HttpMethod.GET, null, String.class);
                String text = response.getBody();
                if (text.equals("REGISTERED_ADMIN")) {
                    logger.info("Новый администратор успешно добавлен!");
                } else if (text.equals("NOT_REGISTERED")) {
                    logger.info("Администратор с таким логином уже существует!");
                } else {
                    logger.error("Непредвиденная ошибка");
                }
                loginAdministrator.setText("");
                passwordAdministrator.setText("");
            } catch (RuntimeException e) {
                logger.error(e);
            }
        }
    }
    /**
     * При нажати кнопки "Отчислить" отправляем на сервер
     * GET - запрос для удаления ученика из БД
     */
    @FXML
    private void setDismiss() {
        if (valueOfStudent != null && !valueOfStudent.isEmpty()) {
            String studentId = valueOfStudent;
            String url_student_dismiss = "http://" + Variables.ip_server + ":" + Variables.port_server + "/deleteStudent/" + studentId;
            ResponseEntity<String> response = null;
            try {
                response = restTemplate.exchange(url_student_dismiss, HttpMethod.GET, null, String.class);
                String text = response.getBody();
                logger.info(text);
            } catch (RuntimeException e) {
                logger.error(e);
            }
        }
    }
    /**
     * При нажатии кнопки "Скачать материал" отправляем на сервер
     * GET - запрос с телом запроса ID pdf файла
     * и получаем byte[] массив, в последствии преобразуем его в pdf
     * сохраняем на компьютер по умолчанию в папке "Загрузки"
     */
    @FXML
    private void setDownloadMaterial() {
        if (valueOfMaterial != null && !valueOfMaterial.isEmpty()) {
            //Определеяем наш домашний каталог
            String userHome = System.getProperty("user.home");
            String pdfId = valueOfMaterial;
            String url_download_material = "http://" + Variables.ip_server + ":" + Variables.port_server + "/getPdfFile/" + pdfId;
            ResponseEntity<byte[]> response = null;
            try {
                response = restTemplate.exchange(url_download_material, HttpMethod.GET, null, new ParameterizedTypeReference<byte[]>() {});
            } catch (RuntimeException e) {
                logger.error(e);
            }
            try {
                //Путь к файлу, который вы хотите создать
                String outputPath = userHome + "\\Downloads\\output.pdf";
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
}