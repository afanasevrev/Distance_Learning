package com.example.Server.controllers;

import com.example.Server.Direction;
import com.example.Server.db.Admins;
import com.example.Server.db.Materials;
import com.example.Server.db.Students;
import com.example.Server.hibernate.HibernateUtil;
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
}
