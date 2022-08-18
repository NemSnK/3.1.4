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
    private boolean showEdit = false;
    private User editUser;
    private final RoleService roleService;
    private final UserService userService;


    @Autowired
    public AdminController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    //Главная страница
    @GetMapping()
    public String showAllUsers(Principal principal, Model model, @RequestParam(defaultValue = "1") int page) {
        model.addAttribute("princ_user", userService.findByUsername(principal.getName()));
        model.addAttribute("showEdit", showEdit);
        model.addAttribute("page", page);
        if (page == 2) {
            model.addAttribute("newuser", new User());
        } else {
            model.addAttribute("newuser", editUser);
        }
        model.addAttribute("users", userService.getUserList());
        return "admin/index";
    }

    //Добавление нового пользователя
    @PostMapping()
    public String createUser(Principal principal, Model model, @ModelAttribute("newuser") @Valid User user,
                             BindingResult bindingResult, Long newRole) {

        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("existedUsername", user.getUsername());
            model.addAttribute("princ_user", userService.findByUsername(principal.getName()));
            model.addAttribute("page", 2);
            return "admin/index";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("princ_user", userService.findByUsername(principal.getName()));
            model.addAttribute("page", 2);
            return "admin/index";
        }

        user.setRoles(Collections.singleton(roleService.getRoleById(newRole)));
        userService.addUser(user);
        return "redirect:/admin";
    }

    //Удаление пользователя
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }

    //Изменение пользователя
    @PutMapping("/{id}/update")
    public String updateUser(Principal principal, Model model, @ModelAttribute("newuser") @Valid User user,
                             BindingResult bindingResult, @PathVariable("id") Long id,
                             Long newRole, String password) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin";

        }
        user.setPassword(password);
        user.setRoles(Collections.singleton(roleService.getRoleById(newRole)));
        userService.updateUser(id, user);
        showEdit = false;
        return "redirect:/admin";
    }

    //Костыли
    @GetMapping("/{id}/edit")
    public String showEditUserForm(Model model, @PathVariable("id") Long id) {
        showEdit = true;
        editUser = userService.getUserById(id);
        return "redirect:/admin";
    }

    @GetMapping("/close")
    public String closeEditUserForm() {
        showEdit = false;
        return "redirect:/admin";
    }
}
