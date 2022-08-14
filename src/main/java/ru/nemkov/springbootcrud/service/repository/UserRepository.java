package ru.nemkov.springbootcrud.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nemkov.springbootcrud.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
