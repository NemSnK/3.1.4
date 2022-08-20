package ru.nemkov.springbootcrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.nemkov.springbootcrud.model.User;
import ru.nemkov.springbootcrud.service.RoleService;
import ru.nemkov.springbootcrud.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final RoleService roleService;
    private final UserService userService;


    @Autowired
    public AdminController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }


    @GetMapping("")
    public String showAllUsers(Principal principal, Model model) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        model.addAttribute("users", userService.getUserList());
        return "admin/index";
    }

    @GetMapping("/add")
    public String showUserAddForm(Principal principal, Model model) {
        model.addAttribute("principal_user", userService.findByUsername(principal.getName()));
        model.addAttribute("user", new User());
        return "admin/newUser";
    }


    @PostMapping("admin/new")
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult, Long role, Principal principal, Model model) {

        if (userService.findByUsername(user.getUsername()) != null || bindingResult.hasErrors()) {
            return "redirect:/admin/add";
        }

        user.setRoles(Collections.singleton(roleService.getRoleById(role)));
        userService.addUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        System.out.println(userService.getUserById(id));
        userService.removeUser(id);
        return "redirect:/admin";
    }

    @PutMapping("/{id}/update")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             @PathVariable("id") Long id, Long role, String password) {
        user.setRoles(Collections.singleton(roleService.getRoleById(role)));
        if ((userService.findByUsername(user.getUsername()) != null &&
                userService.findByUsername(user.getUsername()).getId() != user.getId())
                || bindingResult.hasErrors()) {
            return "redirect:/admin";
        }
        userService.updateUser(id, user);
        return "redirect:/admin";
    }
}
