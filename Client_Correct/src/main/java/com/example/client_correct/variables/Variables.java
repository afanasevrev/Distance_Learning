package com.example.client_correct.variables;

import java.security.SecureRandom;

public class Variables {
    //IP - адрес сервера
    public static String ip_server = "46.226.166.233";
    //Порт сервера
    public static String port_server = "8080";
    //Разряд ученика
    public static int category;
    //Вид оружия
    public static String type;
    //ID ученика вошедшего в систему
    public static int student_id;
    //Баллы за тест
    public static int scores = 0;
    //Статус теста
    public static String pass = "Не сдал";
    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom RANDOM = new SecureRandom();
    public static String generateRandomFileName(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(LETTERS.charAt(RANDOM.nextInt(LETTERS.length())));
        }
        return sb.toString();
    }
}
