package com.example.dao;

import com.example.model.Role;

import java.util.Optional;
import java.util.Set;

public interface RoleDAO {

    void saveRole(Role role);

    Set<Role> findAllRoles();

    Optional<Role> findByAuthority(String authority);

    Optional<Role> findByName(String name);

    Set<Role> findAllByNameIn(String[] roleNames);
}
