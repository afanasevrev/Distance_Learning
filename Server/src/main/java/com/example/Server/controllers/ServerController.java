package com.example.Server.controllers;

import com.example.Server.Direction;
import com.example.Server.db.Admins;
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
    Logger logger = Logger.getLogger(ServerController.class);
    @GetMapping("/")
    private String getInfo() {
        return "Дистанционное обучение для охранников 4-5-6 разряда";
    }
    @GetMapping("/authenticate/{login}&{password}")
    private Direction getAuthentication(@PathVariable String login, @PathVariable String password) {
        if (getAdmins(login, password)) {
            return Direction.AUTHENTICATED;
        } else {
            return Direction.NOT_AUTHENTICATED;
        }
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

            }
        }

        return result;
    }
}
