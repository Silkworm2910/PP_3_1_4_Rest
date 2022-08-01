package com.example.dao;

import com.example.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserDAO {
    void saveUser(User user);

    User findById(int id);

    void updateUser(User user);

    void deleteUserById(int id);

    List<User> findAllUsers();

    Optional<User> findByUsername(String username);

    Optional<User> findUserByRoles(Set<String> role_admin);
}
