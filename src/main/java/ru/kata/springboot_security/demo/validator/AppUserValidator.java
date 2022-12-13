package ru.kata.springboot_security.demo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.kata.springboot_security.demo.model.User;
import ru.kata.springboot_security.demo.service.UserService;

import java.util.Optional;

@Component
public class AppUserValidator implements Validator {
    private UserService userService;

    public AppUserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == User.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        User appUserForm = (User) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.appUserForm.firstName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.appUserForm.lastName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.appUserForm.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.appUserForm.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roles", "NotEmpty.appUserForm.roles");

        Optional<User> dbUser = userService.findByEmail(appUserForm.getEmail());

        if (!dbUser.isEmpty()) {
            errors.rejectValue("email", "Duplicate.appUserForm.email");
        }
    }
}
