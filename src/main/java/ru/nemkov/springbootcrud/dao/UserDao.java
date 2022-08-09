package ru.nemkov.springbootcrud.dao;


import ru.nemkov.springbootcrud.model.User;
import java.util.List;

public interface UserDao {
    void addUser(User user);

    List<User> getUserList();

    User getUserById(Long id);

    void updateUser(User user);

    void removeUser(Long id);
}
