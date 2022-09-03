package init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.nemkov.springbootcrud.model.Role;
import ru.nemkov.springbootcrud.model.User;
import ru.nemkov.springbootcrud.service.RoleService;
import ru.nemkov.springbootcrud.service.UserService;

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
    @EventListener(ApplicationReadyEvent.class)
    public void initialization(){

        Set<Role>  role= new HashSet<>();
        role.add(new Role(1L, "ROLE_USER"));
        role.add(new Role(2L, "ROLE_ADMIN"));

        System.out.println("Start init");
        User user = new User("user@mail.ru", "123",
                Collections.singleton(new Role(1L, "ROLE_USER")),
                "Userfirstname", "Userlastname", (byte) 25);
        User admin = new User("admin@mail.ru", "123",
                Collections.singleton(new Role(2L, "ROLE_ADMIN")),
                "Adminfirstname", "Adminlastname", (byte) 35);

        User duoRoles = new User("pupkin@mail.ru", "123",
                role,"Vasia", "Pupkin", (byte) 35);
        roleService.addRole(new Role(1L, "ROLE_USER"));
        roleService.addRole(new Role(2L, "ROLE_ADMIN"));
        userService.saveOrUpdateUser(user);
        userService.saveOrUpdateUser(admin);
        userService.saveOrUpdateUser(duoRoles);
        System.out.println("End init");
    }
}
