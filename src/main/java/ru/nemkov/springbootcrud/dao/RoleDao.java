package ru.nemkov.springbootcrud.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nemkov.springbootcrud.model.Role;
@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
}
