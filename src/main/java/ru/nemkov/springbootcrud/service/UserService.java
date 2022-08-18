package ru.nemkov.springbootcrud.service;


import ru.nemkov.springbootcrud.model.User;

import javax.transaction.Transactional;
import java.util.List;

public interface UserService {
    void addUser(User user);

    List<User> getUserList();

    User findByUsername(String username);

    User getUserById(Long id);

    void updateUser(Long id, User user);

    void removeUser(Long id);
}
