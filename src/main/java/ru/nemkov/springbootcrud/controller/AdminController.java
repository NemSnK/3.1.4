package ru.nemkov.springbootcrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.nemkov.springbootcrud.model.Role;
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

    @GetMapping()
    public String showAllUsers(Principal principal, Model model, @RequestParam(defaultValue = "1") int page) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        model.addAttribute("page", page);
        if (page == 2) {
            model.addAttribute("newuser", new User());
        } else {
            model.addAttribute("users", userService.getUserList());
        }
        return "admin/index";
    }


 /*  @GetMapping("/{id}")
    public String showUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "user/show";
    }*/

  /*  @GetMapping("/new")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new User());
        return "admin?page=2";
    }*/

    @PostMapping()
    public String createUser(Principal principal, Model model, @ModelAttribute("newuser") @Valid User user,
                             BindingResult bindingResult, Long newRole) {

        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("existedUsername", user.getUsername());
            model.addAttribute("user", userService.findByUsername(principal.getName()));
            model.addAttribute("page", 2);
            return "admin/index";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userService.findByUsername(principal.getName()));
            model.addAttribute("page", 2);
            return "admin/index";
        }

        user.setRoles(Collections.singleton(roleService.getRoleById(newRole)));
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
