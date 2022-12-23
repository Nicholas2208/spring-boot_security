package ru.kata.springboot_security.demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.springboot_security.demo.model.Role;
import ru.kata.springboot_security.demo.model.User;
import ru.kata.springboot_security.demo.service.RoleService;
import ru.kata.springboot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private RoleService roleService;

    private PasswordEncoder passwordEncoder;

    public AdminController(UserService userService,
                           RoleService roleService,
                           PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String getAllUsers(Model model) {
        List<User> allUsers = userService.getAll();
        model.addAttribute("allUsers", allUsers);

        User form = new User();
        model.addAttribute("appUserForm", form);
        Set<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);

        return "list_users";
    }

    @PostMapping("/save")
    public String save(User user) {
        userService.save(user);
        return "redirect:/admin/";
    }

    @PostMapping("update/{id}")
    public String updateUser(@ModelAttribute("user") User user,
                             @PathVariable("id") Long id) {

        if (user.getPassword().length() == 0) {
            User userFromDb = userService.getUser(user.getId());
            user.setPassword(userFromDb.getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        if (user.getRoles() == null) {
            User userFromDb = userService.getUser(user.getId());
            user.setRoles(userFromDb.getRoles());
        } else {
            User userFromDb = userService.getUser(user.getId());
            user.addRoles(userFromDb.getRoles());
        }

        userService.update(id,user);
        return "redirect:/admin/";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin/";
    }
}
