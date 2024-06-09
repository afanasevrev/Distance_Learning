package com.example.Server.controllers;

import com.example.Server.Direction;
import com.example.Server.authorization.Login;
import com.example.Server.db.*;
import com.example.Server.hibernate.HibernateUtil;
import com.example.Server.messaging.ListOfVideoTemp;
import com.example.Server.messaging.MaterialsTemp;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import java.util.ArrayList;
import java.util.List;
/**
 * Класс контроллер для сервера,
 * принимает сообщения от клиентов и отвечает на них
 */
@RestController
public class ServerController {
    //Ведём лог событий этого класса
    Logger logger = Logger.getLogger(ServerController.class);
    /**
     * Возвращает клиенту тест на 6 разряд
     * @return
     */
    @GetMapping("/getTestCategory6")
    private List<TestSecurityCategory6> getTest6Category() {
        return getTestSecurityCategory6();
    }
    /**
     * Возвращает клиенту тест на 5 разряд
     * @return ArrayList
     */
    @GetMapping("/getTestCategory5")
    private List<TestSecurityCategory5> getTest5Category() {
        return getTestSecurityCategory5();
    }
    /**
     * Возвращает клиенту тест на 4 разряд
     * @return ArrayList
     */
    @GetMapping("/getTestCategory4")
    private List<TestSecurityCategory4> getTest4Category() {
        return getTestSecurityCategory4();
    }
    /**
     * Отвечает на общий запрос
     * @return Дистанционное обучение для охранников 4-5-6 разряда
     */
    @GetMapping("/")
    private String getInfo() {
        return "Дистанционное обучение для охранников 4-5-6 разряда";
    }
    /**
     * Метод проверяет, прошёл ли пользователь аутентификацию или нет
     * @param login полученный от пользователя класс для аутентификации
     * @return AUTHENTICATED_ADMIN, AUTHENTICATED_STUDENT, NOT_AUTHENTICATED
     */
    @PostMapping("/authenticate")
    private Login getAuthentication(@RequestBody Login login) {
        Login login1 = new Login();
        if (getAdmins(login.getLogin(), login.getPassword())) {
            login1.setAuthentic_status(Direction.AUTHENTICATED_ADMIN.toString());
            return login1;
        } else if (getStudents(login1, login.getLogin(), login.getPassword())) {
            login1.setAuthentic_status(Direction.AUTHENTICATED_STUDENT.toString());
            return login1;
        } else {
            login1.setAuthentic_status(Direction.NOT_AUTHENTICATED.toString());
            return login1;
        }
    }
    /**
     * Метод регистрирует студента в системе
     * @return REGISTERED_STUDENT, NOT_REGISTERED
     */
    @PostMapping("/registration")
    private String setRegistrationStudent(@RequestBody Students student) {
        if (getStudents(student.getLogin())) {
            return Direction.NOT_REGISTERED.toString();
        } else {
            writeStudent(student);
            return Direction.REGISTERED_STUDENT.toString();
        }
    }
    /**
     * Метод получает от клиента pdf файл и записывает его в БД
     * @param file полученный файл pdf
     * @param textCreateMaterialName полученное имя материала
     * @return Материал успешно добавлен
     */
    @PostMapping(value = "/upload/{textCreateMaterialName}", consumes = MediaType.APPLICATION_PDF_VALUE)
    private ResponseEntity<String> uploadPDF(@RequestBody byte[] file, @PathVariable String textCreateMaterialName) {
        writeMaterial(new Materials(textCreateMaterialName, file));
        return ResponseEntity.ok("Материал успешно добавлен");
    }
    /**
     * Метод получает от клиента pdf файл и записывает его в БД для охранников 4 категории
     * @param file полученный файл pdf
     * @param textCreateMaterialName полученное имя материала
     * @return Материал успешно добавлен
     */
    @PostMapping(value = "/upload/category4/{textCreateMaterialName}", consumes = MediaType.APPLICATION_PDF_VALUE)
    private ResponseEntity<String> uploadPDFCategory4(@RequestBody byte[] file, @PathVariable String textCreateMaterialName) {
        writeMaterialsFor4Category(new MaterialsFor4Category(textCreateMaterialName, file));
        return ResponseEntity.ok("Материал успешно добавлен");
    }
    /**
     * Метод получает от клиента pdf файл и записывает его в БД для охранников 5 категории
     * @param file полученный файл pdf
     * @param textCreateMaterialName полученное имя материала
     * @return Материал успешно добавлен
     */
    @PostMapping(value = "/upload/category5/{textCreateMaterialName}", consumes = MediaType.APPLICATION_PDF_VALUE)
    private ResponseEntity<String> uploadPDFCategory5(@RequestBody byte[] file, @PathVariable String textCreateMaterialName) {
        writeMaterialsFor5Category(new MaterialsFor5Category(textCreateMaterialName, file));
        return ResponseEntity.ok("Материал успешно добавлен");
    }
    /**
     * Метод получает от клиента pdf файл и записывает его в БД для охранников 6 категории
     * @param file полученный файл pdf
     * @param textCreateMaterialName полученное имя материала
     * @return Материал успешно добавлен
     */
    @PostMapping(value = "/upload/category6/{textCreateMaterialName}", consumes = MediaType.APPLICATION_PDF_VALUE)
    private ResponseEntity<String> uploadPDFCategory6(@RequestBody byte[] file, @PathVariable String textCreateMaterialName) {
        writeMaterialsFor6Category(new MaterialsFor6Category(textCreateMaterialName, file));
        return ResponseEntity.ok("Материал успешно добавлен");
    }
    /**
     * Метод получает от клиента pdf файл и записывает его в БД для гражданского оружия вида пистолет
     * @param file полученный файл pdf
     * @param textCreateMaterialName полученное имя материала
     * @return Материал успешно добавлен
     */
    @PostMapping(value = "/upload/pistols/{textCreateMaterialName}", consumes = MediaType.APPLICATION_PDF_VALUE)
    private ResponseEntity<String> uploadPDFPistols(@RequestBody byte[] file, @PathVariable String textCreateMaterialName) {
        writeMaterialsForTypePistols(new MaterialsForTypePistols(textCreateMaterialName, file));
        return ResponseEntity.ok("Материал успешно добавлен");
    }
    /**
     * Метод получает от клиента pdf файл и записывает его в БД для гражданского оружия вида помповые
     * @param file полученный файл pdf
     * @param textCreateMaterialName полученное имя материала
     * @return Материал успешно добавлен
     */
    @PostMapping(value = "/upload/pumps/{textCreateMaterialName}", consumes = MediaType.APPLICATION_PDF_VALUE)
    private ResponseEntity<String> uploadPDFPumps(@RequestBody byte[] file, @PathVariable String textCreateMaterialName) {
        writeMaterialsForTypePumps(new MaterialsForTypePumps(textCreateMaterialName, file));
        return ResponseEntity.ok("Материал успешно добавлен");
    }
    /**
     * Метод получает от клиента pdf файл и записывает его в БД для гражданского оружия вида гладкоствольные
     * @param file полученный файл pdf
     * @param textCreateMaterialName полученное имя материала
     * @return Материал успешно добавлен
     */
    @PostMapping(value = "/upload/smoothBore/{textCreateMaterialName}", consumes = MediaType.APPLICATION_PDF_VALUE)
    private ResponseEntity<String> uploadPDFSmoothBore(@RequestBody byte[] file, @PathVariable String textCreateMaterialName) {
        writeMaterialsForTypeSmoothBore(new MaterialsForTypeSmoothBore(textCreateMaterialName, file));
        return ResponseEntity.ok("Материал успешно добавлен");
    }
    /**
     * GET - запрос от клиента на получение списка материалов
     * @return список материалов
     */
    @GetMapping("/materials")
    private List<MaterialsTemp> getMaterialsTemp() {
        List<Materials> materials = new ArrayList<>();
        List<MaterialsTemp> materialsTemp = new ArrayList<>();
        materials = getMaterials();
        for (Materials material: materials) {
            materialsTemp.add(new MaterialsTemp(material.getId(), material.getName()));
        }
        return materialsTemp;
    }
    /**
     * GET - запрос от клиента на получение списка материалов для охранников 4 разряда
     * @return список материалов
     */
    @GetMapping("/materials/category4")
    private List<MaterialsTemp> getMaterialsTempCategory4() {
        List<MaterialsFor4Category> materials = new ArrayList<>();
        List<MaterialsTemp> materialsTemp = new ArrayList<>();
        materials = getMaterialsCategory4();
        for (MaterialsFor4Category material: materials) {
            materialsTemp.add(new MaterialsTemp(material.getId(), material.getName()));
        }
        return materialsTemp;
    }
    /**
     * GET - запрос от клиента на получение списка материалов для охранников 5 разряда
     * @return список материалов
     */
    @GetMapping("/materials/category5")
    private List<MaterialsTemp> getMaterialsTempCategory5() {
        List<MaterialsFor5Category> materials = new ArrayList<>();
        List<MaterialsTemp> materialsTemp = new ArrayList<>();
        materials = getMaterialsCategory5();
        for (MaterialsFor5Category material: materials) {
            materialsTemp.add(new MaterialsTemp(material.getId(), material.getName()));
        }
        return materialsTemp;
    }
    /**
     * GET - запрос от клиента на получение списка материалов для охранников 4 разряда
     * @return список материалов
     */
    @GetMapping("/materials/category6")
    private List<MaterialsTemp> getMaterialsTempCategory6() {
        List<MaterialsFor6Category> materials = new ArrayList<>();
        List<MaterialsTemp> materialsTemp = new ArrayList<>();
        materials = getMaterialsCategory6();
        for (MaterialsFor6Category material: materials) {
            materialsTemp.add(new MaterialsTemp(material.getId(), material.getName()));
        }
        return materialsTemp;
    }
    /**
     * GET - запрос от клиента на получение списка материалов для оружий вида пистолеты
     * @return список материалов
     */
    @GetMapping("/armMaterials/typePistols")
    private List<MaterialsTemp> getMaterialsTempTypePistols() {
        List<MaterialsForTypePistols> materials = new ArrayList<>();
        List<MaterialsTemp> materialsTemp = new ArrayList<>();
        materials = getMaterialsPistols();
        for (MaterialsForTypePistols material: materials) {
            materialsTemp.add(new MaterialsTemp(material.getId(), material.getName()));
        }
        return materialsTemp;
    }
    /**
     * GET - запрос от клиента на получение списка материалов для оружий вида помповые
     * @return список материалов
     */
    @GetMapping("/armMaterials/typePumps")
    private List<MaterialsTemp> getMaterialsTempTypePumps() {
        List<MaterialsForTypePumps> materials = new ArrayList<>();
        List<MaterialsTemp> materialsTemp = new ArrayList<>();
        materials = getMaterialsPumps();
        for (MaterialsForTypePumps material: materials) {
            materialsTemp.add(new MaterialsTemp(material.getId(), material.getName()));
        }
        return materialsTemp;
    }
    /**
     * GET - запрос от клиента на получение списка материалов для оружий вида гладкоствольные
     * @return список материалов
     */
    @GetMapping("/armMaterials/typeSmoothBore")
    private List<MaterialsTemp> getMaterialsTempTypeSmoothBore() {
        List<MaterialsForTypeSmoothBore> materials = new ArrayList<>();
        List<MaterialsTemp> materialsTemp = new ArrayList<>();
        materials = getMaterialsSmoothBore();
        for (MaterialsForTypeSmoothBore material: materials) {
            materialsTemp.add(new MaterialsTemp(material.getId(), material.getName()));
        }
        return materialsTemp;
    }
    /**
     * POST - запрос от клиента на добавление ссылки на видеоурок
     * @param listOfVideoTemp полученный JSON класс
     * @return статус о выполнении запроса
     */
    @PostMapping("/createVideo")
    private String createVideo(@RequestBody ListOfVideoTemp listOfVideoTemp) {
        writeVideo(new VideoLinks(listOfVideoTemp.getNameVideo(), listOfVideoTemp.getLinkInVideo()));
        return "Видеоурок успешно добавлен";
    }
    /**
     * GET - запрос от клиента на получение списка видеоуроков
     * @return список видеоуроков
     */
    @GetMapping("/videos")
    private List<VideoLinks> getVideo() {
        List<VideoLinks> videoLinks = new ArrayList<>();
        videoLinks = getVideoLinks();
        return videoLinks;
    }
    /**
     * GET - запрос от клиента на получение списка учеников
     * @return список учеников
     */
    @GetMapping("/students")
    private List<Students> getStudentsList() {
        List<Students> studentsList = new ArrayList<>();
        studentsList = getStudents();
        return studentsList;
    }
    /**
     * GET - запрос от клиента на добавление в БД администратора
     * @param loginAdmin логин
     * @param passwordAdmin пароль
     * @return
     */
    @GetMapping("/setAdministrator/{loginAdmin}&{passwordAdmin}")
    private String getAdminsStatus(@PathVariable String loginAdmin, @PathVariable String passwordAdmin) {
        if (!getAdmins(loginAdmin)) {
            writeAdmin(new Admins(loginAdmin, passwordAdmin));
            return Direction.REGISTERED_ADMIN.toString();
        } else {
            return Direction.NOT_REGISTERED.toString();
        }
    }
    /**
     * по GET - запросу от клиента удаляем ученика из системы
     * @param studentId Id ученика
     * @return статус выполнения запроса
     */
    @GetMapping("/deleteStudent/{studentId}")
    private String getDeleteStudent(@PathVariable String studentId) {
        int id = Integer.parseInt(studentId);
        deleteStudent(id);
        return "Ученик отчислен";
    }
    /**
     * по GET - запросу от клиента возвращаем pdf - файл из БД
     * @param pdfId ID pdf - файла
     * @return byte[]
     */
    @GetMapping("/getPdfFile/{pdfId}")
    private byte[] getPDFFile(@PathVariable String pdfId) {
        int id = Integer.parseInt(pdfId);
        byte[] pdf_file = getPdfFile(id);
        logger.info(pdf_file);
        return pdf_file;
    }
    /**
     * по GET - запросу от клиента возвращаем pdf - файл из БД
     * @param pdfId ID pdf - файла
     * @return byte[]
     */
    @GetMapping("/getPdfFile/category4/{pdfId}")
    private byte[] getPDFFileCategory4(@PathVariable String pdfId) {
        int id = Integer.parseInt(pdfId);
        byte[] pdf_file = getPdfFileCategory4(id);
        logger.info(pdf_file);
        return pdf_file;
    }
    /**
     * по GET - запросу от клиента возвращаем pdf - файл из БД
     * @param pdfId ID pdf - файла
     * @return byte[]
     */
    @GetMapping("/getPdfFile/category5/{pdfId}")
    private byte[] getPDFFileCategory5(@PathVariable String pdfId) {
        int id = Integer.parseInt(pdfId);
        byte[] pdf_file = getPdfFileCategory5(id);
        logger.info(pdf_file);
        return pdf_file;
    }
    /**
     * по GET - запросу от клиента возвращаем pdf - файл из БД
     * @param pdfId ID pdf - файла
     * @return byte[]
     */
    @GetMapping("/getPdfFile/category6/{pdfId}")
    private byte[] getPDFFileCategory6(@PathVariable String pdfId) {
        int id = Integer.parseInt(pdfId);
        byte[] pdf_file = getPdfFileCategory6(id);
        logger.info(pdf_file);
        return pdf_file;
    }
    /**
     * по GET - запросу от клиента возвращаем pdf - файл из БД
     * @param pdfId ID pdf - файла
     * @return byte[]
     */
    @GetMapping("/getPdfFile/typePistols/{pdfId}")
    private byte[] getPDFFilePistols(@PathVariable String pdfId) {
        int id = Integer.parseInt(pdfId);
        byte[] pdf_file = getPdfFileTypePistols(id);
        logger.info(pdf_file);
        return pdf_file;
    }
    /**
     * по GET - запросу от клиента возвращаем pdf - файл из БД
     * @param pdfId ID pdf - файла
     * @return byte[]
     */
    @GetMapping("/getPdfFile/typePumps/{pdfId}")
    private byte[] getPDFFilePumps(@PathVariable String pdfId) {
        int id = Integer.parseInt(pdfId);
        byte[] pdf_file = getPdfFileTypePumps(id);
        logger.info(pdf_file);
        return pdf_file;
    }
    /**
     * по GET - запросу от клиента возвращаем pdf - файл из БД
     * @param pdfId ID pdf - файла
     * @return byte[]
     */
    @GetMapping("/getPdfFile/typeSmoothBore/{pdfId}")
    private byte[] getPDFFileSmoothBore(@PathVariable String pdfId) {
        int id = Integer.parseInt(pdfId);
        byte[] pdf_file = getPdfFileTypeSmoothBore(id);
        logger.info(pdf_file);
        return pdf_file;
    }
    /**
     * Метод вытягивает из БД список администраторов системы
     * и проверяет поступивший логин и пароль со списком,
     * в случае положительного решения, метод возвращает true,
     * иначе false
     * @param login - полученный логин
     * @param password - полученный пароль
     * @return boolean
     */
    private boolean getAdmins(String login, String password) {
        boolean result = false;
        List<Admins> admins = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            admins = session.createQuery("from Admins", Admins.class).getResultList();
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error(e);
        }
        for(Admins admin: admins) {
            if (admin.getLogin().equals(login)) {
                if (admin.getPassword().equals(password)) {
                    result = true;
                }
            }
        }
        return result;
    }
    /**
     * Метод вытягивает из БД список администраторов системы
     * и проверяет поступивший логин со списком,
     * в случае положительного решения, метод возвращает true,
     * иначе false
     * @param login полученный логин
     * @return boolean
     */
    private boolean getAdmins(String login) {
        boolean result = false;
        List<Admins> admins = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            admins = session.createQuery("from Admins", Admins.class).getResultList();
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error(e);
        }
        for(Admins admin: admins) {
            if (admin.getLogin().equals(login)) {
                    result = true;
            }
        }
        return result;
    }
    /**
     * Метод вытягивает из БД список студентов системы
     * и проверяет поступивший логин и пароль со списком,
     * в случае положительного решения, метод возвращает true,
     * иначе false
     * @param login - полученный логин
     * @param password - полученный пароль
     * @return тип boolean
     */
    private boolean getStudents(Login login1, String login, String password) {
        boolean result = false;
        List<Students> students = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            students = session.createQuery("from Students", Students.class).getResultList();
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error(e);
        }
        for(Students student: students) {
            if (student.getLogin().equals(login)) {
                if (student.getPassword().equals(password)) {
                    login1.setCategory(student.getCategory());
                    login1.setType(student.getType());
                    login1.setStudent_id(student.getId());
                    result = true;
                }
            }
        }
        return result;
    }
    /**
     * Метод вытягивает из БД список учеников
     * и сравнивает его с полученным логином
     * @param login логин
     * @return boolean
     */
    private boolean getStudents(String login) {
        boolean result = false;
        List<Students> students = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            students = session.createQuery("from Students", Students.class).getResultList();
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error(e);
        }
        for(Students student: students) {
            if (student.getLogin().equals(login)) {
                    result = true;
                }
            }
        return result;
    }
    /**
     * Метод записывает в БД ученика
     * @param student передаем полученного студента
     */
    private synchronized void writeStudent(Students student) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            // Добавим в БД сервер
            session.persist(student);
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    /**
     * Метод записывает в БД материал
     * @param material передаем полученный материал
     */
    private synchronized void writeMaterial(Materials material) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            // Добавим в БД сервер
            session.persist(material);
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    /**
     * Метод вытягивает из БД список учебных материалов
     * @return учебные материалы (Materials)
     */
    private List<Materials> getMaterials() {
        List<Materials> materials = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            materials = session.createQuery("from Materials", Materials.class).getResultList();
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error(e);
        }
        return materials;
    }
    /**
     * Метод вытягивает из БД список учебных материалов для охранников 4 разряда
     * @return учебные материалы (Materials)
     */
    private List<MaterialsFor4Category> getMaterialsCategory4() {
        List<MaterialsFor4Category> materials = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            materials = session.createQuery("from MaterialsFor4Category", MaterialsFor4Category.class).getResultList();
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error(e);
        }
        return materials;
    }
    /**
     * Метод вытягивает из БД список учебных материалов для охранников 5 разряда
     * @return учебные материалы (Materials)
     */
    private List<MaterialsFor5Category> getMaterialsCategory5() {
        List<MaterialsFor5Category> materials = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            materials = session.createQuery("from MaterialsFor5Category", MaterialsFor5Category.class).getResultList();
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error(e);
        }
        return materials;
    }
    /**
     * Метод вытягивает из БД список учебных материалов для охранников 6 разряда
     * @return учебные материалы (Materials)
     */
    private List<MaterialsFor6Category> getMaterialsCategory6() {
        List<MaterialsFor6Category> materials = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            materials = session.createQuery("from MaterialsFor6Category", MaterialsFor6Category.class).getResultList();
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error(e);
        }
        return materials;
    }
    /**
     * Метод вытягивает из БД список учебных материалов для оружия в категории пистолеты
     * @return учебные материалы (Materials)
     */
    private List<MaterialsForTypePistols> getMaterialsPistols() {
        List<MaterialsForTypePistols> materials = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            materials = session.createQuery("from MaterialsForTypePistols", MaterialsForTypePistols.class).getResultList();
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error(e);
        }
        return materials;
    }
    /**
     * Метод вытягивает из БД список учебных материалов для оружия в категории помповые
     * @return учебные материалы (Materials)
     */
    private List<MaterialsForTypePumps> getMaterialsPumps() {
        List<MaterialsForTypePumps> materials = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            materials = session.createQuery("from MaterialsForTypePumps", MaterialsForTypePumps.class).getResultList();
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error(e);
        }
        return materials;
    }
    /**
     * Метод вытягивает из БД список учебных материалов для оружия в категории гладкоствольные
     * @return учебные материалы (Materials)
     */
    private List<MaterialsForTypeSmoothBore> getMaterialsSmoothBore() {
        List<MaterialsForTypeSmoothBore> materials = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            materials = session.createQuery("from MaterialsForTypeSmoothBore", MaterialsForTypeSmoothBore.class).getResultList();
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error(e);
        }
        return materials;
    }
    /**
     * Метод записывает в БД видеоурок
     * @param videoLink передаем полученное сообщение от клиента
     */
    private synchronized void writeVideo(VideoLinks videoLink) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            // Добавим в БД сервер
            session.persist(videoLink);
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    /**
     * Метод вытягивает из БД список видеоматериалов
     * @return видеоматериалы
     */
    private List<VideoLinks> getVideoLinks() {
        List<VideoLinks> videoLinks = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            videoLinks = session.createQuery("from VideoLinks", VideoLinks.class).getResultList();
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error(e);
        }
        return videoLinks;
    }
    /**
     * Метод вытягивает из БД список учеников
     * @return студенты или ученики :)
     */
    private List<Students> getStudents() {
        List<Students> students = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            students = session.createQuery("from Students", Students.class).getResultList();
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error(e);
        }
        return students;
    }
    /**
     * Метод записывает в БД администратора
     * @param admin полученный от клиента данные на администратора
     */
    private synchronized void writeAdmin(Admins admin) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            // Добавим в БД сервер
            session.persist(admin);
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    /**
     * Метод удаляет из БД ученика
     * @param studentId ID ученика
     */
    private synchronized void deleteStudent(int studentId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            Students student = session.get(Students.class, studentId);
            if (student != null) {
                //Удаляем объект
                session.delete(student);
            }
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    /**
     * Метод вытягивает из БД pdf - файл
     * @param pdfId ID полученный от клиента
     * @return byte[]
     */
    private synchronized byte[] getPdfFile(int pdfId) {
        Transaction transaction = null;
        Materials material = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            material = session.get(Materials.class, pdfId);
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return material.getPdf_file();
    }
    /**
     * Метод вытягивает из БД pdf - файл
     * @param pdfId ID полученный от клиента
     * @return byte[]
     */
    private synchronized byte[] getPdfFileCategory4(int pdfId) {
        Transaction transaction = null;
        MaterialsFor4Category material = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            material = session.get(MaterialsFor4Category.class, pdfId);
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return material.getPdf_file();
    }
    /**
     * Метод вытягивает из БД pdf - файл
     * @param pdfId ID полученный от клиента
     * @return byte[]
     */
    private synchronized byte[] getPdfFileCategory5(int pdfId) {
        Transaction transaction = null;
        MaterialsFor5Category material = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            material = session.get(MaterialsFor5Category.class, pdfId);
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return material.getPdf_file();
    }
    /**
     * Метод вытягивает из БД pdf - файл
     * @param pdfId ID полученный от клиента
     * @return byte[]
     */
    private synchronized byte[] getPdfFileCategory6(int pdfId) {
        Transaction transaction = null;
        MaterialsFor6Category material = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            material = session.get(MaterialsFor6Category.class, pdfId);
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return material.getPdf_file();
    }
    /**
     * Метод вытягивает из БД pdf - файл
     * @param pdfId ID полученный от клиента
     * @return byte[]
     */
    private synchronized byte[] getPdfFileTypePistols(int pdfId) {
        Transaction transaction = null;
        MaterialsForTypePistols material = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            material = session.get(MaterialsForTypePistols.class, pdfId);
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return material.getPdf_file();
    }
    /**
     * Метод вытягивает из БД pdf - файл
     * @param pdfId ID полученный от клиента
     * @return byte[]
     */
    private synchronized byte[] getPdfFileTypePumps(int pdfId) {
        Transaction transaction = null;
        MaterialsForTypePumps material = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            material = session.get(MaterialsForTypePumps.class, pdfId);
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return material.getPdf_file();
    }
    /**
     * Метод вытягивает из БД pdf - файл
     * @param pdfId ID полученный от клиента
     * @return byte[]
     */
    private synchronized byte[] getPdfFileTypeSmoothBore(int pdfId) {
        Transaction transaction = null;
        MaterialsForTypeSmoothBore material = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            material = session.get(MaterialsForTypeSmoothBore.class, pdfId);
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return material.getPdf_file();
    }
    /**
     * Метод записывает в БД тест для охранника категории 4
     * @param testSecurityCategory4
     */
    private synchronized void writeTestSecurityCategory4(TestSecurityCategory4 testSecurityCategory4) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            // Добавим в БД сервер
            session.persist(testSecurityCategory4);
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    /**
     * Метод записывает в БД тест для охранника категории 5
     * @param testSecurityCategory5
     */
    private synchronized void writeTestSecurityCategory5(TestSecurityCategory5 testSecurityCategory5) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            // Добавим в БД сервер
            session.persist(testSecurityCategory5);
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    /**
     * Метод записывает в БД тест для охранника категории 6
     * @param testSecurityCategory6
     */
    private synchronized void writeTestSecurityCategory6(TestSecurityCategory6 testSecurityCategory6) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            // Добавим в БД сервер
            session.persist(testSecurityCategory6);
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    /**
     * Метод записывает в БД материалы для охранников 4 разряда
     * @param materialsFor4Category
     */
    private synchronized void writeMaterialsFor4Category(MaterialsFor4Category materialsFor4Category) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            // Добавим в БД сервер
            session.persist(materialsFor4Category);
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    /**
     * Метод записывает в БД материалы для охранников 5 разряда
     * @param materialsFor5Category
     */
    private synchronized void writeMaterialsFor5Category(MaterialsFor5Category materialsFor5Category) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            // Добавим в БД сервер
            session.persist(materialsFor5Category);
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    /**
     * Метод записывает в БД материалы для охранников 6 разряда
     * @param materialsFor6Category
     */
    private synchronized void writeMaterialsFor6Category(MaterialsFor6Category materialsFor6Category) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            // Добавим в БД сервер
            session.persist(materialsFor6Category);
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    /**
     * Метод записывает в БД материалы для пистолетов
     * @param materialsForTypePistols
     */
    private synchronized void writeMaterialsForTypePistols(MaterialsForTypePistols materialsForTypePistols) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            // Добавим в БД сервер
            session.persist(materialsForTypePistols);
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    /**
     * Метод записывает в БД материалы для помповых оружий
     * @param materialsForTypePumps
     */
    private synchronized void writeMaterialsForTypePumps(MaterialsForTypePumps materialsForTypePumps) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            // Добавим в БД сервер
            session.persist(materialsForTypePumps);
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    /**
     * Метод записывает в БД материалы для пистолетов
     * @param materialsForTypeSmoothBore
     */
    private synchronized void writeMaterialsForTypeSmoothBore(MaterialsForTypeSmoothBore materialsForTypeSmoothBore) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            // Добавим в БД сервер
            session.persist(materialsForTypeSmoothBore);
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    /**
     * Возвращает тестовое задание для охранников 4 разряда
     * @return List
     */
    private List<TestSecurityCategory4> getTestSecurityCategory4() {
        List<TestSecurityCategory4> testSecurityCategory4 = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            testSecurityCategory4 = session.createQuery("from TestSecurityCategory4", TestSecurityCategory4.class).getResultList();
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error(e);
        }
        return testSecurityCategory4;
    }
    /**
     * Возвращает тестовое задание для охранников 5 разряда
     * @return List
     */
    private List<TestSecurityCategory5> getTestSecurityCategory5() {
        List<TestSecurityCategory5> testSecurityCategory5 = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            testSecurityCategory5 = session.createQuery("from TestSecurityCategory5", TestSecurityCategory5.class).getResultList();
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error(e);
        }
        return testSecurityCategory5;
    }
    /**
     * Возвращает тестовое задание для охранников 6 разряда
     * @return List
     */
    private List<TestSecurityCategory6> getTestSecurityCategory6() {
        List<TestSecurityCategory6> testSecurityCategory6 = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            testSecurityCategory6 = session.createQuery("from TestSecurityCategory6", TestSecurityCategory6.class).getResultList();
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error(e);
        }
        return testSecurityCategory6;
    }
}
