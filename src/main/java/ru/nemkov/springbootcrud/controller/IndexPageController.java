package ru.nemkov.springbootcrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nemkov.springbootcrud.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/index")
public class IndexPageController {
    private final UserService userService;

    @Autowired
    public IndexPageController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public String showUser(Principal principal, Model model) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "/index";
    }
}
