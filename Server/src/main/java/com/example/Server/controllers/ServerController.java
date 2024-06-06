package com.example.Server.controllers;

import com.example.Server.Direction;
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
    @GetMapping("/writeTests")
    private String writeTests() {
        List<TestSecurityCategory6> tests = new ArrayList<>();
        tests.add(new TestSecurityCategory6("Вред, причиненный в состоянии крайней необходимости:","Не подлежит возмещению.","Во всех случаях подлежит возмещению в полном объеме лицом, причинившим вред.","Подлежит возмещению по решению суда.",3));
        tests.add(new TestSecurityCategory6("Причинение вреда, менее значительного, чем предотвращенный вред, является обязательным условием правомерности действий:","В состоянии необходимой обороны.","В состоянии крайней необходимости.","Как в состоянии необходимой обороны, так и в состоянии крайней необходимости.",2));
        tests.add(new TestSecurityCategory6("При необходимой обороне причинение посягающему лицу любого вреда правомерно","В случае группового посягательства.","Если это посягательство сопряжено с насилием, опасным для жизни обороняющегося или другого лица, либо с непосредственной угрозой применения такого насилия.","Если посягательство сопряжено с насилием, опасным для здоровья обороняющегося.",2));
        
        for (TestSecurityCategory6 testSecurityCategory6: tests) {
            writeTestSecurityCategory6(testSecurityCategory6);
        }
        return "Тесты успешно записаны";
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
     * @param login полученный от пользователя логин
     * @param password полученный от пользователя пароль
     * @return AUTHENTICATED_ADMIN, AUTHENTICATED_STUDENT, NOT_AUTHENTICATED
     */
    @GetMapping("/authenticate/{login}&{password}")
    private String getAuthentication(@PathVariable String login, @PathVariable String password) {
        if (getAdmins(login, password)) {
            return Direction.AUTHENTICATED_ADMIN.toString();
        } else if (getStudents(login, password)) {
            return Direction.AUTHENTICATED_STUDENT.toString();
        } else {
            return Direction.NOT_AUTHENTICATED.toString();
        }
    }
    /**
     * Метод регистрирует студента в системе
     * @param surname полученная от пользователя фамилия
     * @param name полученное от пользователя имя
     * @param patronymic полученное от пользователя отчество
     * @param login полученный от пользователя логин
     * @param password полученный от пользователя пароль
     * @return REGISTERED_STUDENT, NOT_REGISTERED
     */
    @GetMapping("/registration/{surname}&{name}&{patronymic}&{login}&{password}")
    private String setRegistrationStudent(@PathVariable String surname, @PathVariable String name, @PathVariable String patronymic, @PathVariable String login, @PathVariable String password) {
        if (getStudents(login)) {
            return Direction.NOT_REGISTERED.toString();
        } else {
            writeStudent(new Students(surname, name, patronymic, login, password));
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
    private boolean getStudents(String login, String password) {
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
}
