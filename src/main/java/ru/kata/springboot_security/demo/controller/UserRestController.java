package ru.kata.springboot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.springboot_security.demo.service.UserService;
import ru.kata.springboot_security.demo.service.UserServiceImpl;

@RestController
public class UserRestController {
    private UserServiceImpl service;

    public UserRestController(UserServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/check_email")
    public String checkDuplicateEmail(Long id, String email) {
        return service.isEmailUnique(id, email) ? "OK" : "Duplicated";
    }

}
