package ru.nemkov.springbootcrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.nemkov.springbootcrud.exeption_handling.NoSuchUserException;
import ru.nemkov.springbootcrud.exeption_handling.ValidationUserException;
import ru.nemkov.springbootcrud.model.User;
import ru.nemkov.springbootcrud.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ADMIN')")
public class RESTController {
    private UserService userService;

    @Autowired
    public RESTController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    @CrossOrigin
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getUserList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @CrossOrigin
    public User getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new NoSuchUserException(String.format("There is no user with ID = %d in Database", id));
        }
        return user;
    }

    @PostMapping()
    @CrossOrigin
    public ResponseEntity<List<User>> createUser(@RequestBody @Valid User user,
                                                 BindingResult bindingResult) {
        if (userService.findByUsername(user.getUsername()) != null || bindingResult.hasErrors()) {
            throw new ValidationUserException("Error: User can't be create. Validation error");
        }
        System.out.println(user);
        userService.saveOrUpdateUser(user);
        return new ResponseEntity<>(userService.getUserList(), HttpStatus.OK);
    }

    @PutMapping()
    @CrossOrigin
    public ResponseEntity<List<User>> updateUser(@RequestBody @Valid User user,
                                                 BindingResult bindingResult) {
        if ((userService.findByUsername(user.getUsername()) != null &&
                userService.findByUsername(user.getUsername()).getId() != user.getId())
                || bindingResult.hasErrors()) {
            throw new ValidationUserException("Error: User can't be create. Validation error");
        }
        userService.saveOrUpdateUser(user);
        return new ResponseEntity<>(userService.getUserList(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin
    public ResponseEntity<List<User>> deleteUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new NoSuchUserException(String.format("There is no user with ID = %d in Database", id));
        }
        userService.removeUser(id);
        return new ResponseEntity<>(userService.getUserList(), HttpStatus.OK);
    }

}
