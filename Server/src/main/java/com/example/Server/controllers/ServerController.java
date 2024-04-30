package com.example.Server.controllers;

import com.example.Server.Direction;
import com.example.Server.db.Admins;
import com.example.Server.db.Students;
import com.example.Server.hibernate.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

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

        return null;
    }
    /**
     * Метод вытягивает из БД список администраторов системы
     * и проверяет поступивший логин и пароль со списком,
     * в случае положительного решения, метод возвращает true,
     * иначе false
     * @param login - полученный логин
     * @param password - полученный пароль
     * @return тип boolean
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
     * Метод записывает в БД ученика
     * @param student - передаем в метод полученного студента
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
}
