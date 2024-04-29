package com.example.Server.controllers;

import com.example.Server.Direction;
import com.example.Server.db.Admins;
import com.example.Server.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServerController {
    @GetMapping("/")
    private String getInfo() {
        return "Дистанционное обучение для охранников 4-5-6 разряда";
    }
    @GetMapping("/authenticate/{login}&{password}")
    private Direction getAuthentication(@PathVariable String login, @PathVariable String password) {

        return Direction.AUTHENTICATED;
    }
    /**
     *
     * @return
     */
    private boolean getAdmins() {
        boolean result = false;
        List<Admins> admins;
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
            e.printStackTrace();
            result = false;
        }

        return result;
    }
}
