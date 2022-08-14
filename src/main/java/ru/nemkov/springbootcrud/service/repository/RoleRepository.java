package ru.nemkov.springbootcrud.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nemkov.springbootcrud.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
