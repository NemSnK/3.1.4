package ru.nemkov.springbootcrud.service;

import ru.nemkov.springbootcrud.model.Role;

public interface RoleService {
    void addRole(Role role);
    Role getRoleById(Long id);
}
