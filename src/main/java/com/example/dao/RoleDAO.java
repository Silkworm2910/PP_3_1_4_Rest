package com.example.dao;

import com.example.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RoleDAO extends JpaRepository<Role, Integer> {

    Role findByAuthority(String authority);

    Role findByName(String name);

    Set<Role> findAllByNameIn(String[] roleNames);
}
