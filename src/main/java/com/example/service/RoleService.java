package com.example.service;

import com.example.model.Role;

import java.util.Set;

public interface RoleService {

    void saveRole(Role role);

    Set<Role> findAllRoles();
}
