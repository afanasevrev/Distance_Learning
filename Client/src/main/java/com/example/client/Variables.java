package com.example.client;

import java.security.SecureRandom;
/**
 * В этом классе будем хранить статические переменные
 */
public class Variables {
    //IP - адрес сервера
    public static String ip_server = "localhost";
    //Порт сервера
    public static String port_server = "8080";
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

