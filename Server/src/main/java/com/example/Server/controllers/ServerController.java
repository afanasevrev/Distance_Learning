package com.example.Server.controllers;

import com.example.Server.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class ServerController {
    @GetMapping("/")
    private String getInfo() {
        return "Дистанционное обучение для охранников 4-5-6 разряда";
    }
    @GetMapping("/authenticate/{login}&{password}")
    private Direction getAuthentication(@PathVariable String login,@PathVariable String password) {

        return Direction.AUTHENTICATED;
    }

}
