package ru.nemkov.springbootcrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.nemkov.springbootcrud.model.User;
import ru.nemkov.springbootcrud.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.getUserList());
        return "admin/index";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "user/show";
    }

    @GetMapping("/new")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/new";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "/new";
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String showEditUserForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", (User) userService.getUserById(id));
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) return "/edit";
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }
}
