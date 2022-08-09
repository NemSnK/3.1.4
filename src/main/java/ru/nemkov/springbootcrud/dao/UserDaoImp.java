package ru.nemkov.springbootcrud.dao;

import org.springframework.stereotype.Repository;
import ru.nemkov.springbootcrud.model.User;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public List<User> getUserList() {
        return entityManager.createQuery("select e from User e ", User.class).getResultList();
    }
    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class,id);
    }

    @Override
    public void updateUser(User user) { entityManager.merge(user); }

    @Override
    public void removeUser(Long id) {
        User user = entityManager.find(User.class,id);
        entityManager.remove(user);
    }
}
