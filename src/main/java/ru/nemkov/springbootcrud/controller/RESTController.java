package ru.nemkov.springbootcrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.nemkov.springbootcrud.exeption_handling.NoSuchUserException;
import ru.nemkov.springbootcrud.model.User;
import ru.nemkov.springbootcrud.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RESTController {
    private UserService userService;

    @Autowired
    public RESTController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @CrossOrigin
    public List<User> getUsers() {
        return userService.getUserList();
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
    public ResponseEntity<HttpStatus> createUser(@RequestBody @Valid User user,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("!!!Has errors!!!");
        }
        System.out.println(user);
        //userService.saveOrUpdateUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping()
    @CrossOrigin
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid User user,
                           BindingResult bindingResult) {
        System.out.println("I'm here!!!!");
        if (bindingResult.hasErrors()) {
            System.out.println("!!!Has errors!!!");
        }
        System.out.println(user);
        //userService.saveOrUpdateUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id){
        User user = userService.getUserById(id);
        if (user == null) {
            throw new NoSuchUserException(String.format("There is no user with ID = %d in Database", id));
        }
        userService.removeUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
