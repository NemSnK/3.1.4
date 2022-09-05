package ru.nemkov.springbootcrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.nemkov.springbootcrud.exeption_handling.NoSuchUserException;
import ru.nemkov.springbootcrud.exeption_handling.ValidationUserException;
import ru.nemkov.springbootcrud.model.User;
import ru.nemkov.springbootcrud.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminRESTController {
    private UserService userService;

    @Autowired
    public AdminRESTController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public ModelAndView showAdminPage(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", userService.findByUsername(principal.getName()));
        modelAndView.setViewName("admin.html");
        return modelAndView;
    }

    @GetMapping("/api")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getUserList(), HttpStatus.OK);
    }

    @PostMapping("/api")
    public ResponseEntity<List<User>> createUser(@RequestBody @Valid User user,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationUserException("Error: User can't be create. Validation error");
        }
        userService.saveUser(user);
        return new ResponseEntity<>(userService.getUserList(), HttpStatus.OK);
    }

    @PutMapping("/api")
    public ResponseEntity<List<User>> updateUser(@RequestBody @Valid User user,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationUserException("Error: User can't be create. Validation error");
        }
        userService.updateUser(user);
        return new ResponseEntity<>(userService.getUserList(), HttpStatus.OK);
    }

    @DeleteMapping("api/{id}")
    public ResponseEntity<List<User>> deleteUser(@PathVariable Long id) {
        userService.removeUser(id);
        return new ResponseEntity<>(userService.getUserList(), HttpStatus.OK);
    }

}
