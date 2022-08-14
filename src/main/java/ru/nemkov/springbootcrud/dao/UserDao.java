package ru.nemkov.springbootcrud.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nemkov.springbootcrud.model.User;
import java.util.List;

@Repository
public interface UserDao {
   void addUser(User user);
    List<User> getUserList();
    User getUserById(Long id);
    User findByUsername(String username);
    void updateUser(User user);
    void removeUser(Long id);
}
