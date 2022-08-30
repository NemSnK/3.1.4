package ru.nemkov.springbootcrud.service;


import ru.nemkov.springbootcrud.model.User;

import javax.transaction.Transactional;
import java.util.List;

public interface UserService {
    void saveOrUpdateUser(User user);

    List<User> getUserList();

    User findByUsername(String username);

    User getUserById(Long id);

    void removeUser(Long id);
}
