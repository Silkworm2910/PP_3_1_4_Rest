package com.example.service;

import com.example.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    boolean saveUser(User user, String[] rolesNames);

    User findUserByID(int id);

    void updateUser(User user, String[] rolesNames);

    void deleteUserById(int id);

    List<User> findAllUsers();

    Optional<User> findUserByRoles(Set<String> role_admin);
}
