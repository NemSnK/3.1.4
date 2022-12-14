package ru.nemkov.springbootcrud.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.nemkov.springbootcrud.model.Role;
import ru.nemkov.springbootcrud.model.User;
import ru.nemkov.springbootcrud.service.RoleService;
import ru.nemkov.springbootcrud.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class InitUsers {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public InitUsers(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void createUsers() {

        Set<Role> role = new HashSet<>();
        role.add(new Role(1L, "ROLE_ADMIN"));
        role.add(new Role(2L, "ROLE_USER"));

        System.out.println("Start init");
        User admin = new User("admin@mail.ru", "123",
                Collections.singleton(new Role(1L, "ROLE_ADMIN")),
                "Adminfirstname", "Adminlastname", (byte) 35);
        User user = new User("user@mail.ru", "123",
                Collections.singleton(new Role(2L, "ROLE_USER")),
                "Userfirstname", "Userlastname", (byte) 25);
        User duoRoles = new User("pupkin@mail.ru", "123",
                role, "Vasia", "Pupkin", (byte) 35);
        roleService.addRole(new Role(1L, "ROLE_ADMIN"));
        roleService.addRole(new Role(2L, "ROLE_USER"));
        userService.saveUser(user);
        userService.saveUser(admin);
        userService.saveUser(duoRoles);
        System.out.println("End init");
    }
}
