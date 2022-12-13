package ru.kata.springboot_security.demo.controller;

import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.kata.springboot_security.demo.model.Role;
import ru.kata.springboot_security.demo.model.User;
import ru.kata.springboot_security.demo.service.RoleService;
import ru.kata.springboot_security.demo.service.UserService;
import ru.kata.springboot_security.demo.validator.AppUserValidator;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;
    private AppUserValidator appUserValidator;

    public AdminController(UserService userService,
                           RoleService roleService,
                           PasswordEncoder passwordEncoder,
                           AppUserValidator appUserValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.appUserValidator = appUserValidator;
    }

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }

        if (target.getClass() == User.class) {
            dataBinder.setValidator(appUserValidator);
        }
    }

    @GetMapping("/")
    public String getAllUsers(Model model) {
        List<User> allUsers = userService.getAll();
        model.addAttribute("allUsers", allUsers);

        return "list_users";
    }

    @GetMapping("/new")
    public String showNewUserForm(Model model) {
        User form = new User();
        model.addAttribute("appUserForm", form);

        Set<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);

        return "new_user";
    }

    @PostMapping("/save")
    public String saveUser(Model model,
                           @ModelAttribute("appUserForm") @Validated User appUserForm,
                           BindingResult result) {
        if (result.hasErrors()) {
            Set<Role> roles = roleService.getAllRoles();
            model.addAttribute("roles", roles);

            return "new_user";
        }

        userService.save(appUserForm);
        return "redirect:/admin/";
    }

    @RequestMapping("/edit/{id}")
    public String showEditUserPage(@PathVariable("id") Long id,
                                   Model model) {
        User user = userService.getUser(id);
        Set<Role> roles = roleService.getAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);

        return "edit_user";
    }

    @PostMapping("update/{id}")
    public String updateUser(@ModelAttribute("user") @Validated User user,
                             @PathVariable("id") long id,
                             BindingResult result) {
        if (user.getPassword().length() == 0) {
            User userFromDb = userService.getUser(user.getId());
            user.setPassword(userFromDb.getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        if (result.hasErrors()) {
            return "edit_user";
        }

        userService.update(id,user);
        return "redirect:/admin/";
    }

    @RequestMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Long id) {
        userService.delete(id);

        return "redirect:/admin/";
    }
}
