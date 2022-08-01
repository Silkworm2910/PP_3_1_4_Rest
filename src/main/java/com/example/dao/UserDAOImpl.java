package com.example.dao;

import com.example.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public User findById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void deleteUserById(int id) {
        entityManager.remove(findById(id));
    }

    @Override
    public List<User> findAllUsers() {
        return  entityManager
                .createQuery("select u from User u order by u.id", User.class).getResultList();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return entityManager
                .createQuery("select u from User u where u.username = :username", User.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst();
    }

    @Override
    public Optional<User> findUserByRoles(Set<String> roleNames) {
        return entityManager
                .createQuery("select u from User u join u.roles r where r.name in :roleNames", User.class)
                .setParameter("roleNames", roleNames)
                .getResultStream()
                .findFirst();
    }
}
