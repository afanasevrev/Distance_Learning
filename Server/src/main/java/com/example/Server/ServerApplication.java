package com.example.Server;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Точка запуска сервера
 */
@SpringBootApplication
public class ServerApplication {
	public static void main(String[] args) {
		BasicConfigurator.configure();
		SpringApplication.run(ServerApplication.class, args);
	}
}
