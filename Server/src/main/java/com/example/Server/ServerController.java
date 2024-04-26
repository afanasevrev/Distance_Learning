package com.example.Server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {
    @GetMapping("/")
    private String getInfo() {
        return "Дистанционное обучение для охранников 3-4-5 разряда";
    }
}
