package ru.nemkov.springbootcrud.service;


import ru.nemkov.springbootcrud.model.User;

import javax.transaction.Transactional;
import java.util.List;

public interface UserService {
    void addUser(User user);

    List<User> getUserList();
    @Transactional
    User findByUsername(String username);

    Object getUserById(Long id);

    void updateUser(User user);

    void removeUser(Long id);
}
