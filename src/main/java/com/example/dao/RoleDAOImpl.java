package com.example.dao;

import com.example.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class RoleDAOImpl implements RoleDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    public Set<Role> findAllRoles() {
        return new HashSet<>(entityManager
                .createQuery("select r from Role r order by r.id", Role.class).getResultList());
    }

    @Override
    public Optional<Role> findByAuthority(String authority) {
        return entityManager
                .createQuery("select r from Role r where r.authority = :authority", Role.class)
                .setParameter("authority", authority)
                .getResultStream()
                .findFirst();
    }

    public Optional<Role> findByName(String name) {
        return entityManager
                .createQuery("select r from Role r where r.name = :name", Role.class)
                .setParameter("name", name)
                .getResultStream()
                .findFirst();
    }

    public Set<Role> findAllByNameIn(String[] roleNames) {
        List<String> list = Arrays.asList(roleNames);
        return entityManager
                .createQuery("select r from Role r where r.name in :roleNames", Role.class)
                .setParameter("roleNames", list)
                .getResultStream().collect(Collectors.toSet());
    }
}
